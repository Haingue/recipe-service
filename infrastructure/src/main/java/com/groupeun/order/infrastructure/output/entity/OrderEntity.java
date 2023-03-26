package com.groupeun.order.infrastructure.output.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order")
public class OrderEntity {

    @Id
    private UUID id;
    @Column
    private UUID buyer;
    @Column
    private boolean paid;
    @Column
    private LocalDateTime deliveryDatetime;
    @Column
    private boolean delivered;
    @Column
    private Instant creationTimestamp;
    @Column
    private Instant lastUpdateTimestamp;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<ItemEntity> items = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
