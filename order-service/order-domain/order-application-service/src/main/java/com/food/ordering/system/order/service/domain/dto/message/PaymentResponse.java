package com.food.ordering.system.order.service.domain.dto.message;

import com.food.ordering.system.domain.valueobject.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentResponse {
    @NotNull
    String id;
    String sagaId;
    String orderId;
    String paymentId;
    String customerId;
    BigDecimal price;
    Instant createdAt;
    PaymentStatus paymentStatus;
    List<String> failureMessages;
}
