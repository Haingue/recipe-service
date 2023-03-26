package com.groupeun.order.infrastructure.output.mapper;

import com.groupeun.order.infrastructure.output.entity.ItemEntity;
import com.groupeun.order.domain.model.Item;

public class ItemOutputMapper {

    public static ItemEntity modelToEntity (Item model) {
        ItemEntity entity = new ItemEntity();
        entity.setId(model.getId());
        entity.setQuantity(model.getQuantity());
        return entity;
    }

    public static Item entityToModel (ItemEntity entity) {
        Item model = new Item();
        model.setId(entity.getId());
        model.setQuantity(entity.getQuantity());
        return model;
    }
}
