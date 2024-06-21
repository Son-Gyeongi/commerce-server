package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dto.response.ItemResponse;

import java.util.List;

public interface ItemService {

    // 상품 내역 조회
    List<ItemResponse> getAllItems();
}
