package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent {
    private final DomainEventPublisher<PaymentFailedEvent> eventPublisher;

    public PaymentFailedEvent(
            Payment payment,
            ZonedDateTime createdAt,
            List<String> failureMessages,
            DomainEventPublisher<PaymentFailedEvent> eventPublisher
    ) {
        super(payment, createdAt, failureMessages);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void fire() {
        this.eventPublisher.publish(this);
    }
}
