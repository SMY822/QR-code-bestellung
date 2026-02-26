package com.restaurant.desktop.entity;

import java.math.BigDecimal;

public class OrderItem {
    private FoodItem foodItem;
    private int quantity;

    public OrderItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() { return foodItem; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getSubtotal() {
        return foodItem.getPrice().multiply(new BigDecimal(quantity));
    }
}