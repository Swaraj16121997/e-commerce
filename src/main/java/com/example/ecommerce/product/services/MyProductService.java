package com.example.ecommerce.product.services;

import com.example.ecommerce.product.dtos.ClientProductDto;
import com.example.ecommerce.product.dtos.MyProductDto;
import com.example.ecommerce.product.models.Category;
import com.example.ecommerce.product.models.Product;
import com.example.ecommerce.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MyProductService implements ClientProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product addNewProduct(ClientProductDto clientProductDto) {
        return productRepository.save(getProduct((MyProductDto) clientProductDto));
    }

    @Override
    public Product updateProduct(Long productId, ClientProductDto clientProductDto) {
        return null;
    }

    // This is a helper function used for converting product response dto to product
    private Product getProduct(MyProductDto myProductDto){
        Product product = new Product();
        Category category = new Category();

        product.setId(myProductDto.getId());
        product.setTitle(myProductDto.getTitle());
        product.setPrice(myProductDto.getPrice());
        category.setName(myProductDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(myProductDto.getImage());
        product.setDescription(myProductDto.getDescription());

        return product;
    }

    // This is a helper function used for converting product response dto to product
    private List<Product> getProductList(MyProductDto[] myProductDtoArray){

        List<Product> productList = new LinkedList<>();

        for(MyProductDto myProductDto : myProductDtoArray) {
            Product product = new Product();
            Category category = new Category();

            product.setId(myProductDto.getId());
            product.setTitle(myProductDto.getTitle());
            product.setPrice(myProductDto.getPrice());
            category.setName(myProductDto.getCategory());
            product.setCategory(category);
            product.setImageUrl(myProductDto.getImage());
            product.setDescription(myProductDto.getDescription());

            productList.add(product);
        }

        return productList;
    }

}
