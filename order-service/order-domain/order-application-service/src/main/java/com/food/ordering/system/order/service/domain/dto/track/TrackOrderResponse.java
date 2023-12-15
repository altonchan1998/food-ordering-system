package com.food.ordering.system.order.service.domain.dto.track;

import com.food.ordering.system.domain.valueobject.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TrackOrderResponse {
    @NotNull
    final UUID orderTrackingId;
    @NotNull
    final OrderStatus orderStatus;
    final List<String> failureMessages;

}
