package com.unknown.commerceserver.domain.item.entity;

import com.unknown.commerceserver.domain.product.entity.Product;
import com.unknown.commerceserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemProduct extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "item_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @ManyToOne
    @JoinColumn(name = "product_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Product product;

    @Comment("제품의 재고를 없애는 수량 (상품 수량)")
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    // 연관 관계 편의 메서드
    public void addItem(Item item) {
        this.item = item;
        this.item.getItemProducts().add(this);
    }

    // 연관 관계 편의 메서드
    public void addProduct(Product product) {
        this.product = product;
        this.product.getItemProducts().add(this);
    }
}
