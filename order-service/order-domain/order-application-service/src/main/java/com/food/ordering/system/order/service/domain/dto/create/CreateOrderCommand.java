package com.food.ordering.system.order.service.domain.dto.create;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateOrderCommand {
    @NotNull
    final UUID customerId;
    @NotNull
    final UUID restaurantId;
    @NotNull
    final BigDecimal price;
    @NotNull
    final List<OrderItem> items;
    @NotNull
    final OrderAddress address;
}
