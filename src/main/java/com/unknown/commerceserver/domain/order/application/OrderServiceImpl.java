package com.unknown.commerceserver.domain.order.application;

import com.unknown.commerceserver.domain.item.dao.ItemProductRepository;
import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.domain.order.dao.OrderDetailRepository;
import com.unknown.commerceserver.domain.order.dao.OrderRepository;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;
import com.unknown.commerceserver.domain.order.dto.response.OrderedItemResponse;
import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.entity.OrderDetail;
import com.unknown.commerceserver.domain.order.enumerated.OrderStatus;
import com.unknown.commerceserver.domain.product.entity.Product;
import com.unknown.commerceserver.global.common.HttpResponse;
import com.unknown.commerceserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ItemProductRepository itemProductRepository;

    // 주문 완료
    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // 요청한 price 값을 소수점 3자리로 설정 - 비교하기 위해서
        BigDecimal requestPrice = orderRequest.getPrice().setScale(3);
        BigDecimal requestDeliveryPrice = orderRequest.getDeliveryPrice().setScale(3);
        BigDecimal requestTotalPrice = orderRequest.getTotalPrice().setScale(3);

        // 주문 금액 및 재고 유효성 검사
        BigDecimal itemTotalPrice = priceAndQuantityValidation(orderRequest);

        // 계산된 주문한 상품들 + 배송비
        BigDecimal orderTotalPrice = itemTotalPrice.add(requestDeliveryPrice);
        // 주문 금액(서버) == 상품 총금액(요청) || 주문 금액 + 배송 금액(서버) == 전체 금액(요청) 확인하기
        if (!itemTotalPrice.equals(requestPrice)
            || !orderTotalPrice.equals(requestTotalPrice)) {
            BusinessException.builder()
                    .response(HttpResponse.Fail.BAD_REQUEST_PRICE)
                    .build();
        }

        // 주문 저장
        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER_RECEIVED)
                .price(itemTotalPrice)
                .deliveryPrice(requestDeliveryPrice) // TODO 배송비 하드코딩 고민하기
                .totalPrice(orderTotalPrice)
                .recipient(orderRequest.getRecipient()) // 회원이 있다면 orderRequest.getRecipient() 랑 비교할 수도 있겠다.
                .phone(orderRequest.getPhone()) // 회원이 있다면 회원 정보에서 가져오기
                .postcode(orderRequest.getPostcode())
                .address(orderRequest.getAddress())
                .addressDetail(orderRequest.getAddressDetail())
                .description(orderRequest.getDescription())
                .build();

        Order savedOrder = orderRepository.save(order);

        // orderNo 생성
        savedOrder.genOrderNo();

        // 주문 아이템(OrderDetail) 저장 및 응답에 반환하는 주문 아이템 저장
        List<OrderedItemResponse> orderedItemResponses = saveAndResponseOrderedItem(orderRequest, savedOrder);

        // 저장 후에 제품 재고 감소
        productQuantityDecrease(orderRequest);

        return OrderResponse.of(savedOrder, orderedItemResponses);
    }

    // 주문 금액 및 재고 유효성 검사
    private BigDecimal priceAndQuantityValidation(OrderRequest orderRequest) {
        BigDecimal itemTotalPrice = BigDecimal.ZERO;
        for (int i = 0; i < orderRequest.getItemRequests().size(); i++) {
            Long itemId = orderRequest.getItemRequests().get(i).getId();
            Long quantity = orderRequest.getItemRequests().get(i).getQuantity();
            Item item = itemRepository.findByIdAndDeletedAtIsNull(itemId)
                    .orElseThrow(() -> BusinessException.builder()
                            .response(HttpResponse.Fail.NOT_FOUND_ITEM)
                            .build());

            // 아이템별로 제품 재고 확인
            for (int j = 0; j < item.getItemProducts().size(); j++) {
                ItemProduct itemProduct = item.getItemProducts().get(j);
                Product product = itemProduct.getProduct();

                // 제품 수량 < 상품 수량
                if (product.getQuantity() < itemProduct.getQuantity()
                    || product.getQuantity() == 0) {
                    BusinessException.builder()
                            .response(HttpResponse.Fail.BAD_REQUEST_QUANTITY)
                            .build();
                }
            }

            // item.getPrice()*수량(quantity)
            itemTotalPrice = itemTotalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        return itemTotalPrice;
    }

    // 주문 아이템(OrderDetail) 저장 및 응답에 반환하는 주문 아이템 저장
    private List<OrderedItemResponse> saveAndResponseOrderedItem(OrderRequest orderRequest, Order savedOrder) {
        List<OrderedItemResponse> orderedItemResponses = new ArrayList<>();
        for (int i = 0; i < orderRequest.getItemRequests().size(); i++) {
            Long itemId = orderRequest.getItemRequests().get(i).getId();
            Long quantity = orderRequest.getItemRequests().get(i).getQuantity();
            Item item = itemRepository.findByIdAndDeletedAtIsNull(itemId)
                    .orElseThrow(() -> BusinessException.builder()
                            .response(HttpResponse.Fail.NOT_FOUND_ITEM)
                            .build());
            OrderDetail orderDetail = OrderDetail.builder()
                    .itemName(item.getName())
                    .price(item.getPrice())
                    .quantity(quantity)
                    .build();
            orderDetail.addOrder(savedOrder);
            orderDetail.addItem(item);

            orderDetailRepository.save(orderDetail);

            // 저장하면서 상품 반환 리스트까지 작성 - 수량 포함하기
            orderedItemResponses.add(OrderedItemResponse.of(item, quantity));
        }
        return orderedItemResponses;
    }

    // 저장 후에 제품 재고 감소
    @Transactional
    public void productQuantityDecrease(OrderRequest orderRequest) {
        for (int i = 0; i< orderRequest.getItemRequests().size(); i++) {
            Long itemId = orderRequest.getItemRequests().get(i).getId();
            Long quantity = orderRequest.getItemRequests().get(i).getQuantity(); // 주문된 수량

            List<ItemProduct> itemProducts = itemProductRepository.findAllByItemIdAndDeletedAtIsNull(itemId);

            for (int x = 0; x<itemProducts.size();x++) {
                System.out.println(itemProducts.get(x));
            }

            if (itemProducts.isEmpty()) {
                BusinessException.builder()
                        .response(HttpResponse.Fail.NOT_FOUND_PRODUCT)
                        .build();
            }

            // 재고 감소 (주문된 수량 * 상품의 각 제품 수량) - 각 상품 당 해당하는 제품들 재고 감소
            for (int j = 0; j < itemProducts.size(); j++) {
                ItemProduct itemProduct = itemProducts.get(j);
                Long decreaseQuantity = quantity * itemProduct.getQuantity();
                itemProduct.getProduct().minusQuantity(decreaseQuantity);
            }
        }
    }
}
