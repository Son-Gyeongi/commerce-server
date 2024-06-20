package com.unknown.commerceserver.domain.order.enumerated;

/**
 * 주문 상태
 */
public enum OrderStatus {
    /**
     * 주문 접수 - 고객이 주문 접수한 상태, 판매자 측에서는 아직 수락 하지 않은 상태
     */
    ORDER_RECEIVED,
    /**
     * 준비 중 - 판매자 측에서 주문 수락 후 준비하는 상태
     */
    PREPARING,
    /**
     * 준비 완료 - 배송 전 준비 완료된 상태
     */
    READY,
    /**
     * 배송 중 - 배송 중인 상태
     */
    ON_DELIVERY,
    /**
     * 배송 완료 - 배송지까지 도착 완료한 상태
     */
    DELIVERY_COMPLETED,
    /**
     * 주문 취소 - 주문 접수 상태일 때 고객 또는 판매자가 취소한 상태
     */
    CANCELED_ORDER,
    /**
     * 문제 발생 - 주문 처리 중에 예기치 못한 문제가 발생할 경우
     */
    ISSUE_DETECTED
}
