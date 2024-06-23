package com.unknown.commerceserver.domain.order.dao;

import com.unknown.commerceserver.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
