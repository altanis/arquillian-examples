package com.github.altanis.arquillian.view;

import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import com.github.altanis.arquillian.deployment.ArquillianDeployment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class ItemViewBeanTest {

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentUrl;

    @Page
    ItemsPage itemsPage;

    @Deployment(testable = false)
    public static WebArchive deployApplication() {
        return ArquillianDeployment.prepareStandardWebAppDeployment();
    }

    @Test
    public void shouldAddItemToStock(/*@InitialPage ItemsPage itemsPage*/) throws Exception {
        // given
        browser.get(deploymentUrl.toExternalForm() + "index.xhtml");

        // when
        itemsPage.addItem("Drone sux or rux?");

        // then
        assertThat(itemsPage.getItems()).containsOnly("Drone sux or rux?");
    }

}