package com.ecomerce_app.product_service.service.impl;

import com.ecomerce_app.product_service.dto.ProductRequestDto;
import com.ecomerce_app.product_service.dto.ProductResponseDto;
import com.ecomerce_app.product_service.exception.ResourceNotFoundException;
import com.ecomerce_app.product_service.mapper.ProductMapper;
import com.ecomerce_app.product_service.model.Product;
import com.ecomerce_app.product_service.repository.ProductRepository;
import com.ecomerce_app.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.toProduct(productRequestDto);
        Product savedProduct = productRepository.save(product);

        return productMapper.toProductResponseDto(savedProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto", "id", id)
        );

        return productMapper.toProductResponseDto(product);
    }

    @Override
    public ProductResponseDto updateProductById(String id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto", "id", id)
        );

        productMapper.updateProduct(productRequestDto, product);
        Product updatedProduct = productRepository.save(product);

        return productMapper.toProductResponseDto(updatedProduct);
    }

    @Override
    public void deleteProductById(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }

        productRepository.deleteById(id);
    }
}
