package com.unknown.commerceserver.domain.item.dto.request;

import com.unknown.commerceserver.domain.order.dto.request.ProductRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Schema(description = "상품 내역 정보")
public class ItemRequest {
    @Schema(description = "상품 테이블 고유 번호")
    private Long id;

    @Schema(description = "주문한 상품 수량")
    private Long quantity;

    @Schema(description = "상품의 제품들")
    private List<ProductRequest> productRequests;
}
