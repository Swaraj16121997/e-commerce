package com.example.ecommerce.product.controllers;

import com.example.ecommerce.product.dtos.FakeStoreProductDto;
import com.example.ecommerce.product.dtos.MyProductDto;
import com.example.ecommerce.product.models.Product;
import com.example.ecommerce.product.security.JwtObject;
import com.example.ecommerce.product.security.TokenValidator;
import com.example.ecommerce.product.services.ClientProductService;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RestController
@RequestMapping("/products")
public class ProductController {    // will always answer to "/products"

    @Qualifier("myProductService")  // to tell spring to initialise specific product service to avoid ambiguity
    @Autowired
    private ClientProductService clientProductService;
    @Autowired
    private TokenValidator tokenValidator;

//    @Autowired
//    private FakeStoreProductService fakeStoreProductService;  //    This is just for the purpose of argumentCaptor test case

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(clientProductService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")     // way - 1
//  In order to validate user token in the product service, we need take it as an argument in the product controller in this way: getSingleProduct(@Nullable @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable Long productId) throws Exception
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId){  // dispatcher servlet infos like status code, response body etc. on top of this while sending back the response.
        try {
//            JwtObject jwtObject = null;
//            if(authToken != null){
//                Optional<JwtObject> authOptionalObject = tokenValidator.validateToken(authToken);
//                if(authOptionalObject.isEmpty())
//                    throw new Exception("Invalid token!!");
//
//                jwtObject = authOptionalObject.get();
//            }

//            The below line of code is just for argumentCaptor test case purpose as "thenCallRealMethod()" is not applicable on interface's object, it's applicable only on a concrete class's object. Hence, uncomment the below loc and comment the loc just after the below one. Remember to reverse it back once you're done with the testing.
//            Product product = fakeStoreProductService.getSingleProduct(productId);

//            Product product = clientProductService.getSingleProduct(productId,jwtObject);   // we can apply rules based on user roles in the product services
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
