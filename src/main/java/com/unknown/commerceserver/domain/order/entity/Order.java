package com.unknown.commerceserver.domain.order.entity;

import com.unknown.commerceserver.domain.order.enumerated.OrderStatus;
import com.unknown.commerceserver.domain.order.util.OrderUtil;
import com.unknown.commerceserver.global.entity.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "orders")
public class Order extends BaseEntity {

    @Comment("주문 번호")
    @Column(name = "order_no", columnDefinition = "VARCHAR(200)")
    private String orderNo;

    @Comment("주문 상태 정보") // 주문 완료, 준비중, 주문 취소, 배송 완료
    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 소수점 이하 자릿수를 정확하게 유지하려면 BigDecimal과 같은 고정 소수점 형식을 사용
    @Comment("주문 금액")
    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Comment("주문 배송 금액")
    @Column(name = "delivery_price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal deliveryPrice;

    @Comment("주문 총 금액(배송 금액 포함)")
    @Column(name = "total_price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal totalPrice;

    @Comment("주문자")
    @Column(name = "recipient", columnDefinition = "VARCHAR(50)")
    private String recipient;

    @Comment("주문자 전화번호")
    @Column(name = "phone", columnDefinition = "VARCHAR(50)")
    private String phone;

    @Comment("배송지 우편번호")
    @Column(name = "postcode", columnDefinition = "VARCHAR(50)")
    private String postcode;

    @Comment("배송지 주소")
    @Column(name = "address", columnDefinition = "VARCHAR(200)")
    private String address;

    @Comment("배송지 상세 주소")
    @Column(name = "address_detail", columnDefinition = "VARCHAR(200)")
    private String addressDetail;

    @Comment("주문 요청 사항")
    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    private String description;

    @Comment("주문 취소 사유")
    @Column(name = "cancel_reason", columnDefinition = "VARCHAR(200)")
    private String cancelReason;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // orderNo 생성
    public void genOrderNo() {
        // 주문 접수할 때 orderNo 만든다.
        StringBuilder orderNo = new StringBuilder();

        String date = OrderUtil.date.getDate();
        String time = OrderUtil.date.getTime();
        String timeMillis = OrderUtil.date.getTimeMillis(getCreatedAt());

        orderNo.append(date).append("-");
        orderNo.append(time).append("-");
        orderNo.append(timeMillis).append("-");
        orderNo.append(this.getId());

        this.orderNo = orderNo.toString();
    }
}
