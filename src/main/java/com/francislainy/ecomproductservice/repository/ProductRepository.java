package com.francislainy.ecomproductservice.repository;

import com.francislainy.ecomproductservice.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
