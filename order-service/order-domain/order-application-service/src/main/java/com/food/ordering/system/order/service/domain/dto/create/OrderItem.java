package com.food.ordering.system.order.service.domain.dto.create;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderItem {
    @NotNull
    final UUID productId;
    @NotNull
    final Integer quantity;
    @NotNull
    final BigDecimal price;
    @NotNull
    final BigDecimal subTotal;
}
