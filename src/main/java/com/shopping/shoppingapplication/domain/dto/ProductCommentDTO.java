package com.shopping.shoppingapplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCommentDTO {
    private String comment;

    private Integer rating;

    private Long userId;

    private Long productId;
}
