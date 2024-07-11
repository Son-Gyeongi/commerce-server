package com.unknown.commerceserver.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Schema(description = "상품의 제품 내역 정보")
public class ProductRequest {
    @Schema(description = "제품 테이블 고유 번호")
    private Long id;
}
