package com.example.ecommerce.payment.services;

import com.example.ecommerce.payment.strategies.payment_gateways.PaymentGatewayStrategy;
import com.example.ecommerce.payment.strategies.payment_gateways.PaymentGatewayStrategySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentGatewayStrategySelector paymentGatewayStrategySelector;

    public String initiatePayment(String orderId, String email, String phoneNumber, Long amount) {
        // Order order = orderService.getOrderDetails(orderId)
        // Long amount = order.getAmount();
        // double amount = 10.10; // store number * 100
        // String orderId, String email, String phoneNumber, Long amount
//        Long amount = 1010L; // 10.00 => 1000

        PaymentGatewayStrategy paymentGateway = paymentGatewayStrategySelector.getBestPaymentGateway();

        return paymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount);
    }
}

