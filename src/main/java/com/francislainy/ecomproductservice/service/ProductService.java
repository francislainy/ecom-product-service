package com.francislainy.ecomproductservice.service;

import com.francislainy.ecomproductservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);

    Product getProduct(UUID id);

    void deleteProduct(UUID id);

    Page<Product> findAll(Pageable pageable);
}
