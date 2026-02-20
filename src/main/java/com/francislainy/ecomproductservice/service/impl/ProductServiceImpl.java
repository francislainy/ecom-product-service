package com.francislainy.ecomproductservice.service.impl;

import com.francislainy.ecomproductservice.entity.ProductEntity;
import com.francislainy.ecomproductservice.model.Product;
import com.francislainy.ecomproductservice.repository.ProductRepository;
import com.francislainy.ecomproductservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {

        ProductEntity productEntity = product.toEntity();
        productEntity = productRepository.save(productEntity);
        return productEntity.toModel();
    }
}
