package com.example.ecommerce.services;

import com.example.ecommerce.clients.FakeStoreClient;
import com.example.ecommerce.dtos.ClientProductDto;
import com.example.ecommerce.dtos.FakeStoreProductDto;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Service
public class FakeStoreProductService implements ClientProductService {

    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Autowired
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product getSingleProduct(Long productId){
        FakeStoreProductDto fakeStoreProductDto = fakeStoreClient.getSingleProduct(productId);
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts(){
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreClient.getAllProducts();
        return getProductList(fakeStoreProductDtos);
    }

    @Override
    public Product addNewProduct(ClientProductDto clientProductDto) {
        FakeStoreProductDto fakeStoreProductDTO = fakeStoreClient.addNewProduct((FakeStoreProductDto) clientProductDto);
        return getProduct(fakeStoreProductDTO);  // good practice to return back the object in the response body once product is added successfully
    }

    @Override
    public Product updateProduct(Long productId, ClientProductDto clientProductDto) {
        FakeStoreProductDto fakeStoreProductDTO = fakeStoreClient.updateProduct(productId, (FakeStoreProductDto) clientProductDto);
        return getProduct(fakeStoreProductDTO);
    }

    // This is a helper function used for converting product response dto to product
    private Product getProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        Category category = new Category();

        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());

        return product;
    }

    // This is a helper function used for converting product response dto to product
    private List<Product> getProductList(FakeStoreProductDto[] fakeStoreProductDtoArray){

        List<Product> productList = new LinkedList<>();

        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoArray) {
            Product product = new Product();
            Category category = new Category();

            product.setId(fakeStoreProductDto.getId());
            product.setTitle(fakeStoreProductDto.getTitle());
            product.setPrice(fakeStoreProductDto.getPrice());
            category.setName(fakeStoreProductDto.getCategory());
            product.setCategory(category);
            product.setImageUrl(fakeStoreProductDto.getImage());
            product.setDescription(fakeStoreProductDto.getDescription());

            productList.add(product);
        }

        return productList;
    }

}