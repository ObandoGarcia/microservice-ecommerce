package com.ecomerce_app.product_service.mapper;

import com.ecomerce_app.product_service.dto.ProductRequestDto;
import com.ecomerce_app.product_service.dto.ProductResponseDto;
import com.ecomerce_app.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequestDto productRequestDto);

    ProductResponseDto toProductResponseDto(Product product);

    @Mapping(target = "id", ignore = true)
    void updateProduct(ProductRequestDto productRequestDto, @MappingTarget Product product);
}
