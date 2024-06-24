package com.unknown.commerceserver.domain.order.api;

import com.unknown.commerceserver.domain.order.application.OrderService;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@Tag(name = "v1-orders", description = "주문 관련 API")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 완료", description = "상품 선택 -> 장바구니 담기 -> 주문 완료 / 이 부분은 주문을 하기 위한 플로우이며, 주문 완료 API를 구현시 앞 프로세스는 완료되었다는 전제")
    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse createdOrder = orderService.createOrder(orderRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdOrder.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdOrder);
    }
}
