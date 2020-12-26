package com.shopping.shoppingapplication.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {
    private Long id;

    private String name;

    private String description;

}
