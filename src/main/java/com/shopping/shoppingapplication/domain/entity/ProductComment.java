package com.shopping.shoppingapplication.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product_comment",
        uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "product_id"})})
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    private Product product;
}
