package com.unknown.commerceserver.domain.item.dto.response;

import com.unknown.commerceserver.domain.item.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Schema(description = "상품 내역 정보")
public class ItemResponse {
    @Schema(description = "상품 테이블 고유 번호")
    private Long id;

    @Schema(description = "상품 타입 : 의류, 식품 등등")
    private String type;

    @Schema(description = "상품 이름")
    private String name;

    @Schema(description = "상품 가격")
    private BigDecimal price;

    @Schema(description = "상품 정보")
    private String info;

    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .type(item.getType())
                .name(item.getName())
                .price(item.getPrice())
                .info(item.getInfo())
                .build();
    }
}
