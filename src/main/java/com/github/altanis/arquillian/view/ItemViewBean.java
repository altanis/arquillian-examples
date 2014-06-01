package com.github.altanis.arquillian.view;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.github.altanis.arquillian.core.items.Item;
import com.github.altanis.arquillian.core.items.ItemRepository;

@Named
@RequestScoped
public class ItemViewBean {

    @Inject
    ItemRepository itemRepository;

    Item currentlyEditedItem = new Item();

    public List<Item> getItems() {
        return itemRepository.getAll();
    }

    public boolean containsElements() {
        return itemRepository.count() > 0;
    }

    public Item getCurrentlyEditedItem() {
        return currentlyEditedItem;
    }

    public void setCurrentlyEditedItem(Item currentlyEditedItem) {
        this.currentlyEditedItem = currentlyEditedItem;
    }

    @Transactional
    public void saveCurrentlyEditedItem(@NotNull @Valid Item itemToBeSaved) {
        if(itemToBeSaved != null) {
            itemRepository.save(itemToBeSaved);
        }
    }
}
