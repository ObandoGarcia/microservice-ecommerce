package com.ecomerce_app.product_service.controller;

import com.ecomerce_app.product_service.dto.ProductRequestDto;
import com.ecomerce_app.product_service.dto.ProductResponseDto;
import com.ecomerce_app.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RefreshScope
public class ProductController {

    private final ProductService productService;

    @Value("${app.maintenance.message: Sistema operativo}")
    private String maintenanceMessage;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct( @Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts(HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader("X-Maintenance-Message", maintenanceMessage);
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable String id) {
        productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto updateProduct(@PathVariable String id,
                                            @Valid @RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProductById(id, productRequestDto);
    }
}
