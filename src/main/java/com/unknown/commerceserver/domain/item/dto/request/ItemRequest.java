package com.unknown.commerceserver.domain.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
}
