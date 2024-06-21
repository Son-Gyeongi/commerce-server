package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.dto.response.ItemDetailResponse;
import com.unknown.commerceserver.domain.item.dto.response.ItemResponse;
import com.unknown.commerceserver.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

        if (itemResponses.isEmpty())
            throw new NoSuchElementException("등록된 상품이 없습니다.");

        return itemResponses;
    }

    // 상품 상세 조회
    @Override
    public ItemDetailResponse getItemById(Long id) {
        Item foundItem = itemRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

        return ItemDetailResponse.of(foundItem);
    }
}
