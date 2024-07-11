package com.unknown.commerceserver.domain.product.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerceserver.domain.product.entity.Product;
import com.unknown.commerceserver.domain.product.entity.QProduct;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    QProduct qProduct = QProduct.product;

    @Override
    public Optional<Product> findByIdAndDeletedAtIsNullForUpdate(Long productId) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(qProduct.id.eq(productId))
                .and(qProduct.deletedAt.isNull());

        return Optional.ofNullable(jpaQueryFactory
                .select(qProduct)
                .from(qProduct)
                .where(where)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne());
    }
}
