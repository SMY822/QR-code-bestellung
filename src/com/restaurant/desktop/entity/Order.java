package com.restaurant.desktop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String tableNumber;
    private List<OrderItem> items;

    public Order(String tableNumber) {
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    public void clearItems() {
        items.clear();
    }
}