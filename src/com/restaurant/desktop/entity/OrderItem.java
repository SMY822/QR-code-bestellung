package com.restaurant.desktop.entity;

import java.math.BigDecimal;

public class OrderItem {
    // 1. 将MenuItem改为FoodMenuItem
    private FoodMenuItem foodMenuItem;
    private int quantity;
    private String comment;
    private Integer rating;

    // 2. 构造器参数改为FoodMenuItem
    public OrderItem(FoodMenuItem foodMenuItem, int quantity) {
        this.foodMenuItem = foodMenuItem;
        this.quantity = quantity;
        this.comment = "";
        this.rating = 0;
    }

    // 3. Getter/Setter方法同步修改
    public FoodMenuItem getFoodMenuItem() {
        return foodMenuItem;
    }

    public void setFoodMenuItem(FoodMenuItem foodMenuItem) {
        this.foodMenuItem = foodMenuItem;
    }

    // 4. 修正getSubtotal()方法（调用foodMenuItem的getPrice()）
    public BigDecimal getSubtotal() {
        return foodMenuItem.getPrice().multiply(new BigDecimal(quantity));
    }

    // 保留原有的comment、rating、quantity的Getter/Setter
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}