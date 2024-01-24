package com.example.ecommerce.clients;

import com.example.ecommerce.dtos.FakeStoreProductDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

// basically we have ensured SRP so that FakeStoreProductService is not burdened with more than one task, i.e, creating RestTemplate, making restTemplate method calls and converting response back
@Component
public class FakeStoreClient {
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDto getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntityProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}", FakeStoreProductDto.class, productId); // Spring internally is doing the conversion from json to object using jackson library

        return  responseEntityProductDto.getBody();
    }

    public FakeStoreProductDto[] getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntityProductDtoArray = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class);

        return  responseEntityProductDtoArray.getBody();
    }

    public FakeStoreProductDto addNewProduct(FakeStoreProductDto fakeStoreProductDto){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntityFakeStoreProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class);
        return responseEntityFakeStoreProductDto.getBody();
    }

    public FakeStoreProductDto updateProduct(Long productId, FakeStoreProductDto fakeStoreProductDto){
        ResponseEntity<FakeStoreProductDto> responseEntityThirdPartyResponseDto = httpMethodForEntity(HttpMethod.PATCH, "https://fakestoreapi.com/products/{productId}", fakeStoreProductDto, FakeStoreProductDto.class, productId);
        return responseEntityThirdPartyResponseDto.getBody();
    }
    private <T> ResponseEntity<T> httpMethodForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);

        return restTemplate.execute(url, httpMethod.PATCH, requestCallback, responseExtractor, uriVariables);
    }
}
