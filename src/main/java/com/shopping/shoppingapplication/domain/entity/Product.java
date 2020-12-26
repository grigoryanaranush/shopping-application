package com.shopping.shoppingapplication.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private List<ProductComment> productComments;
}
