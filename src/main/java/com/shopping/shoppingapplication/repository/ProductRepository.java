package com.shopping.shoppingapplication.repository;


import com.shopping.shoppingapplication.domain.entity.Product;
import com.shopping.shoppingapplication.domain.filter.FilterProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();

    @Query("select p from Product p" +
            " join ProductComment pc on pc.product.id = p.id" +
            " where " +
            " (:#{#filterProduct.name} is null or p.name like %:#{#filterProduct.name}%)" +
            " and (:#{#filterProduct.priceFrom} is null or p.price >= :#{#filterProduct.priceFrom})" +
            " and (:#{#filterProduct.priceTo} is null or p.price <= :#{#filterProduct.priceTo})  " +
            " and (:#{#filterProduct.rating} is null or pc.rating = :#{#filterProduct.rating})")
    List<Product> filterProducts(@Param("filterProduct") FilterProduct filterProduct);
}
