package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class OrderCreatedEventApplicationListener {
    @Autowired
    private OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    @TransactionalEventListener
    public void process(OrderCreatedEvent orderCreatedEvent) {

    }
}
