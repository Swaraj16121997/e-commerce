package com.example.ecommerce.payment.strategies.payment_gateways;

public interface PaymentGatewayStrategy {
    String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount);
}