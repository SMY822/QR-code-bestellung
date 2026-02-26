package com.restaurant.desktop.ui;

import com.restaurant.desktop.entity.*;
import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class MenuPanel extends JPanel {
    private Navigator navigator;
    private Order order;
    private Map<Category, List<FoodItem>> menuData;
    private JTabbedPane categoryTabs;
    private JTextArea orderArea;
    private JButton confirmBtn;
    private JButton clearBtn;

    public MenuPanel(Navigator navigator, Order order) {
        this.navigator = navigator;
        this.order = order;
        initMenuData();
        initUI();
    }

    private void initMenuData() {
        menuData = new HashMap<>();
        for (Category cat : Category.values()) {
            menuData.put(cat, new ArrayList<>());
        }

        // 前菜 (APPETIZER)
        menuData.get(Category.APPETIZER).add(createFoodItem(101, "凉拌黄瓜", "Cucumber Salad", "Gurkensalat", "清爽开胃", 18.00, Category.APPETIZER, "/images/food/cucumber_salad.png"));
        menuData.get(Category.APPETIZER).add(createFoodItem(102, "凯撒沙拉", "Caesar Salad", "Caesar-Salat", "经典沙拉", 28.00, Category.APPETIZER, "/images/food/caesar_salad.png"));
        menuData.get(Category.APPETIZER).add(createFoodItem(103, "德式香肠拼盘", "German Sausage Platter", "Deutsche Wurstplatte", "多种香肠组合", 58.00, Category.APPETIZER, "/images/food/german_sausage_platter.png"));
        menuData.get(Category.APPETIZER).add(createFoodItem(104, "口水鸡", "Spicy Chicken Salad", "Scharfes Hühnchen", "麻辣鲜香", 42.00, Category.APPETIZER, "/images/food/spicy_chicken_salad.png"));
        menuData.get(Category.APPETIZER).add(createFoodItem(105, "法式洋葱汤", "French Onion Soup", "Französische Zwiebelsuppe", "浓郁芝士", 32.00, Category.APPETIZER, "/images/food/french_onion_soup.png"));

        // 主食 (MAIN)
        menuData.get(Category.MAIN).add(createFoodItem(201, "宫保鸡丁", "Kung Pao Chicken", "Kung-Pao-Hühnchen", "经典川菜", 48.00, Category.MAIN, "/images/food/kung_pao_chicken.png"));
        menuData.get(Category.MAIN).add(createFoodItem(202, "黑椒牛排", "Black Pepper Steak", "Pfeffersteak", "黑椒汁", 88.00, Category.MAIN, "/images/food/black_pepper_steak.png"));
        menuData.get(Category.MAIN).add(createFoodItem(203, "德式酸菜香肠配土豆泥", "German Sausage with Sauerkraut and Mashed Potatoes", "Bratwurst mit Sauerkraut und Kartoffelpüree", "传统德式", 68.00, Category.MAIN, "/images/food/bratwurst_with_sauerkraut.png"));
        menuData.get(Category.MAIN).add(createFoodItem(204, "意大利肉酱面", "Spaghetti Bolognese", "Spaghetti Bolognese", "经典意面", 45.00, Category.MAIN, "/images/food/spaghetti_bolognese.png"));
        menuData.get(Category.MAIN).add(createFoodItem(205, "扬州炒饭", "Yangzhou Fried Rice", "Yangzhou-Reis", "什锦炒饭", 38.00, Category.MAIN, "/images/food/yangzhou_fried_rice.png"));

        // 甜点 (DESSERT)
        menuData.get(Category.DESSERT).add(createFoodItem(301, "提拉米苏", "Tiramisu", "Tiramisu", "意式经典", 32.00, Category.DESSERT, "/images/food/tiramisu.png"));
        menuData.get(Category.DESSERT).add(createFoodItem(302, "黑森林蛋糕", "Black Forest Cake", "Schwarzwälder Kirschtorte", "巧克力樱桃", 35.00, Category.DESSERT, "/images/food/black_forest_cake.png"));
        menuData.get(Category.DESSERT).add(createFoodItem(303, "芒果布丁", "Mango Pudding", "Mangopudding", "香滑可口", 22.00, Category.DESSERT, "/images/food/mango_pudding.png"));
        menuData.get(Category.DESSERT).add(createFoodItem(304, "杨枝甘露", "Mango Sago Pomelo", "Mango-Sago-Pampelmuse", "港式甜品", 28.00, Category.DESSERT, "/images/food/mango_sago_pomelo.png"));
        menuData.get(Category.DESSERT).add(createFoodItem(305, "巧克力慕斯", "Chocolate Mousse", "Schokoladenmousse", "浓郁丝滑", 30.00, Category.DESSERT, "/images/food/chocolate_mousse.png"));

        // 饮料 (DRINK)
        menuData.get(Category.DRINK).add(createFoodItem(401, "柠檬可乐", "Lemon Coke", "Zitronen-Cola", "清爽解渴", 12.00, Category.DRINK, "/images/food/lemon_coke.png"));
        menuData.get(Category.DRINK).add(createFoodItem(402, "珍珠奶茶", "Bubble Tea", "Bubble-Tea", "经典奶茶", 18.00, Category.DRINK, "/images/food/bubble_tea.png"));
        menuData.get(Category.DRINK).add(createFoodItem(403, "鲜榨橙汁", "Fresh Orange Juice", "Frischer Orangensaft", "维C满满", 22.00, Category.DRINK, "/images/food/fresh_orange_juice.png"));
        menuData.get(Category.DRINK).add(createFoodItem(404, "德国小麦啤酒", "German Wheat Beer", "Hefeweizen", "醇厚麦香", 28.00, Category.DRINK, "/images/food/hefeweizen.png"));
        menuData.get(Category.DRINK).add(createFoodItem(405, "蜂蜜柚子茶", "Honey Pomelo Tea", "Honig-Pampelmuse-Tee", "暖胃饮品", 16.00, Category.DRINK, "/images/food/honey_pomelo_tea.png"));
    }

    private FoodItem createFoodItem(long id, String zhName, String enName, String deName, String desc, double price, Category category, String imagePath) {
        Map<String, String> names = new HashMap<>();
        names.put("zh", zhName);
        names.put("en", enName);
        names.put("de", deName);

        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("zh", desc);
        descriptions.put("en", desc);
        descriptions.put("de", desc);

        return new FoodItem(id, names, descriptions, BigDecimal.valueOf(price), category, imagePath);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel topLabel = new JLabel(LanguageManager.get("table") + ": " + order.getTableNumber(), SwingConstants.CENTER);
        topLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(topLabel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);

        categoryTabs = new JTabbedPane();
        for (Category cat : Category.values()) {
            JPanel catPanel = createCategoryPanel(cat);
            categoryTabs.addTab(cat.getDisplayName(LanguageManager.getCurrentLang()), catPanel);
        }
        splitPane.setLeftComponent(categoryTabs);

        orderArea = new JTextArea(15, 20);
        orderArea.setEditable(false);
        updateOrderDisplay();
        JScrollPane rightScroll = new JScrollPane(orderArea);
        rightScroll.setBorder(BorderFactory.createTitledBorder(LanguageManager.get("selected.items")));
        splitPane.setRightComponent(rightScroll);

        add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        clearBtn = new JButton(LanguageManager.get("clear.order"));
        clearBtn.addActionListener(e -> {
            order.clear();
            updateOrderDisplay();
            String[] options = {LanguageManager.get("ok")};
            JOptionPane.showOptionDialog(this,
                    LanguageManager.get("order.cleared"),
                    LanguageManager.get("prompt"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
        });
        bottomPanel.add(clearBtn);

        confirmBtn = new JButton(LanguageManager.get("confirm.order"));
        confirmBtn.addActionListener(e -> {
            if (order.getItems().isEmpty()) {
                String[] options = {LanguageManager.get("ok")};
                JOptionPane.showOptionDialog(this,
                        LanguageManager.get("please.select.items.first"),
                        LanguageManager.get("prompt"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0]);
            } else {
                navigator.showPayment();
            }
        });
        bottomPanel.add(confirmBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCategoryPanel(Category category) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        String lang = LanguageManager.getCurrentLang();

        for (FoodItem item : menuData.get(category)) {
            // 按钮文字：名称 + 价格（带货币符号）
            String buttonText = "<html>" + item.getName(lang) + "<br>" + LanguageManager.formatPrice(item.getPrice()) + "</html>";
            JButton btn = new JButton(buttonText);

            // 尝试加载图片
            String imagePath = item.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    java.net.URL imgURL = getClass().getResource(imagePath);
                    if (imgURL != null) {
                        ImageIcon icon = new ImageIcon(imgURL);
                        // 缩放图片到 60x60 像素（可根据需要调整）
                        Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        btn.setIcon(new ImageIcon(scaled));
                        // 设置文字位置：图片在上，文字在下
                        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
                        btn.setHorizontalTextPosition(SwingConstants.CENTER);
                        btn.setIconTextGap(5);
                    } else {
                        System.err.println("图片未找到: " + imagePath);
                    }
                } catch (Exception e) {
                    System.err.println("加载图片失败: " + imagePath + " - " + e.getMessage());
                }
            }

            btn.addActionListener(e -> addToOrder(item));
            panel.add(btn);
        }
        return panel;
    }

    private void addToOrder(FoodItem item) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(LanguageManager.get("input.quantity"));
        JTextField textField = new JTextField("1", 5);
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);

        int result = JOptionPane.showOptionDialog(this, panel,
                LanguageManager.get("input.quantity.title"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{LanguageManager.get("ok"), LanguageManager.get("cancel")},
                LanguageManager.get("ok"));

        if (result == JOptionPane.OK_OPTION) {
            int quantity;
            try {
                quantity = Integer.parseInt(textField.getText().trim());
                if (quantity <= 0) quantity = 1;
            } catch (NumberFormatException e) {
                quantity = 1;
            }
            order.addItem(new OrderItem(item, quantity));
            updateOrderDisplay();
        }
    }

    private void updateOrderDisplay() {
        StringBuilder sb = new StringBuilder();
        for (OrderItem oi : order.getItems()) {
            sb.append(oi.getFoodItem().getName(LanguageManager.getCurrentLang()))
                    .append(" × ").append(oi.getQuantity())
                    .append(" = ").append(LanguageManager.formatPrice(oi.getSubtotal())).append("\n");
        }
        sb.append("-------------------\n");
        sb.append(LanguageManager.get("total")).append(": ").append(LanguageManager.formatPrice(order.getTotalAmount()));
        orderArea.setText(sb.toString());
    }
}