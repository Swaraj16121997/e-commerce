package com.example.ecommerce.dtos;

import com.example.ecommerce.models.Rating;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ThirdPartyProductDto implements ClientProductDto{
    private Long id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
    private Rating rating;
}