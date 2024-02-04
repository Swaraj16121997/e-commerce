package com.example.ecommerce.controllers;

import com.example.ecommerce.dtos.ClientProductDto;
import com.example.ecommerce.dtos.FakeStoreProductDto;
import com.example.ecommerce.dtos.MyProductDto;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.ClientProductService;
import com.example.ecommerce.services.FakeStoreProductService;
import com.example.ecommerce.services.MyProductService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("myProductService")  // to tell spring to initialise specific product service to avoid ambiguity
    @Autowired
    private ClientProductService clientProductService;

//    @Autowired
//    private FakeStoreProductService fakeStoreProductService;  //    This is just for the purpose of argumentCaptor test case

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(clientProductService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")     // way - 1
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId) {  // dispatcher servlet infos like status code, response body etc. on top of this while sending back the response.
        try {
//            The below line of code is just for argumentCaptor test case purpose as "thenCallRealMethod()" is not applicable on interface's object, it's applicable only on a concrete class's object. Hence, uncomment the below loc and comment the loc just after the below one. Remember to reverse it back once you're done with the testing.
//            Product product = fakeStoreProductService.getSingleProduct(productId);

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

    @PostMapping("/add-my-product")
    public ResponseEntity<Product> addMyProduct(@RequestBody MyProductDto myProductDto){
        Product product = clientProductService.addNewProduct(myProductDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PatchMapping("/update-product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody FakeStoreProductDto fakeStoreProductDto){
        Product product = clientProductService.updateProduct(productId, fakeStoreProductDto);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

}
