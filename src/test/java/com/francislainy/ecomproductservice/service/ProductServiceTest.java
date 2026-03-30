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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
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

    @Test
    void shouldGetProductById() {
        UUID productId = UUID.randomUUID();
        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .name("Test Product")
                .price(new BigDecimal("99.99"))
                .description("Test Description")
                .build();

        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(productEntity));

        Product result = productService.getProduct(productId);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(productId, result.getId()),
                () -> assertEquals(productEntity.getName(), result.getName()),
                () -> assertEquals(productEntity.getDescription(), result.getDescription()),
                () -> assertEquals(productEntity.getPrice(), result.getPrice())
        );

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundOnRetrieval() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProduct(productId));
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldDeleteProduct() {
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(true);

        assertDoesNotThrow(() -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundOnDelete() {
        UUID productId = UUID.randomUUID();

        Exception exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId));
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).existsById(productId);
    }

    @Test
    void shouldGetAllProductsWithPagination() {
        UUID productId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        ProductEntity productEntity = ProductEntity.builder()
                .id(productId)
                .name("Test Product")
                .price(new BigDecimal("99.99"))
                .description("Test Description")
                .build();

        Page<ProductEntity> productPage = new PageImpl<>(List.of(productEntity), pageable, 1);
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<Product> result = productService.findAll(pageable);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.getTotalElements()),
                () -> assertEquals(1, result.getContent().size()),
                () -> assertEquals(productId, result.getContent().getFirst().getId()),
                () -> assertEquals(productEntity.getName(), result.getContent().getFirst().getName()),
                () -> assertEquals(productEntity.getDescription(), result.getContent().getFirst().getDescription()),
                () -> assertEquals(productEntity.getPrice(), result.getContent().getFirst().getPrice())
        );

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldGetEmptyListWhenNoProductsToReturn() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findAll(pageable)).thenReturn(Page.empty(pageable));

        Page<Product> result = productService.findAll(pageable);

        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isEmpty()),
                () -> assertEquals(0, result.getTotalElements())
        );

        verify(productRepository, times(1)).findAll(pageable);
    }
}
