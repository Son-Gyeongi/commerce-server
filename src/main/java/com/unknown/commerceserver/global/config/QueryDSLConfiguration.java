package com.unknown.commerceserver.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 컨피그 파일 생성
 */
@Configuration
public class QueryDSLConfiguration {
    @PersistenceContext
    private EntityManager entityManager;

    /*
    JPAQueryFactory 객체를 @Bean 객체로 등록해두면 매번 JPAQueryFactory를
    초기화하지 않고 스프링 컨테이너에서 가져다 쓸 수 있음
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
