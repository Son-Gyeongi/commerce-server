package com.unknown.commerceserver.domain.item.api;

import com.unknown.commerceserver.domain.item.application.ItemService;
import com.unknown.commerceserver.domain.item.dto.response.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/items")
@Tag(name = "v1-items", description = "상품 관련 API")
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상품 내역 조회", description = "삭제된 상품 제외 모든 상품 내역 조회")
    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<ItemResponse> allItems = itemService.getAllItems();

        return ResponseEntity.ok().body(allItems);
    }
}
