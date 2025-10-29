package com.restaurant.desktop.entity;

import javax.swing.ImageIcon;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

public class FoodMenuItem {
    // 现有代码...

    // 修改getName和getDescription方法
    public String getName() {
        String currentLang = LanguageService.getCurrentLanguage();
        return names.getOrDefault(currentLang, names.get("zh")); // 默认中文
    }

    public String getDescription() {
        String currentLang = LanguageService.getCurrentLanguage();
        return descriptions.getOrDefault(currentLang, descriptions.get("zh")); // 默认中文
    }

    // 添加分类的多语言支持
    public String getCategory() {
        String currentLang = LanguageService.getCurrentLanguage();
        String categoryKey = this.category; // 原始分类

        // 将中文分类映射到多语言
        Map<String, String> categoryMap = new HashMap<>();
        if ("主食".equals(category)) {
            categoryMap.put("zh", "主食");
            categoryMap.put("en", "Main Dish");
            categoryMap.put("de", "Hauptgericht");
        } else if ("饮料".equals(category)) {
            categoryMap.put("zh", "饮料");
            categoryMap.put("en", "Drink");
            categoryMap.put("de", "Getränk");
        } else if ("甜点".equals(category)) {
            categoryMap.put("zh", "甜点");
            categoryMap.put("en", "Dessert");
            categoryMap.put("de", "Nachspeise");
        }

        return categoryMap.getOrDefault(currentLang, category);
    }
}