package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ClientProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Qualifier("myProductService")
    @MockBean   // could have also used @Mock instead
    ClientProductService clientProductService;

    @Autowired  // if @Mock had been used, then would have used @InjectMocks instead
    ProductController productController;

    @Test
    void testGetSingleProduct_whenCalled_thenReturnProduct(){   // basically we are testing that if product service is working fine then our product controller will also work fine

        Product product = new Product();
        product.setId(2L);
        product.setTitle("Test");

        // Arrange or Create
        when(clientProductService.getSingleProduct(any(Long.class))).thenReturn(product);

        // Act or Call
        ResponseEntity<Product> productResponseEntity = productController.getSingleProduct(2L);

        // Assert or Check
        assertNotNull(productResponseEntity);
        assertEquals("Test", productResponseEntity.getBody().getTitle());
    }

    @Test
    void testGetSingleProduct_whenCalled_thenThrowException(){
        when(clientProductService.getSingleProduct(any(Long.class))).thenThrow(new RuntimeException("something went wrong"));
        assertThrows(RuntimeException.class, () -> productController.getSingleProduct(2L));
    }
}