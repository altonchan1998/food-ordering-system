package com.food.ordering.system.order.service.domain.outbox.model.scheduler.payment;

import com.food.ordering.system.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
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
public class PaymentOutboxCleanerScheduler implements OutboxScheduler {
    private final PaymentOutboxHelper paymentOutboxHelper;

    @Scheduled(cron = "@midnight")
    @Override
    public void processOutboxMessage() {
        List<OrderPaymentOutboxMessage> orderPaymentOutboxMessages = paymentOutboxHelper.getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        ).orElse(List.of());

        if (orderPaymentOutboxMessages.isEmpty()) return;

        log.info(
                "Received {} OrderPaymentOutboxMessage for clean-up. The payloads: {}",
                orderPaymentOutboxMessages.size(),
                orderPaymentOutboxMessages.stream()
                        .map(OrderPaymentOutboxMessage::getPayload)
                        .collect(Collectors.joining("\n"))
        );

        paymentOutboxHelper.deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        );

        log.info("{} OrderPaymentOutboxMessage deleted", orderPaymentOutboxMessages.size());
    }
}
