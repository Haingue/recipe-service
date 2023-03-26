package com.groupeun.order.infrastructure.output.repository;

import com.groupeun.order.infrastructure.output.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {

    List<OrderEntity> findAllByBuyerOrderByCreationTimestampDesc (UUID buyerId);

    List<OrderEntity> findAllByBuyerAndDeliveredOrderByCreationTimestampDesc (UUID buyerId, boolean delivered);

}
