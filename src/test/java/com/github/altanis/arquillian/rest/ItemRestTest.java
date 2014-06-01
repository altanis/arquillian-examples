package com.github.altanis.arquillian.rest;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import com.github.altanis.arquillian.core.items.Item;
import com.github.altanis.arquillian.core.items.ItemRepository;
import com.github.altanis.arquillian.deployment.ArquillianDeployment;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(ArquillianCucumber.class)
@Features("/bdd")
public class ItemRestTest {

    @Inject
    ItemRepository itemRepository;

    @Deployment(testable = false)
    public static WebArchive deployApplication() {
        return ArquillianDeployment.prepareBddWebAppDeployment();
    }

    @Given("^Empty items stock$")
    public void emptyItemsStock() throws Exception {
        assertThat(itemRepository.getAll()).hasSize(0);
    }

    @When("^Adding \"([^\"]*)\"$")
    public void addAnItem(String itemName) throws Exception {
        itemRepository.save(new Item(itemName));
    }

    @Then("^Stock contains \"([^\"]*)\" element$")
    public void checkIfStockContainsNumberOfElements(int numberOfElements) throws Exception {
        assertThat(itemRepository.getAll()).hasSize(numberOfElements);
    }


}