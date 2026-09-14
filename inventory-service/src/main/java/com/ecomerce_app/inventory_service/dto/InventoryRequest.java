package com.ecomerce_app.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryRequest(
    @NotBlank(message = "El sku no puede estar vacio")
    String sku,

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    Integer quantity
){
}
