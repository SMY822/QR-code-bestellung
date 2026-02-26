package com.restaurant.desktop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String tableNumber;
    private List<OrderItem> items;
    private String paymentMethod; // 支付方式
    private boolean paid;

    public Order(String tableNumber) {
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.paid = false;
    }

    public String getTableNumber() { return tableNumber; }
    public List<OrderItem> getItems() { return items; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isPaid() { return paid; }

    public void addItem(OrderItem item) {
        // 如果已存在相同菜品，合并数量（此处简化，直接添加）
        items.add(item);
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        items.clear();
    }
}