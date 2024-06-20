package com.unknown.commerceserver.domain.item.entity;

import com.unknown.commerceserver.domain.order.entity.OrderItem;
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
public class Item extends BaseEntity {

    @Comment("상품 타입 : 의류, 식품 등등")
    @Column(name = "type", columnDefinition = "VARCHAR(20)")
    private String type;

    @Comment("상품 이름")
    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @Comment("상품 가격")
    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Comment("상품 정보")
    @Column(name = "info", columnDefinition = "MEDIUMTEXT")
    private String info;

    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<ItemProduct> itemProducts = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
}
