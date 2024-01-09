package com.food.ordering.system.order.service.dataaccess.order.entity;

import com.food.ordering.system.domain.valueobject.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_orders")
@Entity
public class OrderEntity {
    @Id
    UUID id;

    UUID customerId;

    UUID restaurantId;

    UUID trackingId;

    BigDecimal price;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    String failureMessage;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    OrderAddressEntity address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItemEntity> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
