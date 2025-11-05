package com.restaurant.desktop.entity;

import com.restaurant.desktop.service.LanguageService;

import javax.swing.ImageIcon;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

public class FoodMenuItem {
    private Long id;
    private Map<String, String> names; // 多语言名称
    private Map<String, String> descriptions; // 多语言描述
    private BigDecimal price;
    private String category; // 分类（存储原始值）
    private String imageUrl;
    private ImageIcon imageIcon;

    // 构造函数
    public FoodMenuItem(Long id, Map<String, String> names, Map<String, String> descriptions,
                        BigDecimal price, String category, String imageUrl) {
        this.id = id;
        this.names = names;
        this.descriptions = descriptions;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        // 在构造函数中加载图片
        this.imageIcon = loadImageIcon(imageUrl);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getImagePath() {
        return this.imageUrl != null ? this.imageUrl : "";
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category; // 返回原始分类
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        // 当图片URL改变时重新加载图片
        this.imageIcon = loadImageIcon(imageUrl);
    }

    public ImageIcon getImageIcon() {
        // 如果imageIcon为null，尝试重新加载
        if (imageIcon == null && imageUrl != null) {
            this.imageIcon = loadImageIcon(imageUrl);
        }
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    // 修改getName和getDescription方法 - 添加null检查
    public String getName() {
        if (names == null || names.isEmpty()) {
            return "Unknown";
        }
        String currentLang = LanguageService.getCurrentLanguage();
        return names.getOrDefault(currentLang, names.get("zh")); // 默认中文
    }

    public String getDescription() {
        if (descriptions == null || descriptions.isEmpty()) {
            return "";
        }
        String currentLang = LanguageService.getCurrentLanguage();
        return descriptions.getOrDefault(currentLang, descriptions.get("zh")); // 默认中文
    }

    // 添加获取多语言分类的方法
    public String getLocalizedCategory() {
        String currentLang = LanguageService.getCurrentLanguage();

        // 分类多语言映射
        Map<String, Map<String, String>> categoryTranslations = new HashMap<>();

        // 主食分类
        Map<String, String> mainDish = new HashMap<>();
        mainDish.put("zh", "主食");
        mainDish.put("en", "Main Dish");
        mainDish.put("de", "Hauptgericht");
        categoryTranslations.put("主食", mainDish);

        // 素菜分类
        Map<String, String> vegetarian = new HashMap<>();
        vegetarian.put("zh", "素菜");
        vegetarian.put("en", "Vegetarian");
        vegetarian.put("de", "Vegetarisch");
        categoryTranslations.put("素菜", vegetarian);

        // 饮料分类
        Map<String, String> drink = new HashMap<>();
        drink.put("zh", "饮料");
        drink.put("en", "Drink");
        drink.put("de", "Getränk");
        categoryTranslations.put("饮料", drink);

        // 甜点分类
        Map<String, String> dessert = new HashMap<>();
        dessert.put("zh", "甜点");
        dessert.put("en", "Dessert");
        dessert.put("de", "Nachspeise");
        categoryTranslations.put("甜点", dessert);

        // 返回对应语言的分类，如果没有找到则返回原始分类
        Map<String, String> translation = categoryTranslations.get(this.category);
        if (translation != null && translation.containsKey(currentLang)) {
            return translation.get(currentLang);
        }
        return this.category; // 默认返回原始分类
    }

    // 加载图片的方法
    private ImageIcon loadImageIcon(String imagePath) {
        try {
            if (imagePath == null || imagePath.trim().isEmpty()) {
                System.out.println("图片路径为空");
                return null;
            }

            // 确保路径以斜杠开头
            if (!imagePath.startsWith("/")) {
                imagePath = "/" + imagePath;
            }

            System.out.println("尝试加载图片: " + imagePath);
            java.net.URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                System.out.println("图片加载成功: " + imagePath);
                return new ImageIcon(imageUrl);
            } else {
                System.out.println("图片加载失败，URL为null: " + imagePath);
                return null;
            }
        } catch (Exception e) {
            System.out.println("图片加载异常: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "FoodMenuItem{" +
                "id=" + id +
                ", name=" + getName() +
                ", description=" + getDescription() +
                ", price=" + price +
                ", category=" + getLocalizedCategory() +
                '}';
    }
}