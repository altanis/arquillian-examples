package com.github.altanis.arquillian.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.github.altanis.arquillian.core.items.Item;
import com.github.altanis.arquillian.core.items.ItemRepository;
import com.github.altanis.arquillian.deployment.ArquillianDeployment;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class ItemRepositoryTest {

    @Inject
    ItemRepository itemRepository;

    @Inject
    EntityManager entityManager;

    @Deployment
    public static WebArchive deployApplication() {
        return ArquillianDeployment.prepareStandardWebAppDeployment();
    }

    @Test
    public void shouldAddItemToTheRepository() {
        //given
        Item itemToBeAdded = new Item("test");

        //when
        itemRepository.save(itemToBeAdded);

        //then
        assertThat(itemRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldAllowAddingItemTwoTimes() {
        //given
        Item itemToBeAdded = new Item("test");

        //when
        itemToBeAdded = itemRepository.save(itemToBeAdded);
        itemRepository.save(itemToBeAdded);

        //then
        assertThat(itemRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldNotAllowSavingInvalidItem() {
        //given
        Item itemToBeAdded = new Item();

        //when
        itemRepository.save(itemToBeAdded);
        catchException(entityManager).flush();

        //then
        assertThat(caughtException()).isInstanceOf(ValidationException.class);
    }

}