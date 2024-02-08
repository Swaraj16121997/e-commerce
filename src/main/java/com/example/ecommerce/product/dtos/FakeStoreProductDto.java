package com.example.ecommerce.product.dtos;

import com.example.ecommerce.product.models.Rating;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FakeStoreProductDto extends ClientProductDto{
    private Long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}
