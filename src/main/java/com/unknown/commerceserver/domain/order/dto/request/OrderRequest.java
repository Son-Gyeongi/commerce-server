package com.unknown.commerceserver.domain.order.dto.request;

import com.unknown.commerceserver.domain.item.dto.request.ItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Schema(description = "주문 요청 정보")
public class OrderRequest {
    @Schema(description = "주문 번호")
    private String orderNo;

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

    @Schema(description = "주문한 상품들")
    private List<ItemRequest> itemRequests;
}
