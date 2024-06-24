package com.unknown.commerceserver.domain.order.application;

import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;

public interface OrderService {

    // 주문 완료
    OrderResponse createOrder(OrderRequest orderRequest);
}
