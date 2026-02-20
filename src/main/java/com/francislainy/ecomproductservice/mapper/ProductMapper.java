package com.francislainy.ecomproductservice.mapper;

import com.francislainy.ecomproductservice.entity.ProductEntity;
import com.francislainy.ecomproductservice.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductEntity toEntity(Product product);
    Product toModel(ProductEntity productEntity);
}
