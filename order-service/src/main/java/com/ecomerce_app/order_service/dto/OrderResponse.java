package com.ecomerce_app.order_service.dto;

import java.util.List;

public record OrderResponse(
        Long id,
        String orderNumber,
        List<OrderLineItemsResponse> orderLineItemsList
) {
}
