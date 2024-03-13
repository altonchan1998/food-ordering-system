package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.outbox.model.scheduler.approval.ApprovalOutboxHelper;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.*;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {
// Why MockBean not working: https://stackoverflow.com/questions/66712011/why-does-bean-returning-a-mock-work-but-mockbean-doesnt-work-when-it-comes-to
//    @MockBean
//    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
//    @MockBean
//    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;
//    @MockBean
//    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;
//    @MockBean
//    public OrderRepository orderRepository;
//    @MockBean
//    public CustomerRepository customerRepository;
//    @MockBean
//    public RestaurantRepository restaurantRepository;


    @Bean
    public PaymentRequestMessagePublisher paymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }

    @Bean
    public RestaurantApprovalRequestMessagePublisher restaurantApprovalRequestMessagePublisher() {
        return Mockito.mock(RestaurantApprovalRequestMessagePublisher.class);
    }

    @Bean
    public PaymentOutboxRepository paymentOutboxRepository() {
        return Mockito.mock(PaymentOutboxRepository.class);
    }

    @Bean
    public ApprovalOutboxRepository approvalOutboxRepository() {
        return Mockito.mock(ApprovalOutboxRepository.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    };
}
