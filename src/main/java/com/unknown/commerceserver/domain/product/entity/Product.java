package com.unknown.commerceserver.domain.product.entity;

import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Comment("제품 타입 : 의류, 식품 등등")
    @Column(name = "type", columnDefinition = "VARCHAR(20)")
    private String type;

    @Comment("제품 이름")
    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @Comment("제품 가격")
    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Comment("제품 수량 - 재고 관리")
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<ItemProduct> itemProducts = new ArrayList<>();

    public void minusQuantity(Long quantity) {
        this.quantity = this.quantity - quantity;
    }
}
