package com.unknown.commerceserver.domain.product.dao;

import com.unknown.commerceserver.domain.product.entity.Product;

import java.util.Optional;

public interface ProductRepositoryCustom {
    Optional<Product> findByIdAndDeletedAtIsNullForUpdate(Long id);
}
