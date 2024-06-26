package com.unknown.commerceserver.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class HttpResponse {

    @Getter
    @RequiredArgsConstructor
    public enum Fail {
        // 400
        BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),
        BAD_REQUEST_PRICE(HttpStatus.BAD_REQUEST, "다시 주문 확인 부탁드립니다. 주문하신 가격이 잘못 되었습니다."),
        BAD_REQUEST_QUANTITY(HttpStatus.BAD_REQUEST, "해당하는 상품은 품절이거나 수량이 부족합니다."),

        // 401
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."),

        // 403
        FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 접근입니다."),

        // 404
        NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
        NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "등록된 상품이 없습니다."),
        NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "등록된 제품이 없습니다."),

        // 405
        METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP METHOD 입니다."),

        // 409
        CONFLICT(HttpStatus.CONFLICT, "이미 리소스가 존재합니다."),

        // 500 서버 에러
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다.");

        private final HttpStatus status;
        private final String message;
    }
}
