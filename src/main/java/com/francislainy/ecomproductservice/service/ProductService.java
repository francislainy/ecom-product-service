package com.francislainy.ecomproductservice.service;

import com.francislainy.ecomproductservice.model.Product;

import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);

    Product getProduct(UUID id);

    void deleteProduct(UUID id);
}
