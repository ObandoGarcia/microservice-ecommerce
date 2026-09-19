package com.ecomerce_app.order_service.service.impl;

import com.ecomerce_app.order_service.dto.OrderRequest;
import com.ecomerce_app.order_service.dto.OrderResponse;
import com.ecomerce_app.order_service.exception.ResourceNotFoundException;
import com.ecomerce_app.order_service.mapper.OrderMapper;
import com.ecomerce_app.order_service.model.Order;
import com.ecomerce_app.order_service.repository.OrderRepository;
import com.ecomerce_app.order_service.service.OrderService;
import com.ecomerce_app.order_service.service.client.InventoryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    //private final WebClient.Builder webClientBuilder;
    private final InventoryClient inventoryClient;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderMapper orderMapper,
                            InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.inventoryClient = inventoryClient;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);

        for (var item : order.getOrderLineItemsList()) {
            String sku = item.getSku();
            Integer quantity = item.getQuantity();

            try {
                 /*webClientBuilder.build().put()
                        .uri("http://localhost:8081/api/v1/inventory/reduce/" + sku,
                                uriBuilder -> uriBuilder.queryParam("quantity", quantity).build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();*/

                inventoryClient.reduceStock(sku, quantity);

            } catch (Exception e){
                log.error("Error when trying to reduce stock for sku {} : {}", sku, e.getMessage());
                throw new IllegalArgumentException("The order could not be processed; insufficient stock or an inventory error.");
            }
        }

        order.setOrderNumber(UUID.randomUUID().toString());
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden", "id", id));

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Orden", "id", id);
        }

        orderRepository.deleteById(id);
    }
}
