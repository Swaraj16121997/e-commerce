package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category save(Category category);
    Category findById(long id);
    @Query("SELECT c.name FROM Category c WHERE c.id = :id") // static parameter example -> replace "c.id = :id" with "c.id = ?1" and remove @Param("id") annotation
    String findCategoryNameById(@Param("id") long id);
}
