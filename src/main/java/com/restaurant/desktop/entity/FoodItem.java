package com.restaurant.desktop.entity;

import java.math.BigDecimal;
import java.util.Map;

public class FoodItem {
    private Long id;
    private Map<String, String> names;       // 多语言名称
    private Map<String, String> descriptions;
    private BigDecimal price;
    private Category category;
    private String imagePath;                 // 图片路径

    public FoodItem(Long id, Map<String, String> names, Map<String, String> descriptions,
                    BigDecimal price, Category category, String imagePath) {
        this.id = id;
        this.names = names;
        this.descriptions = descriptions;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Long getId() { return id; }
    public Map<String, String> getNames() { return names; }
    public Map<String, String> getDescriptions() { return descriptions; }
    public BigDecimal getPrice() { return price; }
    public Category getCategory() { return category; }
    public String getImagePath() { return imagePath; }

    public String getName(String lang) {
        return names.getOrDefault(lang, names.get("zh"));
    }

    public String getDescription(String lang) {
        return descriptions.getOrDefault(lang, descriptions.get("zh"));
    }
}