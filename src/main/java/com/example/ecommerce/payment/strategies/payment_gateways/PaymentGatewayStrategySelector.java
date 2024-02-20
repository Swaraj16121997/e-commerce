package com.example.ecommerce.payment.strategies.payment_gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayStrategySelector {
//    @Autowired
//    private RazorPayPaymentGatewayStrategy razorPayPaymentGatewayStrategy;
    @Autowired
    private StripePaymentGatewayStrategy stripePaymentGatewayStrategy;


    public PaymentGatewayStrategy getBestPaymentGateway() {
        // We usually choose the best payment gateway based on various parameters. But for simplicity purpose, we will pick a random one as shown below.
//        int randomInt = new Random().nextInt();
//
//        if (randomInt % 2 == 0) {
//            return razorpayPaymentGateway;
//        }
//
        return stripePaymentGatewayStrategy;
//        return razorPayPaymentGatewayStrategy;
    }
}