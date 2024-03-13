package com.food.ordering.system.order.service.domain.outbox.model.scheduler.approval;

import com.food.ordering.system.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.food.ordering.system.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.food.ordering.system.order.service.domain.outbox.model.scheduler.payment.PaymentOutboxHelper;
import com.food.ordering.system.outbox.OutboxScheduler;
import com.food.ordering.system.outbox.OutboxStatus;
import com.food.ordering.system.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantApprovalOutboxCleanerScheduler implements OutboxScheduler {
    private final ApprovalOutboxHelper approvalOutboxHelper;

    @Scheduled(cron = "@midnight")
    @Override
    public void processOutboxMessage() {
        List<OrderApprovalOutboxMessage> orderApprovalOutboxMessages = approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        ).orElse(List.of());

        if (orderApprovalOutboxMessages.isEmpty()) return;

        log.info(
                "Received {} OrderApprovalOutboxMessage for clean-up. The payloads: {}",
                orderApprovalOutboxMessages.size(),
                orderApprovalOutboxMessages.stream()
                        .map(OrderApprovalOutboxMessage::getPayload)
                        .collect(Collectors.joining("\n"))
        );

        approvalOutboxHelper.deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        );

        log.info("{} OrderApprovalOutboxMessage deleted", orderApprovalOutboxMessages.size());
    }
}
