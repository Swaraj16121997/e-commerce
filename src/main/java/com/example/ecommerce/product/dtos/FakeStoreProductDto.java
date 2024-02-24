package com.example.ecommerce.product.dtos;

import com.example.ecommerce.product.models.Rating;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class FakeStoreProductDto extends ClientProductDto implements Serializable {  // to store objects in byte stream into Redis Cache
    private Long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}
