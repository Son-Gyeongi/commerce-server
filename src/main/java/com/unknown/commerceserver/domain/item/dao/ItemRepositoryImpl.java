package com.unknown.commerceserver.domain.item.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.QItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    QItem qItem = QItem.item;

    @Override
    public List<Item> findAllAndDeletedAtIsNull() {
        // TODO 정렬 고민, type에 따라 선택 가능 고민
        // 삭제되지 않은 제품, TODO 제품에 수량이 있는 경우 보여줄 것
        BooleanBuilder where = new BooleanBuilder();

        where.and(qItem.deletedAt.isNull());

        List<Item> items = jpaQueryFactory
                .select(qItem)
                .from(qItem)
                .where(where)
                .fetch();

        return items;
    }
}
