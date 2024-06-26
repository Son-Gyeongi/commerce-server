package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.dto.response.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.dto.response.ItemResponse;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.global.common.HttpResponse;
import com.unknown.commerceserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    // 상품 내역 조회
    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAllAndDeletedAtIsNull();

        List<ItemResponse> itemResponses = items.stream()
                .map(item -> ItemResponse.of(item)).toList();

        if (itemResponses.isEmpty()) {
            BusinessException.builder()
                    .response(HttpResponse.Fail.NOT_FOUND_ITEM)
                    .build();
        }

        return itemResponses;
    }

    // 상품 상세 조회
    @Override
    public ItemDetailResponse getItemById(Long id) {
        Item foundItem = itemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> BusinessException.builder()
                        .response(HttpResponse.Fail.NOT_FOUND_ITEM)
                        .build());

        return ItemDetailResponse.of(foundItem);
    }
}
