package com.example.ecommerce.product.controllers;

import com.example.ecommerce.product.dtos.SearchRequestDto;
import com.example.ecommerce.product.dtos.SearchResponseDto;
import com.example.ecommerce.product.models.Product;
import com.example.ecommerce.product.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @PostMapping
    public List<SearchResponseDto> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {

//        List<Product> result = searchService.searchProducts(searchRequestDto.getQuery(),
//                searchRequestDto.getPageNumber(), searchRequestDto.getSizeOfPage(), searchRequestDto.getSortParamList());

        List<Product> result = searchService.searchProducts(searchRequestDto.getQuery(),
                searchRequestDto.getPageNumber(), searchRequestDto.getSizeOfPage());

        List<SearchResponseDto> shareableResult = new LinkedList<>();
        for(Product product : result) {
            shareableResult.add(getProduct(product));
        }

        return shareableResult;
    }

    private SearchResponseDto getProduct(Product p) {
        SearchResponseDto product = new SearchResponseDto();
        product.setId(p.getId());
        product.setTitle(p.getTitle());
        product.setPrice(p.getPrice());
        product.setDescription(p.getDescription());
        return product;
    }
}