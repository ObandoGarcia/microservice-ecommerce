package com.ecomerce_app.inventory_service.dto;

public record InventoryResponse(
       Long id,
       String sku,
       Integer quantity,
       boolean inStock
) {
}
