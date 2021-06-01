package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
       }

    @Test
    public void testFindProductsResults() {
       // assertFalse(productDao.findProducts("S", "", "").isEmpty());
    }

    @Test
    public void testSaveUpdateNewProduct() {
        Currency usd = Currency.getInstance("USD");
        List<History> history = new ArrayList<>();
        Product product = new Product("db", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product1 = new Product(1L,"abs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", history);
        productDao.save(product);
        productDao.save(product1);
        Product result = productDao.getProduct(product.getId());
        assertEquals("db", result.getCode());
        result = productDao.getProduct(product1.getId());
        assertEquals("abs", result.getCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveProduct() {
        productDao.delete(1L);
        productDao.getProduct(1L);
    }
}
