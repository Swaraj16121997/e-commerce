package com.example.ecommerce.product.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Rating {
    private double rate;
    private double count;
}
