package com.francislainy.ecomproductservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
}
