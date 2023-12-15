package com.food.ordering.system.order.service.domain.dto.create;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderAddress {
    @NotNull
    @Max(value = 50)
    final String street;
    @NotNull
    @Max(value = 10)
    final String postalCode;
    @NotNull
    @Max(value = 50)
    final String city;
}
