package com.restaurant.desktop.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;
    private FoodItem item1;
    private FoodItem item2;

    @BeforeEach
    void setUp() {
        order = new Order("1");
        Map<String, String> names = new HashMap<>();
        names.put("zh", "测试菜品");
        Map<String, String> desc = new HashMap<>();
        item1 = new FoodItem(1L, names, desc, new BigDecimal("10.00"), Category.MAIN, null);
        item2 = new FoodItem(2L, names, desc, new BigDecimal("20.00"), Category.MAIN, null);
    }

    @Test
    void testAddItemAndTotal() {
        order.addItem(new OrderItem(item1, 2));
        order.addItem(new OrderItem(item2, 1));
        assertEquals(new BigDecimal("40.00"), order.getTotalAmount());
    }

    @Test
    void testEmptyOrderTotal() {
        assertEquals(BigDecimal.ZERO, order.getTotalAmount());
    }
}