package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dto.response.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.dto.response.ItemResponse;

import java.util.List;

public interface ItemService {

    // 상품 내역 조회
    List<ItemResponse> getAllItems();

    // 상품 상세 조회
    ItemDetailResponse getItemById(Long id);
}
