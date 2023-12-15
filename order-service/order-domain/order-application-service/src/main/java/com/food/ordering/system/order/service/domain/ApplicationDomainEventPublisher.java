package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationDomainEventPublisher implements DomainEventPublisher<OrderCreatedEvent> {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(orderCreatedEvent);
        log.info("OrderCreatedEvent is published for order id: {}", orderCreatedEvent.getOrder().getId().getValue());
    }
}
