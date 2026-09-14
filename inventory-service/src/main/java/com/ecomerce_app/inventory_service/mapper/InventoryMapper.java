package com.ecomerce_app.inventory_service.mapper;

import com.ecomerce_app.inventory_service.dto.InventoryRequest;
import com.ecomerce_app.inventory_service.dto.InventoryResponse;
import com.ecomerce_app.inventory_service.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toModel(InventoryRequest inventoryRequest);

    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponse toResponse(Inventory inventory);
}
