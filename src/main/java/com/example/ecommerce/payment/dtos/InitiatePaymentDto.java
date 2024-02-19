package com.example.ecommerce.payment.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {
    private String email;
    private String phoneNumber;
    private Long amount;
    private String orderId;
}