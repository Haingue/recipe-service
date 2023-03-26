package com.groupeun.order.domain.service;

import com.groupeun.order.application.ports.output.ItemOutputPort;
import com.groupeun.order.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@AllArgsConstructor
public class ItemService {

    private ItemOutputPort itemOutputPort;

    public boolean checkItemExist(Set<Item> items) {
        if (items.isEmpty()) return true;
        Set<Item> existingItems = itemOutputPort.getItems(items);
        return items.size() == existingItems.size();
    }

    public Set<Item> getItems(Set<Item> items) {
        if (items.isEmpty()) return Collections.emptySet();
        return itemOutputPort.getItems(items);
    }
}
