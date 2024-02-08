package com.example.ecommerce.product.controllers;

import com.example.ecommerce.product.controllers.ProductController;
import com.example.ecommerce.product.dtos.ClientProductDto;
import com.example.ecommerce.product.models.Product;
import com.example.ecommerce.product.services.ClientProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Qualifier("myProductService")
    @MockBean
    ClientProductService clientProductService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());

        when(clientProductService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(products)));    // to convert from json to string
    }

    @Test
    void createProduct() throws Exception {
        Product productToCreate = new Product();
        productToCreate.setTitle("macbook pro air");
        productToCreate.setImageUrl("mc-pro-air-image.jpg");
        productToCreate.setDescription("best business laptop in the market");

        Product expectedProduct = new Product();
        expectedProduct.setId(4L);
        expectedProduct.setTitle("macbook pro air");
        expectedProduct.setImageUrl("mc-pro-air-image.jpg");
        expectedProduct.setDescription("best business laptop in the market");

        when(clientProductService.addNewProduct(any(ClientProductDto.class))).thenReturn(expectedProduct);

        mockMvc.perform(post("/products/add-my-product")
                        .contentType(MediaType.APPLICATION_JSON)    // media type of input request
                        .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedProduct)));

    }
}