package com.example.ecommerce.product.dtos;

import com.example.ecommerce.product.models.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponseDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}
