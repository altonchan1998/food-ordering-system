package com.food.ordering.system.order.service.domain;


import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class OrderTrackCommandHandler {

    @Autowired
    OrderDataMapper orderDataMapper;
    @Autowired
    OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Order order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()))
                .orElseThrow(() -> {
                    log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
                    return new OrderNotFoundException("Could not find order with tracking id: " + trackOrderQuery.getOrderTrackingId());
                });

        return orderDataMapper.orderToTrackOrderResponse(order);
    }
}
