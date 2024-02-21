package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderSagaHelper {
    private final OrderRepository orderRepository;

    Order findOrder(String orderId) {
        return orderRepository.findByOrderId(new OrderId(UUID.fromString(orderId)))
                .orElseThrow(() -> {
                    log.error("Order with id: {} could not be found", orderId);
                    return new OrderNotFoundException("Order with id: " + orderId + " could not be found");
                });
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
