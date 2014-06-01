package com.github.altanis.arquillian.view;

import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@Location("index.xhtml")
public class ItemsPage {

    @Root
    private WebElement root;

    @FindBy(id = "form-newItem:input-itemName")
    private WebElement idCreateItemName;

    @FindBy(id = "form-newItem:button-addNewItem")
    private WebElement idCreateSubmitButton;

    @FindBy(id = "panel-items")
    private WebElement itemsPanel;

    public void addItem(String name) {
        idCreateItemName.sendKeys(name);
        guardHttp(idCreateSubmitButton).click();
    }

    public List<String> getItems() {
        List<String> items = new ArrayList<>();
        WebElement row = itemsPanel.findElement(By.id("table-items"));
        List<WebElement> cells = row.findElements(By.xpath(".//*[local-name(.)='td']"));
        for(int i = 1; i < cells.size(); i+= 2) {
            items.add(cells.get(i).getText());
        }
        return items;
    }

}
