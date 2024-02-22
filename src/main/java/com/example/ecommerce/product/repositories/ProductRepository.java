package com.example.ecommerce.product.repositories;

import com.example.ecommerce.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product save(Product product);
    Product findProductById(Long id);
    Product findByPriceBetween(double greaterThan, double lessThan);
    List<Product> findByIdIsNotNullOrderByPrice();
    List<Product> findAllByIsPublicFalse();

//    List<Product> findByTitleEquals(String title);
    List<Product> findByTitleEquals(String title, Pageable pageable);
}
