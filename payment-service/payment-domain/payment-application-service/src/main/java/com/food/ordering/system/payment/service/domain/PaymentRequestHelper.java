package com.food.ordering.system.payment.service.domain;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.exception.PaymentApplicationServiceException;
import com.food.ordering.system.payment.service.domain.mapper.PaymentDataMapper;
import com.food.ordering.system.payment.service.domain.port.output.message.publisher.PaymentCancelledMessagePublisher;
import com.food.ordering.system.payment.service.domain.port.output.message.publisher.PaymentCompletedMessagePublisher;
import com.food.ordering.system.payment.service.domain.port.output.message.publisher.PaymentFailedMessagePublisher;
import com.food.ordering.system.payment.service.domain.port.output.repository.CreditEntryRepository;
import com.food.ordering.system.payment.service.domain.port.output.repository.CreditHistoryRepository;
import com.food.ordering.system.payment.service.domain.port.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentRequestHelper {
    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;
    private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
    private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistories(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(
                payment,
                creditEntry,
                creditHistories,
                failureMessages,
                paymentCompletedMessagePublisher,
                paymentFailedMessagePublisher
        );

        persistDbObject(payment, creditEntry, creditHistories, failureMessages);

        return paymentEvent;
    }

    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment rollback event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId()))
                .orElseThrow(() -> {
                    log.error("Payment with order id: {} could not be found.", paymentRequest.getOrderId());
                    return new PaymentApplicationServiceException("Payment with order id: " + paymentRequest.getOrderId() + " could not be found.");
                });

        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistories(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndCancelPayment(
                payment,
                creditEntry,
                creditHistories,
                failureMessages,
                paymentCancelledMessagePublisher,
                paymentFailedMessagePublisher
        );

        persistDbObject(payment, creditEntry, creditHistories, failureMessages);

        return paymentEvent;
    }

    private void persistDbObject(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        paymentRepository.save(payment);
        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }

    private List<CreditHistory> getCreditHistories(CustomerId customerId) {
        return creditHistoryRepository.findByCustomerId(customerId)
                .orElseThrow(() -> {
                    log.error("Could not find credit history for customer: {}", customerId.getValue());
                    return new PaymentApplicationServiceException("Could not find credit history for customer: " + customerId.getValue());
                });
    }

    private CreditEntry getCreditEntry(CustomerId customerId) {
        return creditEntryRepository.findByCustomerId(customerId)
                .orElseThrow(() -> {
                    log.error("Could not find credit entry for customer: {}", customerId.getValue());
                    return new PaymentApplicationServiceException("Could not find credit entry for customer: " + customerId.getValue());
                });
    }
}
