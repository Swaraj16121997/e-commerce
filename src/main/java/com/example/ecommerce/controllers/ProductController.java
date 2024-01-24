package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.FakeStoreProductDto;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ClientProductService;
import com.example.ecommerce.services.FakeStoreProductService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/products")
public class ProductController {    // will always answer to "/products"

    private ClientProductService clientProductService;

    @Autowired
    public ProductController(FakeStoreProductService fakeStoreProductService) {
        this.clientProductService = fakeStoreProductService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(clientProductService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")     // way - 1
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId){  // dispatcher servlet infos like status code, response body etc. on top of this while sending back the response.
        try {
            Product product = clientProductService.getSingleProduct(productId);      // Spring internally is doing the conversion from object to json using jackson library while sending back the request

            // for setting response headers
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json");
            headers.add("auth-token", "some_random_token");

            return new ResponseEntity<>(product, headers, HttpStatus.OK);
        } catch (Exception exception){
            throw exception;
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<Product> addNewProduct(@RequestBody FakeStoreProductDto fakeStoreProductDto){
        Product product = clientProductService.addNewProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PatchMapping("/update-product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody FakeStoreProductDto fakeStoreProductDto){
        Product product = clientProductService.updateProduct(productId, fakeStoreProductDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}
