package com.groupeun.order.application.ports.output;

import com.groupeun.order.domain.model.Item;
import com.groupeun.order.domain.model.Order;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderOutputPort {

    Optional<Order> findOne (UUID id);
    List<Order> findAll ();
    List<Order> findAllByBuyer (UUID buyer);
    List<Order> findAllByBuyerAndDelivered(UUID buyer);

    Optional<Order> create (UUID id, Set<Item> items, UUID buyer, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered, Instant creationTimestamp, Instant lastUpdateTimestamp);
    Optional<Order> update (UUID id, Set<Item> items, UUID buyer, boolean isPaid, LocalDateTime deliveryDatetime, boolean isDelivered, Instant lastUpdateTimestamp);

    void delete (UUID id);

}
