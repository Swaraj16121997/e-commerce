package com.example.ecommerce.controllers;

import com.example.ecommerce.clients.FakeStoreClient;
import com.example.ecommerce.dtos.FakeStoreProductDto;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ClientProductService;
import com.example.ecommerce.services.FakeStoreProductService;
import com.example.ecommerce.services.MyProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Qualifier("myProductService")
    @MockBean   // could have also used @Mock instead
    ClientProductService clientProductService;

    @Autowired  // if @Mock had been used, then would have used @InjectMocks instead
    ProductController productController;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @MockBean
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    FakeStoreClient fakeStoreClient;

    @MockBean
    FakeStoreProductService fakeStoreProductService;

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

    @Test
    void testGetSingleProduct_whenProductControllerCallsProductService_thenVerifyId(){  // This test case is written to verify if the same product id passed in the product controller is being passed to the product service or not to demonstrate the concept of argument captor.
        Long id = 2L;

        when(restTemplateBuilder.build()).thenReturn(new RestTemplate());

        productController.getSingleProduct(id);

        verify(fakeStoreProductService).getSingleProduct(idCaptor.capture());   // to capture the id
        verify(fakeStoreProductService, times(1)).getSingleProduct(any());  // to verify no. of calls made to the Fake Store Product Service

        assertEquals(id, idCaptor.getValue());  // to check that the value of captured id matches (is equal to) the value of id passed in the argument or not
    }

}