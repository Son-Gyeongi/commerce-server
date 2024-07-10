package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.Item;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Item as s where s.id = :id and s.deletedAt IS NULL") // native 쿼리를 활용해서 쿼리 작성
    Optional<Item> findByIdAndDeletedAtIsNull(Long id);
}
