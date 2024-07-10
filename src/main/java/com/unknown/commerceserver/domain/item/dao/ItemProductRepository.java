package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {
    Optional<ItemProduct> findByIdAndDeletedAtIsNull(Long itemId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ItemProduct> findAllByItemIdAndDeletedAtIsNull(Long itemId);
}
