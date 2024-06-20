package com.unknown.commerceserver.domain.order.entity;

import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @Comment("주문 상품 이름")
    @Column(name = "item_name", columnDefinition = "VARCHAR(100)")
    private String itemName;

    @Comment("주문 상품 가격")
    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Comment("주문 상품 수량")
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    // 연관 관계 편의 메서드
    public void addOrder(Order order) {
        this.order = order;
        this.order.getOrderItems().add(this);
    }

    // 연관 관계 편의 메서드
    public void addItem(Item item) {
        this.item = item;
        this.item.getOrderItems().add(this);
    }
}
