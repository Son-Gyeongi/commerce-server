package com.unknown.commerceserver.domain.item.application;

import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.dto.response.ItemResponse;
import com.unknown.commerceserver.domain.item.entity.Item;
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
        // TODO 삭제되지 않은 제품, 제품에 수량이 있는 경우 보여줄 것
//        List<Item> items = itemRepository.findAll();
        // QueryDSL 써야 겠는데
        List<Item> items = itemRepository.findAllAndDeletedAtIsNull();

        List<ItemResponse> itemResponses = items.stream()
                .map(item -> ItemResponse.of(item)).toList();

        return itemResponses;
    }
}
