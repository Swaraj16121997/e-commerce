package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.ProductDto;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ProductService;
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

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")     // way - 1
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId){  // dispatcher servlet infos like status code, response body etc. on top of this while sending back the response.
        try {
            Product product = productService.getSingleProduct(productId);      // Spring internally is doing the conversion from object to json using jackson library while sending back the request

            // for setting response headers
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add("Contect-Type", "application/json");
            headers.add("auth-token", "some_random_token");

            return new ResponseEntity<>(product, headers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto productDto){
        Product product = productService.addNewProduct(productDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}
