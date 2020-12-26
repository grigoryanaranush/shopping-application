package com.shopping.shoppingapplication.repository;

import com.shopping.shoppingapplication.domain.entity.ProductComment;
import org.springframework.data.repository.CrudRepository;

public interface ProductCommentRepository extends CrudRepository<ProductComment, Long> {
}
