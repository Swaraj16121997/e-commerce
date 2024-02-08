package com.example.ecommerce.product.services;

import com.example.ecommerce.product.dtos.ClientProductDto;
import com.example.ecommerce.product.models.Product;

import java.util.List;

public interface ClientProductService {
    Product getSingleProduct(Long productId);

    List<Product> getAllProducts();

    Product addNewProduct(ClientProductDto clientProductDto);

    Product updateProduct(Long productId, ClientProductDto clientProductDto);
}
