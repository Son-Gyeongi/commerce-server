package com.unknown.commerceserver.domain.item.dao;

import com.unknown.commerceserver.domain.item.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findAllAndDeletedAtIsNull();
}
