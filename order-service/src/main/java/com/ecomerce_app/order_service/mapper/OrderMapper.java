package com.ecomerce_app.order_service.mapper;

import com.ecomerce_app.order_service.dto.OrderLineItemsRequest;
import com.ecomerce_app.order_service.dto.OrderLineItemsResponse;
import com.ecomerce_app.order_service.dto.OrderRequest;
import com.ecomerce_app.order_service.dto.OrderResponse;
import com.ecomerce_app.order_service.model.Order;
import com.ecomerce_app.order_service.model.OrderLineItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequest orderRequest);

    OrderLineItems toOrderLineItems(OrderLineItemsRequest orderLineItemRequest);

    OrderResponse toOrderResponse(Order order);

    OrderLineItemsResponse toOrderLineItemResponse(OrderLineItems orderLineItems);
}
