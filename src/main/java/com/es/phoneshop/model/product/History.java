package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class History {

    private BigDecimal price;

    private Currency currency;

    private Date date;

    public History(BigDecimal price, Currency currency, int year, int month, int day) {
        this.price = price;
        this.currency = currency;
        this.date = new Date(year, month, day);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
