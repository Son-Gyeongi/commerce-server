package com.unknown.commerceserver.domain.order.dto.response;

import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.enumerated.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Schema(description = "주문 완료 정보")
public class OrderResponse {
    @Schema(description = "상품 테이블 고유 번호")
    private Long id;

    @Schema(description = "주문 번호")
    private String orderNo;

    @Schema(description = "주문 상태 정보") // 주문 완료, 준비중, 주문 취소, 배송 완료
    private OrderStatus orderStatus;

    @Schema(description = "주문 금액")
    private BigDecimal price;

    @Schema(description = "주문 배송 금액")
    private BigDecimal deliveryPrice;

    @Schema(description = "주문 총 금액(배송 금액 포함)")
    private BigDecimal totalPrice;

    @Schema(description = "주문자")
    private String recipient;

    @Schema(description = "주문자 전화번호")
    private String phone;

    @Schema(description = "배송지 우편번호")
    private String postcode;

    @Schema(description = "배송지 주소")
    private String address;

    @Schema(description = "배송지 상세 주소")
    private String addressDetail;

    @Schema(description = "주문 요청 사항")
    private String description;

    @Schema(description = "주문 취소 사유")
    private String cancelReason;

    @Schema(description = "주문한 상품 주문 정보에 포함")
    private List<OrderedItemResponse> orderedItems;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .orderStatus(order.getOrderStatus())
                .price(order.getPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .totalPrice(order.getTotalPrice())
                .recipient(order.getRecipient())
                .phone(order.getPhone())
                .postcode(order.getPostcode())
                .address(order.getAddress())
                .addressDetail(order.getAddressDetail())
                .description(order.getDescription())
                .cancelReason(order.getCancelReason())
                .build();
    }

    // 주문한 상품 주문 정보에 포함
    public static OrderResponse of(Order order, List<OrderedItemResponse> orderedItems) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .orderStatus(order.getOrderStatus())
                .price(order.getPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .totalPrice(order.getTotalPrice())
                .recipient(order.getRecipient())
                .phone(order.getPhone())
                .postcode(order.getPostcode())
                .address(order.getAddress())
                .addressDetail(order.getAddressDetail())
                .description(order.getDescription())
                .cancelReason(order.getCancelReason())
                .orderedItems(orderedItems)
                .build();
    }
}
