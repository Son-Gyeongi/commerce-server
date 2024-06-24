package com.unknown.commerceserver.domain.order.dao;

import com.unknown.commerceserver.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
