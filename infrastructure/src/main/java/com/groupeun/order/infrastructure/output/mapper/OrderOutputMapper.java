package com.groupeun.order.infrastructure.output.mapper;

import com.groupeun.order.infrastructure.output.entity.OrderEntity;
import com.groupeun.order.domain.model.Order;

public class OrderOutputMapper {

    public static OrderEntity modelToEntity (Order model) {
        OrderEntity entity = new OrderEntity();
        entity.setId(model.getId());
        entity.setBuyer(model.getBuyer());
        model.getItems().stream()
                .map(ItemOutputMapper::modelToEntity)
                .forEach(entity.getItems()::add);

        entity.setPaid(model.isPaid());
        entity.setDeliveryDatetime(model.getDeliveryDatetime());
        entity.setDelivered(model.isDelivered());
        entity.setLastUpdateTimestamp(model.getLastUpdateTimestamp());
        entity.setCreationTimestamp(model.getCreationTimestamp());
        return entity;
    }

    public static Order entityToModel (OrderEntity entity) {
        Order model = new Order();
        model.setId(entity.getId());
        model.setBuyer(entity.getBuyer());
        entity.getItems().stream()
                .map(ItemOutputMapper::entityToModel)
                .forEach(model.getItems()::add);

        model.setPaid(entity.isPaid());
        model.setDeliveryDatetime(entity.getDeliveryDatetime());
        model.setDelivered(entity.isDelivered());
        model.setLastUpdateTimestamp(entity.getLastUpdateTimestamp());
        model.setCreationTimestamp(entity.getCreationTimestamp());
        return model;
    }
}
