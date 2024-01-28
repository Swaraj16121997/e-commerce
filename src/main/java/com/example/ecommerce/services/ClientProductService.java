package com.example.ecommerce.services;

import com.example.ecommerce.dtos.ClientProductDto;
import com.example.ecommerce.dtos.FakeStoreProductDto;
import com.example.ecommerce.models.Product;

import java.util.List;

public interface ClientProductService {
    Product getSingleProduct(Long productId);

    List<Product> getAllProducts();

    Product addNewProduct(ClientProductDto clientProductDto);

    Product updateProduct(Long productId, ClientProductDto clientProductDto);
}
