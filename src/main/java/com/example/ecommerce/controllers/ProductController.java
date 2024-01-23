package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.ThirdPartyProductDto;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ThirdPartyProductService;
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

    private ThirdPartyProductService thirdPartyProductService;

    @Autowired
    public ProductController(ThirdPartyProductService thirdPartyProductService) {
        this.thirdPartyProductService = thirdPartyProductService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(thirdPartyProductService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")     // way - 1
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId){  // dispatcher servlet infos like status code, response body etc. on top of this while sending back the response.
        try {
            Product product = thirdPartyProductService.getSingleProduct(productId);      // Spring internally is doing the conversion from object to json using jackson library while sending back the request

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
    public ResponseEntity<Product> addNewProduct(@RequestBody ThirdPartyProductDto thirdPartyProductDto){
        Product product = thirdPartyProductService.addNewProduct(thirdPartyProductDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PatchMapping("/update-product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ThirdPartyProductDto thirdPartyProductDto){
        Product product = thirdPartyProductService.updateProduct(productId, thirdPartyProductDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}
