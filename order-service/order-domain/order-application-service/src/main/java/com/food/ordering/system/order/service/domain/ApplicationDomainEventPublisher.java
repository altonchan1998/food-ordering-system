package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationDomainEventPublisher implements DomainEventPublisher<OrderCreatedEvent> {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(orderCreatedEvent);
        log.info("OrderCreatedEvent is published for order id: {}", orderCreatedEvent.getOrder().getId().getValue());
    }
}
