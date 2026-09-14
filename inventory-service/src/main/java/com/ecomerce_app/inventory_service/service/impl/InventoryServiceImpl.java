package com.ecomerce_app.inventory_service.service.impl;

import com.ecomerce_app.inventory_service.dto.InventoryRequest;
import com.ecomerce_app.inventory_service.dto.InventoryResponse;
import com.ecomerce_app.inventory_service.exception.ResourceNotFoundException;
import com.ecomerce_app.inventory_service.mapper.InventoryMapper;
import com.ecomerce_app.inventory_service.model.Inventory;
import com.ecomerce_app.inventory_service.repository.InventoryRepository;
import com.ecomerce_app.inventory_service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        boolean existsSkuInDb = inventoryRepository.existsBySku(inventoryRequest.sku());

        if (existsSkuInDb) {
            throw new RuntimeException(String.format("sku %s already exists", inventoryRequest.sku()));
        }

        Inventory inventory = inventoryMapper.toModel(inventoryRequest);
        Inventory savedInventory = inventoryRepository.save(inventory);

        log.info("Created Inventory with SKU {}", inventory.getSku());

        return inventoryMapper.toResponse(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponse updateInventory(Long id, InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found", "id", id));

        inventory.setSku(inventoryRequest.sku());
        inventory.setQuantity(inventoryRequest.quantity());

        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Updated Inventory with SKU {}", inventory.getSku());

        return inventoryMapper.toResponse(savedInventory);
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory not found", "id", id);
        }

        inventoryRepository.deleteById(id);
        log.info("Deleted Inventory with SKU {}", id);
    }

    @Override
    @Transactional
    public void reduceStock(String sku, Integer quantity) {
        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Inventory not found", "sku", sku)
                );

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException(String.format("quantity %s not enough", sku));
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
