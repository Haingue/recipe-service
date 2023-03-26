package com.groupeun.order.infrastructure.input.controller;

import com.groupeun.order.infrastructure.input.dto.OrderDto;
import com.groupeun.order.infrastructure.input.mapper.OrderInputMapper;
import com.groupeun.order.application.ports.input.OrderInputPort;
import com.groupeun.order.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/services/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderInputPort orderInputPort;

    @GetMapping
    public ResponseEntity<?> getOrder (@PathParam("orderId") String orderId, @PathParam("buyerId") String buyerId, Authentication authentication) {
        // TODO add user/roles checking (Keycloak object)
        if (orderId != null) {
            logger.info("Load one order: {}", orderId);
            Order order = orderInputPort.findOne(UUID.fromString(orderId));
            return ResponseEntity.ok(OrderInputMapper.modelToDto(order));
        } else if (buyerId != null) {
            logger.info("Load all order with buyer : {}", buyerId);
            List<Order> orders = orderInputPort.findAllByBuyer(UUID.fromString(orderId));
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders.stream()
                    .map(OrderInputMapper::modelToDto).collect(Collectors.toList()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/not-delivered")
    public ResponseEntity<?> getOrderNotDelivered (@PathParam("buyerId") String buyerId, Authentication authentication) {
        // TODO add user/roles checking (Keycloak object)
        if (buyerId != null) {
            logger.info("Load all order not delivered with buyer : {}", buyerId);
            List<Order> orders = orderInputPort.findAllNotDeliveredByBuyer(UUID.fromString(buyerId));
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders.stream()
                    .map(OrderInputMapper::modelToDto).collect(Collectors.toList()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder (@RequestBody OrderDto orderDto, Authentication authentication) {
        logger.info("Add new order {}: {}", authentication.getName(), orderDto.getId());
        Order order = orderInputPort.create(OrderInputMapper.dtoToModel(orderDto));
        return new ResponseEntity(OrderInputMapper.modelToDto(order), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder (@RequestBody OrderDto orderDto, Authentication authentication) {
        logger.info("Update order by {}: {}", authentication.getName(), orderDto.getId());
        Order order = orderInputPort.update(OrderInputMapper.dtoToModel(orderDto));
        return new ResponseEntity(OrderInputMapper.modelToDto(order), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity deleteOrderById (@RequestBody OrderDto orderDto, @RequestParam UUID id, Authentication authentication) {
        if (id == null) {
            logger.info("Delete order: {}", orderDto.getId());
            orderInputPort.delete(orderDto.getId());
        } else {
            logger.info("Delete order: {}", id);
            orderInputPort.delete(id);
        }
        return ResponseEntity.ok().build();
    }
}
