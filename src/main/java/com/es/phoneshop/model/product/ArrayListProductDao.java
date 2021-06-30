package com.es.phoneshop.model.product;

import java.math.BigDecimal;
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
    public List<Product> findProducts(String model, String minS, String maxS) {
        BigDecimal min = new BigDecimal(0);
        BigDecimal max =new BigDecimal(0);
        try {
            min = new BigDecimal(minS);
        } catch (NullPointerException e) {
            min = new BigDecimal(0);
        }
        //catch (NumberFormatException e) {}
        try {
            max = new BigDecimal(maxS);
        } catch (NullPointerException e) {
            max = new BigDecimal(1000);
        }
        //catch (NumberFormatException e) {}
        BigDecimal finalMin = min;
        BigDecimal finalMax = max;
        return products.stream()
                .filter(product -> model == null || model.isEmpty() || product.getDescription().contains(model))
                .filter(product -> finalMin == null || finalMin.equals(BigDecimal.ZERO) || product.getPrice().doubleValue() >= finalMin.doubleValue())
                .filter(product -> finalMax == null || finalMax.equals(BigDecimal.ZERO) || product.getPrice().doubleValue() <= finalMax.doubleValue())
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