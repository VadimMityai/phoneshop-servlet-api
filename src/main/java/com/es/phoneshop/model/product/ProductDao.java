package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String model, SortField sortField, SortOrder sortOrder);
    List<Product> findProducts(String model, String min, String max);
    void save(Product product);
    void delete(Long id);
}
