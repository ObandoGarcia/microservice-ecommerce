package com.ecomerce_app.product_service.service;

import com.ecomerce_app.product_service.dto.ProductRequestDto;
import com.ecomerce_app.product_service.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(String id);

    ProductResponseDto updateProductById(String id, ProductRequestDto productRequestDto);

    void deleteProductById(String id);
}
