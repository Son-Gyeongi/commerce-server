package com.unknown.commerceserver.domain.order.api;

import com.unknown.commerceserver.domain.item.dao.ItemProductRepository;
import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.dto.request.ItemRequest;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.domain.order.application.OrderService;
import com.unknown.commerceserver.domain.order.dao.OrderRepository;
import com.unknown.commerceserver.domain.order.dto.request.OrderRequest;
import com.unknown.commerceserver.domain.product.dao.ProductRepository;
import com.unknown.commerceserver.domain.product.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemProductRepository itemProductRepository;

    // 테스트를 하기 위해서 데이터베이스에 재고가 있어야 함으로
    // 테스트가 실행되기 전에 데이터를 생성
    @BeforeEach
    public void before() {
        itemRepository.saveAndFlush(Item.builder()
                .type("식품")
                .name("삼양라면 1봉지")
                .price(BigDecimal.valueOf(2500))
                .info("맛있는 삼양라면")
                .build());

        productRepository.saveAndFlush(Product.builder()
                .type("식품")
                .name("삼양라면 1봉지(5개입)")
                .price(BigDecimal.valueOf(2500))
                .quantity(100L) // 제품 재고 수량 100개
                .build());

        itemProductRepository.saveAndFlush(ItemProduct.builder()
                .item(itemRepository.findByIdAndDeletedAtIsNull(1L).get())
                .product(productRepository.findByIdAndDeletedAtIsNull(1L).get())
                .quantity(1L) // 상품(Item) : 삼양라면 1봉지
                .build());
    }

    // 테스트 케이스가 종료되면 모든 상품(Item)을 삭제
    @AfterEach
    public void after() {
        itemRepository.deleteAll();
        productRepository.deleteAll();
        itemProductRepository.deleteAll();
    }

    // TODO 테스트 데이터베이스 새롭게 만들어야 할듯
    @Test
    public void 동시에_100개_요청() throws InterruptedException {
        // 동시에 여러 개의 요청을 보내기 위해 멀티 쓰레드 사용
        int threadCount = 100;

        // 멀티스레드를 이용해야 하기 때문에 ExecutorService 사용
        // ExecutorService 는 비동기로 실행하는 작업을 단순화하여 사용할 수 있게 도와주는 자바의 API
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 100개 요청이 끝날 때까지 기다려야 함으로 CountDownLatch 활용
        // CountDownLatch 는 다른 스레드에서 수행 중인 작업이 완료될 때까지 대기할 수 있도록 도와주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 주문에 필요한 데이터 생성
        // 주문할 상품 정보와 주문할 상품 수량
        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .quantity(1L)
                .build();

        // 주문 정보
        OrderRequest orderRequest = OrderRequest.builder()
                .price(BigDecimal.valueOf(2500))
                .deliveryPrice(BigDecimal.valueOf(3000))
                .totalPrice(BigDecimal.valueOf(5500))
                .recipient("주문자")
                .phone("010-0000-0000")
                .postcode("00000")
                .address("대한시 민국구")
                .addressDetail("00동")
                .description("문 앞에 두고 벨 눌러주세요.")
                .itemRequests(List.of(itemRequest))
                .build();

        // for문을 활용해서 100개의 요청을 보낸다.
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    // TODO orderRequest 만들기
                    orderService.createOrder(orderRequest);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // 모든 요청이 완료된다면 productRepository를 활용해서 남은 재고(quantity)를 가지고 와서 비교
        Product product = productRepository.findByIdAndDeletedAtIsNull(1L).orElseThrow();
        // 예상 동작 - 처음에 100개 저장하고 1개씩 100번 감소해서 0개가 될 것이다.
        Assertions.assertEquals(0L, product.getQuantity());
    }
}