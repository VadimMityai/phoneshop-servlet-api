package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;
    private ReadWriteLock rwlock;   // need add lock instead of synchronized

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }
    private static long maxId = 0;
    private List<Product> products;


    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException{

        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findProducts(String model, SortField sortField, SortOrder sortOrder) {
            Comparator<Product> comparator = Comparator.comparing(product -> {
                if (sortField != null && SortField.description == sortField) {
                    return (Comparable) product.getDescription();
                } else {
                    return (Comparable) product.getPrice();
                }
            });
 /*           if (sortOrder == SortOrder.desc) {
                comparator = Comparator.reverseOrder();    // make desc sort???
            }
*/
            return products.stream()
                    .filter(product -> model == null || model.isEmpty() || product.getDescription().contains(model))
                    .filter(product -> product.getPrice() != null && product.getStock() > 0)
                    .sorted(comparator)
                    .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product.getId() != null) {
            delete(product.getId());
        } else {
        product.setId(maxId++);
        }
        products.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> id.equals(product.getId()));
    }


}