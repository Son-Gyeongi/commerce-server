package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {
}
