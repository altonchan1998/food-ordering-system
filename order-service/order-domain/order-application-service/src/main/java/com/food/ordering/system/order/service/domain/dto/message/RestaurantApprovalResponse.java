package com.food.ordering.system.order.service.domain.dto.message;

import com.food.ordering.system.domain.valueobject.OrderApprovalStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RestaurantApprovalResponse {
    @NotNull
    String id;
    String sagaId;
    String orderId;
    String restaurantId;
    Instant createdAt;
    OrderApprovalStatus orderApprovalStatus;
    List<String> failureMessages;
}
