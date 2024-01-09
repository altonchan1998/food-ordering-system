package com.food.ordering.system.order.service.application.rest;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {
    @Autowired
    private OrderApplicationService orderApplicationService;

    @PostMapping
    public CreateOrderResponse createOrder(
            @RequestBody
            CreateOrderCommand createOrderCommand
    ) {
        log.info("Creating order for customer: {} at restaurant: {}", createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with tracking id: {}", createOrderResponse.getOrderTrackingId());
        return createOrderResponse;
    }

    @GetMapping("/{trackingId}")
    public TrackOrderResponse getOrderByTrackingId(
            @PathVariable
            UUID trackingId
    ) {
        TrackOrderResponse trackOrderResponse = orderApplicationService.trackOrder(
                TrackOrderQuery.builder()
                        .orderTrackingId(trackingId)
                        .build()
        );
        log.info("Returning order status with tracking id: {}", trackingId);
        return trackOrderResponse;
    }
}
