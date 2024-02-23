package com.example.ecommerce.product.services;

import com.example.ecommerce.product.dtos.SearchResponseDto;
import com.example.ecommerce.product.models.Product;
import com.example.ecommerce.product.models.SortParam;
import com.example.ecommerce.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductRepository productRepository;    // Ideally the Search service should interact with some product service instead of directly interacting with product repository

    public List<Product> searchProducts(String query, int pageNumber, int sizeOfPage, List<SortParam> sortParamList) {

//        Sort sort = Sort.by("title").descending().and(Sort.by("price").descending());

        Sort sort;
        if(sortParamList.get(0).getSortType().equals("ASC")) {
            sort = Sort.by(sortParamList.get(0).getParamName());
        } else {
            sort = Sort.by(sortParamList.get(0).getParamName()).descending();
        }

        for(int i = 1; i< sortParamList.size(); i++) {
            if(sortParamList.get(i).getSortType().equals("ASC")) {
                sort = sort.and(Sort.by(sortParamList.get(i).getParamName()));
            } else {
                sort = sort.and(Sort.by(sortParamList.get(i).getParamName())
                        .descending());
            }
        }

        return productRepository.findByTitleEquals(query, PageRequest.of(pageNumber, sizeOfPage, sort));
    }
}
