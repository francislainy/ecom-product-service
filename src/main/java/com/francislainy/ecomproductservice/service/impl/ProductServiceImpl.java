package com.francislainy.ecomproductservice.service.impl;

import com.francislainy.ecomproductservice.entity.ProductEntity;
import com.francislainy.ecomproductservice.mapper.ProductMapper;
import com.francislainy.ecomproductservice.model.Product;
import com.francislainy.ecomproductservice.repository.ProductRepository;
import com.francislainy.ecomproductservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productRepository.save(productMapper.toEntity(product));
        return productMapper.toModel(productEntity);
    }

    @Override
    public Product getProduct(UUID id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toModel(productEntity);
    }
}
