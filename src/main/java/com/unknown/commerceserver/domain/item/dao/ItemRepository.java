package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    Optional<Item> findByIdAndDeletedAtIsNull(Long id);
}
