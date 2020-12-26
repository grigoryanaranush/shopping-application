package com.shopping.shoppingapplication.service.impl;

import com.shopping.shoppingapplication.domain.dto.ProductCommentDTO;
import com.shopping.shoppingapplication.domain.entity.Product;
import com.shopping.shoppingapplication.domain.entity.ProductComment;
import com.shopping.shoppingapplication.domain.entity.User;
import com.shopping.shoppingapplication.exception.product.ProductNotFoundException;
import com.shopping.shoppingapplication.exception.user.UserNotFoundException;
import com.shopping.shoppingapplication.repository.ProductCommentRepository;
import com.shopping.shoppingapplication.repository.ProductRepository;
import com.shopping.shoppingapplication.repository.UserRepository;
import com.shopping.shoppingapplication.service.RateProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateProductServiceImpl implements RateProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductCommentRepository productCommentRepository;

    public RateProductServiceImpl(UserRepository userRepository,
                                  ProductRepository productRepository,
                                  ProductCommentRepository productCommentRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productCommentRepository = productCommentRepository;
    }

    @Override
    public void rateProduct(ProductCommentDTO productCommentDTO) throws UserNotFoundException, ProductNotFoundException {
        Optional<User> userOptional = userRepository.findById(productCommentDTO.getUserId());
        Optional<Product> productOptional = productRepository.findById(productCommentDTO.getProductId());

        if(userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        User user = userOptional.get();
        Product product = productOptional.get();

        ProductComment productComment = ProductComment.builder()
                .product(product)
                .user(user)
                .comment(productCommentDTO.getComment())
                .rating(productCommentDTO.getRating())
                .build();

        productCommentRepository.save(productComment);
    }
}
