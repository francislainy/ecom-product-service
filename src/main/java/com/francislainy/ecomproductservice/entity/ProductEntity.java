package com.francislainy.ecomproductservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@With
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
}
