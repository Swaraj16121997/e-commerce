package com.example.ecommerce.services;

import com.example.ecommerce.dtos.ProductDto;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Service
public class ProductService {

    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public ProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public Product getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> responseEntityProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}", ProductDto.class, productId); // Spring internally is doing the conversion from json to object using jackson library

        return getProduct(responseEntityProductDto.getBody());
    }

    public List<Product> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto[]> responseEntityProductDtoArray = restTemplate.getForEntity("https://fakestoreapi.com/products", ProductDto[].class);

        return getProductList(responseEntityProductDtoArray.getBody());
    }

    public Product addNewProduct(ProductDto productDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> responseEntityProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products", productDto, ProductDto.class);

        return getProduct(responseEntityProductDto.getBody());  // good practice to return back the object in the response body once product is added successfully
    }

    private Product getProduct(ProductDto productDto){  // converting product response dto to product using helper function
        Product product = new Product();
        Category category = new Category();

        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        product.setDescription(productDto.getDescription());

        return product;
    }

    private List<Product> getProductList(ProductDto[] productDtoArray){  // converting product response dto to product using helper function

        List<Product> productList = new LinkedList<>();

        for(ProductDto productDto : productDtoArray) {
            Product product = new Product();
            Category category = new Category();

            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            category.setName(productDto.getCategory());
            product.setCategory(category);
            product.setImageUrl(productDto.getImage());
            product.setDescription(productDto.getDescription());

            productList.add(product);
        }

        return productList;
    }


}
