package com.food.ordering.system.order.service.domain.outbox.model.scheduler.approval;

import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalRequestMessagePublisher;
import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantApprovalOutboxScheduler implements OutboxScheduler {
    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher;

    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}", initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    @Transactional
    @Override
    public void processOutboxMessage() {
        List<OrderApprovalOutboxMessage> orderApprovalOutboxMessages = approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus.STARTED, SagaStatus.PROCESSING)
                .orElse(List.of());

        if (orderApprovalOutboxMessages.isEmpty()) return;

        log.info(
                "Received {} OrderApprovalOutboxMessage with ids: {}, sending to message bus",
                orderApprovalOutboxMessages.size(),
                orderApprovalOutboxMessages.stream()
                        .map(OrderApprovalOutboxMessage::getId)
                        .map(UUID::toString)
                        .collect(Collectors.joining(","))
        );

        orderApprovalOutboxMessages.forEach(msg -> restaurantApprovalRequestMessagePublisher.publish(msg, this::updateOutboxStatus));

        log.info("{} OrderApprovalOutboxMessage sent to message bus", orderApprovalOutboxMessages.size());
    }

    private void updateOutboxStatus(OrderApprovalOutboxMessage orderApprovalOutboxMessage, OutboxStatus outboxStatus) {
        orderApprovalOutboxMessage.setOutboxStatus(outboxStatus);
        approvalOutboxHelper.save(orderApprovalOutboxMessage);
        log.info("OrderApprovalOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
