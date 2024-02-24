package com.example.ecommerce.product.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Rating implements Serializable {
    private double rate;
    private double count;
}
