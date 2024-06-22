package com.unknown.commerceserver.domain.order.application;

import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.domain.order.dao.OrderRepository;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.order.dto.response.OrderResponse;
import com.unknown.commerceserver.domain.order.entity.Order;
import com.unknown.commerceserver.domain.order.enumerated.OrderStatus;
import com.unknown.commerceserver.domain.product.dao.ProductRepository;
import com.unknown.commerceserver.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    // 주문 완료
    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        // 주문 금액 유효성 검사
        BigDecimal itemTotalPrice = BigDecimal.ZERO;
        for (int i = 0; i < orderRequest.getItemRequests().size(); i++) {
            Long itemId = orderRequest.getItemRequests().get(i).getId();
            Item item = itemRepository.findByIdAndDeletedAtIsNull(itemId)
                    .orElseThrow(() -> new NoSuchElementException("해당하는 상품이 없습니다."));

            // 아이템별로 제품 재고 확인
            for (int j = 0; j < item.getItemProducts().size(); j++) {
                ItemProduct itemProduct = item.getItemProducts().get(j);
                Product product = itemProduct.getProduct();
                
                if (product.getQuantity() < itemProduct.getQuantity()
                    || product.getQuantity() == 0) {
                    throw new IllegalArgumentException("해당하는 상품은 품절입니다.");
                }
            }

            itemTotalPrice = itemTotalPrice.add(item.getPrice());
        }

        // 주문 금액 == 상품 총금액 || 주문 금액 + 배송 금액 == 전체 금액 확인하기
        BigDecimal orderTotalPrice = orderRequest.getPrice().add(orderRequest.getDeliveryPrice());
        if (!itemTotalPrice.equals(orderRequest.getPrice())
            || !orderTotalPrice.equals(itemTotalPrice)) {
            throw new IllegalArgumentException("다시 주문 확인 부탁드립니다. 주문하신 가격이 잘못 되었습니다.");
        }

        // 아이템 저장 - OrderDetail

        // 주문 저장
        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER_RECEIVED)
                .price(itemTotalPrice)
                .deliveryPrice(orderRequest.getDeliveryPrice()) // TODO 배송비 하드코딩 고민하기
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

        return OrderResponse.of(savedOrder);
    }
}
