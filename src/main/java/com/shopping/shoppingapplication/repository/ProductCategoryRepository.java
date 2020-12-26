package com.shopping.shoppingapplication.repository;

import com.shopping.shoppingapplication.domain.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
    List<ProductCategory> findAll();
}
