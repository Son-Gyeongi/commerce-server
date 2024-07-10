package com.unknown.commerceserver.global.init;

import com.unknown.commerceserver.domain.item.dao.ItemProductRepository;
import com.unknown.commerceserver.domain.item.dao.ItemRepository;
import com.unknown.commerceserver.domain.item.entity.Item;
import com.unknown.commerceserver.domain.item.entity.ItemProduct;
import com.unknown.commerceserver.domain.product.dao.ProductRepository;
import com.unknown.commerceserver.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@Slf4j
@Profile("!prod & !test")
@RequiredArgsConstructor
@Configuration
public class NotProd {

    @Autowired
    @Lazy
    private NotProd self;

    private final ItemRepository itemRepository;
    private final ItemProductRepository itemProductRepository;
    private final ProductRepository productRepository;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            if (!itemRepository.findAll().isEmpty()) {
                return;
            }

            initItem();
//            initProduct();
//            initItemProduct();
        };
    }

    public void initItem() {
        //initItem
        Item item1 = itemRepository.save(Item.builder().type("식품").name("삼양라면 5개입 2봉지").price(BigDecimal.valueOf(5_000L)).info("맛있는 삼양라면 5개입 2봉지 팝니다.").build());// 하나에 2,500원
        Item item2 = itemRepository.save(Item.builder().type("식품").name("짜파게티 5개입 3봉지").price(BigDecimal.valueOf(7_500L)).info("맛있는 짜파게티 5개입 3봉지 팝니다.").build());// 하나에 2,500원
        Item item3 = itemRepository.save(Item.builder().type("식품").name("삼양라면 5개입 1봉지, 신라면 5개입 1봉지 총 2봉지").price(BigDecimal.valueOf(4_500L)).info("맛있는 삼양라면 5개입, 신라면 5개입 각 1봉지씩 총 2봉지 팝니다.").build());// 하나에 2,000원

        Item item4 = itemRepository.save(Item.builder().type("의류").name("검은색 반팔 2개").price(BigDecimal.valueOf(25_000L)).info("여름에 시원하게 입을 수 있는 검은색 반팔 팝니다.").build());// 하나에 12,500원
        Item item5 = itemRepository.save(Item.builder().type("의류").name("긴팔 흰 셔츠, 청바지 각 1개씩 세트").price(BigDecimal.valueOf(45_000L)).info("긴팔 흰 셔츠와 청바지 세트 팝니다.").build());// 셔츠 20,000원, 청바지 25,000원

        // initProduct
        Product product1 = productRepository.save(Product.builder().type("식품").name("삼양라면 5개입").price(BigDecimal.valueOf(2_500L)).quantity(30L).build());
        Product product2 = productRepository.save(Product.builder().type("식품").name("짜파게티 5개입").price(BigDecimal.valueOf(2_500L)).quantity(20L).build());
        Product product3 = productRepository.save(Product.builder().type("식품").name("신라면 5개입").price(BigDecimal.valueOf(2_000L)).quantity(25L).build());

        Product product4 = productRepository.save(Product.builder().type("의류").name("검은색 반팔").price(BigDecimal.valueOf(12_500L)).quantity(100L).build());
        Product product5 = productRepository.save(Product.builder().type("의류").name("긴팔 흰 셔츠").price(BigDecimal.valueOf(20_000L)).quantity(150L).build());
        Product product6 = productRepository.save(Product.builder().type("의류").name("청바지").price(BigDecimal.valueOf(25_000L)).quantity(150L).build());

        // initItemProduct
        // 삼양라면 5개입 2봉지
        itemProductRepository.save(ItemProduct.builder().item(item1).product(product1).quantity(2L).build());
        // 짜파게티 5개입 3봉지
        itemProductRepository.save(ItemProduct.builder().item(item2).product(product2).quantity(3L).build());
        // 삼양라면 5개입 1봉지, 신라면 5개입 1봉지 총 2봉지
        itemProductRepository.save(ItemProduct.builder().item(item3).product(product1).quantity(1L).build());
        itemProductRepository.save(ItemProduct.builder().item(item3).product(product3).quantity(1L).build());

        // 검은색 반팔 2개
        itemProductRepository.save(ItemProduct.builder().item(item4).product(product4).quantity(2L).build());
        // 긴팔 흰 셔츠, 청바지 각 1개씩 세트
        itemProductRepository.save(ItemProduct.builder().item(item5).product(product5).quantity(1L).build());
        itemProductRepository.save(ItemProduct.builder().item(item5).product(product6).quantity(1L).build());
    }

    public void initProduct() {
        productRepository.save(Product.builder().type("식품").name("삼양라면 5개입").price(BigDecimal.valueOf(2_500L)).quantity(30L).build());
        productRepository.save(Product.builder().type("식품").name("짜파게티 5개입").price(BigDecimal.valueOf(2_500L)).quantity(20L).build());
        productRepository.save(Product.builder().type("식품").name("신라면 5개입").price(BigDecimal.valueOf(2_000L)).quantity(25L).build());

        productRepository.save(Product.builder().type("의류").name("검은색 반팔").price(BigDecimal.valueOf(12_500L)).quantity(100L).build());
        productRepository.save(Product.builder().type("의류").name("긴팔 흰 셔츠").price(BigDecimal.valueOf(20_000L)).quantity(150L).build());
        productRepository.save(Product.builder().type("의류").name("청바지").price(BigDecimal.valueOf(25_000L)).quantity(150L).build());
    }

    public void initItemProduct() {
        // 삼양라면 5개입 2봉지
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(1L).get()).product(productRepository.findByIdAndDeletedAtIsNull(1L).get()).quantity(2L).build());
        // 짜파게티 5개입 3봉지
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(2L).get()).product(productRepository.findByIdAndDeletedAtIsNull(2L).get()).quantity(3L).build());
        // 삼양라면 5개입 1봉지, 신라면 5개입 1봉지 총 2봉지
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(3L).get()).product(productRepository.findByIdAndDeletedAtIsNull(1L).get()).quantity(1L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(3L).get()).product(productRepository.findByIdAndDeletedAtIsNull(3L).get()).quantity(1L).build());

        // 검은색 반팔 2개
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(4L).get()).product(productRepository.findByIdAndDeletedAtIsNull(4L).get()).quantity(2L).build());
        // 긴팔 흰 셔츠, 청바지 각 1개씩 세트
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(5L).get()).product(productRepository.findByIdAndDeletedAtIsNull(5L).get()).quantity(1L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findByIdAndDeletedAtIsNull(5L).get()).product(productRepository.findByIdAndDeletedAtIsNull(6L).get()).quantity(1L).build());
    }
}
