package com.francislainy.ecomproductservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    @NotNull
    private BigDecimal price;
}
