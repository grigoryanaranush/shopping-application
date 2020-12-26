package com.shopping.shoppingapplication.domain.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FilterProduct {
    private String name;

    private BigDecimal priceFrom;

    private BigDecimal priceTo;

    private Integer rating;
}
