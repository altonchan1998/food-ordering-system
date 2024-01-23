package com.food.ordering.system.order.service.dataaccess.customer.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_customer_m_view", schema = "customer")
@Entity
public class CustomerEntity {
    @Id
    UUID id;

}
