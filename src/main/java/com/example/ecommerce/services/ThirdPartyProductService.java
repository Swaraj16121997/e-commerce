package com.example.ecommerce.services;

import com.example.ecommerce.dtos.ThirdPartyProductDto;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Service
public class ThirdPartyProductService {

    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public ThirdPartyProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public Product getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ThirdPartyProductDto> responseEntityProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}", ThirdPartyProductDto.class, productId); // Spring internally is doing the conversion from json to object using jackson library

        return getProduct(responseEntityProductDto.getBody());
    }

    public List<Product> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ThirdPartyProductDto[]> responseEntityProductDtoArray = restTemplate.getForEntity("https://fakestoreapi.com/products", ThirdPartyProductDto[].class);

        return getProductList(responseEntityProductDtoArray.getBody());
    }

    public Product addNewProduct(ThirdPartyProductDto thirdPartyProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ThirdPartyProductDto> responseEntityThirdPartyProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products", thirdPartyProductDto, ThirdPartyProductDto.class);

        return getProduct(responseEntityThirdPartyProductDto.getBody());  // good practice to return back the object in the response body once product is added successfully
    }

    public Product updateProduct(Long productId, ThirdPartyProductDto thirdPartyProductDto) {
        ResponseEntity<ThirdPartyProductDto> responseEntityThirdPartyResponseDto = httpMethodForEntity(HttpMethod.PATCH, "https://fakestoreapi.com/products/{productId}", thirdPartyProductDto, ThirdPartyProductDto.class, productId);

        return getProduct(responseEntityThirdPartyResponseDto.getBody());
    }
    private Product getProduct(ThirdPartyProductDto thirdPartyProductDto){  // converting product response dto to product using helper function
        Product product = new Product();
        Category category = new Category();

        product.setId(thirdPartyProductDto.getId());
        product.setTitle(thirdPartyProductDto.getTitle());
        product.setPrice(thirdPartyProductDto.getPrice());
        category.setName(thirdPartyProductDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(thirdPartyProductDto.getImage());
        product.setDescription(thirdPartyProductDto.getDescription());

        return product;
    }

    private List<Product> getProductList(ThirdPartyProductDto[] thirdPartyProductDtoArray){  // converting product response dto to product using helper function

        List<Product> productList = new LinkedList<>();

        for(ThirdPartyProductDto thirdPartyProductDto : thirdPartyProductDtoArray) {
            Product product = new Product();
            Category category = new Category();

            product.setId(thirdPartyProductDto.getId());
            product.setTitle(thirdPartyProductDto.getTitle());
            product.setPrice(thirdPartyProductDto.getPrice());
            category.setName(thirdPartyProductDto.getCategory());
            product.setCategory(category);
            product.setImageUrl(thirdPartyProductDto.getImage());
            product.setDescription(thirdPartyProductDto.getDescription());

            productList.add(product);
        }

        return productList;
    }
    private <T> ResponseEntity<T> httpMethodForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod.PATCH, requestCallback, responseExtractor, uriVariables);
    }
}