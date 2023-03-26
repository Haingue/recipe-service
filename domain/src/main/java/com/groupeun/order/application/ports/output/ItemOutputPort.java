package com.groupeun.order.application.ports.output;

import com.groupeun.order.domain.model.Item;

import java.util.Set;

public interface ItemOutputPort {
    Set<Item> getItems(Set<Item> itemIds);
}
