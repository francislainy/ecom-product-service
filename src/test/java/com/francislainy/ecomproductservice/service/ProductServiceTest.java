package com.francislainy.ecomproductservice.service;

import com.francislainy.ecomproductservice.entity.ProductEntity;
import com.francislainy.ecomproductservice.mapper.ProductMapper;
import com.francislainy.ecomproductservice.model.Product;
import com.francislainy.ecomproductservice.repository.ProductRepository;
import com.francislainy.ecomproductservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Spy
    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void shouldCreateProduct() {
        Product product = Product.builder()
                .name("Test Product")
                .price(new BigDecimal("99.99"))
                .description("Test Description")
                .build();

        ProductEntity productEntity = productMapper.toEntity(product);
        when(productRepository.save(any())).thenReturn(productEntity.withId(UUID.randomUUID()));

        Product result = productService.createProduct(product);

        assertAll(
                () -> assertNotNull(result.getId()),
                () -> assertEquals(product.getName(), result.getName()),
                () -> assertEquals(product.getDescription(), result.getDescription()),
                () -> assertEquals(product.getPrice(), result.getPrice())
        );

        verify(productRepository, times(1)).save(any());
    }
}
