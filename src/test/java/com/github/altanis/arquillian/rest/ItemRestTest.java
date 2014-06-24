package com.github.altanis.arquillian.rest;

import java.net.URI;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import com.github.altanis.arquillian.core.items.Item;
import com.github.altanis.arquillian.deployment.ArquillianDeployment;
import com.github.altanis.arquillian.rest.config.RestConfig;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.ArquillianCucumber;
import cucumber.runtime.arquillian.api.Features;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(ArquillianCucumber.class)
@Features("/bdd")
public class ItemRestTest {

    @ArquillianResource
    private URI webAppAddress;

    private int lastHttpStatus;

    @Deployment(testable = false)
    public static WebArchive deployApplication() {
        return ArquillianDeployment.prepareStandardWebAppDeployment();
    }

    @Given("^Standard items stock$")
    public void emptyItemsStock() throws Throwable {
    }

    @When("^Adding item with (\\w+)$")
    public void addingItemWithName(String name) throws Throwable {
        Response response = getRestClient().post(Entity.entity(new Item(name), MediaType.APPLICATION_JSON),
                Response.class);
        lastHttpStatus = response.getStatus();
    }

    @Then("^API return status is (\\d+)$")
    public void apiReturnStatusIs(int httpReturnCode) throws Throwable {
        assertThat(lastHttpStatus).isEqualTo(httpReturnCode);
    }

    private Invocation.Builder getRestClient() {
        return ClientBuilder.newClient().target(webAppAddress).path(
                RestConfig.APPLICATION_PATH + "/" + ItemRest.ITEM_REST_PATH).request(MediaType.APPLICATION_JSON_TYPE);
    }

}