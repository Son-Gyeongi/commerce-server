package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {
    Optional<ItemProduct> findByIdAndDeletedAtIsNull(Long itemId);
}
