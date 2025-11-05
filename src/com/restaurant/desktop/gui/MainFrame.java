package com.restaurant.desktop.gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.restaurant.desktop.entity.FoodMenuItem;
import com.restaurant.desktop.entity.Order;
import com.restaurant.desktop.entity.OrderItem;
import com.restaurant.desktop.service.LanguageService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final List<FoodMenuItem> menuItems = new ArrayList<>();
    private Order currentOrder;

    // 界面组件
    private JComboBox<String> tableComboBox;
    private JTabbedPane tabbedPane;
    private JPanel menuPanel;
    private JPanel orderPanel;
    private JTextArea orderTextArea;
    private JButton languageBtn;
    private JButton newOrderBtn;
    private JButton viewOrdersBtn;
    private JButton qrBtn;
    private JButton submitBtn;
    private JButton clearBtn;
    private JButton printBtn;
    private JPanel topPanel;
    private DefaultListModel<OrderStatusItem> statusListModel;
    private JPanel statusContainerPanel; // 新增：用于更新状态面板标题

    // 图片尺寸常量
    private static final int IMAGE_WIDTH = 80;
    private static final int IMAGE_HEIGHT = 80;

    // 搜索组件
    private JTextField searchField;
    private String currentSearchKeyword = "";

    public MainFrame() {
        initializeFrame();
        initializeMenu();
        initializeUI();
    }

    private void initializeFrame() {
        setTitle(LanguageService.getString("app.title"));
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置窗口图标
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/restaurant_icon.png"));
            if (icon.getImage() != null) {
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            System.out.println("图标文件未找到，使用默认图标");
        }
    }

    private void initializeMenu() {
        // 初始化测试菜品数据（多语言支持）
        Map<String, String> names1 = new HashMap<>();
        names1.put("zh", "宫保鸡丁");
        names1.put("en", "Kung Pao Chicken");
        names1.put("de", "Gong Bao Huhn");

        Map<String, String> descs1 = new HashMap<>();
        descs1.put("zh", "经典川菜，麻辣鲜香");
        descs1.put("en", "Classic Sichuan dish, spicy and delicious");
        descs1.put("de", "Klassisches Sichuan-Gericht, scharf und lecker");

        // 修改图片路径到 food 目录
        menuItems.add(new FoodMenuItem(1L, names1, descs1, new BigDecimal("48.00"), "主食", "/images/food/kungpao.png"));

        Map<String, String> names2 = new HashMap<>();
        names2.put("zh", "麻婆豆腐");
        names2.put("en", "Mapo Tofu");
        names2.put("de", "Mapo Tofu");

        Map<String, String> descs2 = new HashMap<>();
        descs2.put("zh", "麻辣嫩滑，下饭神器");
        descs2.put("en", "Spicy and tender, perfect with rice");
        descs2.put("de", "Scharf und zart, ideal mit Reis");

        menuItems.add(new FoodMenuItem(2L, names2, descs2, new BigDecimal("32.00"), "主食", "/images/food/tofu.png"));

        Map<String, String> names3 = new HashMap<>();
        names3.put("zh", "可乐");
        names3.put("en", "Coca-Cola");
        names3.put("de", "Coca-Cola");

        Map<String, String> descs3 = new HashMap<>();
        descs3.put("zh", "冰镇可乐");
        descs3.put("en", "Iced Coca-Cola");
        descs3.put("de", "Eiskalte Coca-Cola");

        menuItems.add(new FoodMenuItem(3L, names3, descs3, new BigDecimal("8.00"), "饮料", "/images/food/cola.png"));

        Map<String, String> names4 = new HashMap<>();
        names4.put("zh", "米饭");
        names4.put("en", "Rice");
        names4.put("de", "Reis");

        Map<String, String> descs4 = new HashMap<>();
        descs4.put("zh", "香喷喷的白米饭");
        descs4.put("en", "Fragrant white rice");
        descs4.put("de", "Duftender weißer Reis");

        menuItems.add(new FoodMenuItem(4L, names4, descs4, new BigDecimal("3.00"), "主食", "/images/food/rice.png"));

        Map<String, String> names5 = new HashMap<>();
        names5.put("zh", "提拉米苏");
        names5.put("en", "Tiramisu");
        names5.put("de", "Tiramisu");

        Map<String, String> descs5 = new HashMap<>();
        descs5.put("zh", "意大利经典甜品");
        descs5.put("en", "Classic Italian dessert");
        descs5.put("de", "Klassisches italienisches Dessert");

        menuItems.add(new FoodMenuItem(5L, names5, descs5, new BigDecimal("28.00"), "甜点", "/images/food/tiramisu.png"));
    }

    private void initializeUI() {
        setModernLookAndFeel();

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));

        // 顶部控制面板
        topPanel = createTopPanel();

        // 中间标签页
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(255, 255, 255));
        menuPanel = createMenuPanel();
        orderPanel = createOrderPanel();

        tabbedPane.addTab(LanguageService.getString("menu.order"), menuPanel);
        tabbedPane.addTab(LanguageService.getString("current.order"), orderPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    /**
     * 设置现代化外观
     */
    private void setModernLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // 设置现代化UI参数
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 5);
            UIManager.put("ProgressBar.arc", 10);
            UIManager.put("ScrollBar.thumbArc", 10);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("table.management")));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel(LanguageService.getString("select.table") + ":"));
        tableComboBox = new JComboBox<>(new String[]{"1号桌", "2号桌", "3号桌", "4号桌", "5号桌"});
        tableComboBox.setBackground(Color.WHITE);
        panel.add(tableComboBox);

        newOrderBtn = createModernButton(LanguageService.getString("new.order"), new Color(70, 130, 180));
        newOrderBtn.addActionListener(e -> startNewOrder());
        panel.add(newOrderBtn);

        viewOrdersBtn = createModernButton(LanguageService.getString("view.history"), new Color(100, 149, 237));
        viewOrdersBtn.addActionListener(e -> viewOrderHistory());
        panel.add(viewOrdersBtn);

        // 添加扫码点餐按钮
        qrBtn = createModernButton("📱 " + LanguageService.getString("scan.order"), new Color(50, 205, 50));
        qrBtn.addActionListener(e -> showQRCodeDialog());
        panel.add(qrBtn);

        // 添加语言切换按钮
        languageBtn = createModernButton("🌐 " + LanguageService.getString("chinese"), new Color(147, 112, 219));
        languageBtn.addActionListener(e -> showLanguageDialog());
        panel.add(languageBtn);

        // 添加搜索框
        searchField = createSearchField();
        panel.add(searchField);

        return panel;
    }

    // 搜索框
    private JTextField createSearchField() {
        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("search.dish")));
        searchField.addActionListener(e -> {
            currentSearchKeyword = searchField.getText().trim().toLowerCase();
            // 重新加载菜单面板以应用筛选
            menuPanel.removeAll();
            menuPanel = createMenuPanel();
            tabbedPane.setComponentAt(0, menuPanel);
            menuPanel.revalidate();
            menuPanel.repaint();
        });
        return searchField;
    }

    // 语言切换对话框
    private void showLanguageDialog() {
        String[] languages = {LanguageService.getString("chinese"), LanguageService.getString("english"), LanguageService.getString("german")};
        String[] codes = {"zh", "en", "de"};

        String choice = (String) JOptionPane.showInputDialog(this,
                LanguageService.getString("select.language"),
                LanguageService.getString("language.setting"),
                JOptionPane.QUESTION_MESSAGE,
                null,
                languages,
                languages[0]);

        if (choice != null) {
            int index = Arrays.asList(languages).indexOf(choice);
            LanguageService.setLanguage(codes[index]);
            updateUILanguage();
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("language.changed.to") + ": " + choice,
                    LanguageService.getString("prompt"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 更新界面语言
    private void updateUILanguage() {
        // 更新窗口标题
        setTitle(LanguageService.getString("app.title"));

        // 更新顶部面板
        if (topPanel.getComponentCount() > 0 && topPanel.getComponent(0) instanceof JLabel) {
            ((JLabel)topPanel.getComponent(0)).setText(LanguageService.getString("select.table") + ":");
        }
        newOrderBtn.setText(LanguageService.getString("new.order"));
        viewOrdersBtn.setText(LanguageService.getString("view.history"));
        qrBtn.setText("📱 " + LanguageService.getString("scan.order"));
        languageBtn.setText("🌐 " + LanguageService.getString(LanguageService.getCurrentLanguage()));
        searchField.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("search.dish")));

        // 更新标签页
        tabbedPane.setTitleAt(0, LanguageService.getString("menu.order"));
        tabbedPane.setTitleAt(1, LanguageService.getString("current.order"));

        // 更新订单面板按钮
        submitBtn.setText(LanguageService.getString("submit.pay"));
        clearBtn.setText(LanguageService.getString("clear.order"));
        printBtn.setText(LanguageService.getString("print.order"));

        // 更新订单状态面板标题
        if (statusContainerPanel != null) {
            Border border = BorderFactory.createTitledBorder(LanguageService.getString("order.status"));
            statusContainerPanel.setBorder(border);
        }

        // 更新菜单面板
        menuPanel.removeAll();
        menuPanel = createMenuPanel();
        tabbedPane.setComponentAt(0, menuPanel);

        // 更新当前订单显示
        updateOrderDisplay();
    }

    // 评价对话框
    private void showRatingDialog(OrderItem orderItem) {
        if (orderItem == null || orderItem.getFoodMenuItem() == null) {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("invalid.order.item"),
                    LanguageService.getString("error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(LanguageService.getString("rate.dish") + " " + orderItem.getFoodMenuItem().getName());
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));

        // 星级评分
        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        starPanel.add(new JLabel(LanguageService.getString("rating") + ": "));

        ButtonGroup starGroup = new ButtonGroup();
        JRadioButton[] stars = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JRadioButton(String.valueOf(i + 1));
            starGroup.add(stars[i]);
            starPanel.add(stars[i]);
        }
        // 默认选中3星
        stars[2].setSelected(true);

        // 评论区域
        JTextArea commentArea = new JTextArea(4, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("comment")));
        commentArea.setText(getOrderItemComment(orderItem));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(starPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(commentArea), BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(this, panel,
                LanguageService.getString("dish.rating"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int rating = 0;
            for (int i = 0; i < 5; i++) {
                if (stars[i].isSelected()) {
                    rating = i + 1;
                    break;
                }
            }
            setOrderItemRating(orderItem, rating);
            setOrderItemComment(orderItem, commentArea.getText().trim());
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("thank.you.for.rating"),
                    LanguageService.getString("rating.success"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 辅助方法 - 获取订单项评论
    private String getOrderItemComment(OrderItem orderItem) {
        try {
            return orderItem.getComment() != null ? orderItem.getComment() : "";
        } catch (Exception e) {
            return "";
        }
    }

    // 辅助方法 - 设置订单项评分
    private void setOrderItemRating(OrderItem orderItem, Integer rating) {
        try {
            orderItem.setRating(rating);
        } catch (Exception e) {
            // 忽略
        }
    }

    // 辅助方法 - 设置订单项评论
    private void setOrderItemComment(OrderItem orderItem, String comment) {
        try {
            orderItem.setComment(comment);
        } catch (Exception e) {
            // 忽略
        }
    }

    // 模拟扫码点餐功能
    private void simulateQRCodeOrder() {
        String simulatedOrder = LanguageService.getString("simulated.qr.order") + ":\n" +
                LanguageService.getString("table") + ": 3号桌\n" +
                LanguageService.getString("dish") + ": " + menuItems.get(0).getName() + " × 2\n" +
                LanguageService.getString("dish") + ": " + menuItems.get(2).getName() + " × 1\n" +
                LanguageService.getString("total") + ": ¥104.00";

        JOptionPane.showMessageDialog(this, simulatedOrder,
                LanguageService.getString("qr.order.received"), JOptionPane.INFORMATION_MESSAGE);

        // 自动创建订单
        currentOrder = new Order("3");
        currentOrder.addItem(new OrderItem(menuItems.get(0), 2)); // 宫保鸡丁
        currentOrder.addItem(new OrderItem(menuItems.get(2), 1)); // 可乐
        updateOrderDisplay();
    }

    /**
     * 创建现代化按钮
     */
    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // 创建分类菜单
        JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 15, 15));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        categoryPanel.setBackground(new Color(245, 245, 245));

        // 按分类显示菜品
        Set<String> categories = new HashSet<>();
        for (FoodMenuItem item : menuItems) {
            // 应用搜索筛选，不区分大小写
            if (!currentSearchKeyword.isEmpty()) {
                boolean nameMatch = item.getName() != null &&
                        item.getName().toLowerCase().contains(currentSearchKeyword);
                boolean descMatch = item.getDescription() != null &&
                        item.getDescription().toLowerCase().contains(currentSearchKeyword);
                if (!nameMatch && !descMatch) {
                    continue;
                }
            }
            categories.add(item.getCategory());
        }

        for (String category : categories) {
            JPanel categoryGroup = createCategoryPanel(category);
            categoryPanel.add(categoryGroup);
        }

        JScrollPane scrollPane = new JScrollPane(categoryPanel);
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCategoryPanel(String category) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "🍽️ " + category
        ));
        panel.setBackground(Color.WHITE);

        JPanel itemsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        itemsPanel.setBackground(Color.WHITE);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (FoodMenuItem item : menuItems) {
            // 应用搜索筛选
            if (!currentSearchKeyword.isEmpty()) {
                boolean nameMatch = item.getName() != null &&
                        item.getName().toLowerCase().contains(currentSearchKeyword);
                boolean descMatch = item.getDescription() != null &&
                        item.getDescription().toLowerCase().contains(currentSearchKeyword);
                if (!nameMatch && !descMatch) {
                    continue;
                }
            }

            if (item.getCategory().equals(category)) {
                JPanel itemPanel = createMenuItemPanel(item);
                itemsPanel.add(itemPanel);
            }
        }

        panel.add(itemsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMenuItemPanel(FoodMenuItem item) {
        JPanel panel = new JPanel(new BorderLayout(15, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        panel.setBackground(Color.WHITE);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 左侧：菜品图片
        JLabel imageLabel = createImageLabel(item);

        // 中间：菜品信息
        JPanel infoPanel = createInfoPanel(item);

        // 右侧：数量控制和添加按钮
        JPanel controlPanel = createControlPanel(item);

        // 组装面板：图片 + 信息 + 控制
        JPanel contentPanel = new JPanel(new BorderLayout(15, 0));
        contentPanel.add(imageLabel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel, BorderLayout.EAST);

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * 创建菜品图片标签（带加载失败处理）
     */
    private JLabel createImageLabel(FoodMenuItem item) {
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = item.getImageIcon();

        if (originalIcon != null && originalIcon.getImage() != null) {
            // 缩放图片到合适大小（保持比例）
            Image originalImage = originalIcon.getImage();
            Image scaledImage = scaleImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            // 图片加载失败时显示默认占位图
            try {
                imageLabel.setIcon(new ImageIcon(getClass().getResource("/images/placeholder.png")));
            } catch (Exception e) {
                imageLabel.setText("?");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
            imageLabel.setToolTipText(LanguageService.getString("image.load.failed"));
        }

        imageLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        imageLabel.setBackground(new Color(250, 250, 250));
        imageLabel.setOpaque(true);
        return imageLabel;
    }

    /**
     * 保持比例缩放图片
     */
    private Image scaleImage(Image image, int maxWidth, int maxHeight) {
        // 处理图片宽度或高度为null的情况
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if (width <= 0 || height <= 0) {
            return image.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
        }

        double scale = Math.min((double) maxWidth / width,
                (double) maxHeight / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);
        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    /**
     * 创建菜品信息面板
     */
    private JPanel createInfoPanel(FoodMenuItem item) {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        String name = item.getName() != null ? item.getName() : "未知菜品";
        String desc = item.getDescription() != null ? item.getDescription() : "";
        String price = item.getPrice() != null ? item.getPrice().toString() : "0.00";

        JLabel nameLabel = new JLabel("<html><b style='font-size:16px; color: #333;'>" + name + "</b></html>");
        JLabel descLabel = new JLabel("<html><font color='#666' style='font-size:13px;'>" + desc + "</font></html>");
        JLabel priceLabel = new JLabel("<html><b style='color: #e44d26; font-size:18px;'>¥" + price + "</b></html>");

        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(descLabel, BorderLayout.CENTER);
        infoPanel.add(priceLabel, BorderLayout.SOUTH);

        return infoPanel;
    }

    /**
     * 创建数量控制面板
     */
    private JPanel createControlPanel(FoodMenuItem item) {
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(Color.WHITE);

        // 创建数量标签和按钮
        JLabel quantityLabel = new JLabel("0");
        quantityLabel.setPreferredSize(new Dimension(40, 30));
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        quantityLabel.setOpaque(true);
        quantityLabel.setBackground(Color.WHITE);

        JButton minusBtn = createModernButton("-", new Color(220, 220, 220));
        minusBtn.setForeground(Color.BLACK);
        minusBtn.setPreferredSize(new Dimension(35, 30));

        JButton plusBtn = createModernButton("+", new Color(220, 220, 220));
        plusBtn.setForeground(Color.BLACK);
        plusBtn.setPreferredSize(new Dimension(35, 30));

        JButton addBtn = createModernButton(LanguageService.getString("add.to.order"), new Color(70, 130, 180));

        // 按钮事件
        setupButtonActions(item, minusBtn, plusBtn, addBtn, quantityLabel);

        controlPanel.add(new JLabel(LanguageService.getString("quantity") + ":"));
        controlPanel.add(minusBtn);
        controlPanel.add(quantityLabel);
        controlPanel.add(plusBtn);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(addBtn);

        return controlPanel;
    }

    /**
     * 设置按钮动作
     */
    private void setupButtonActions(FoodMenuItem item, JButton minusBtn, JButton plusBtn, JButton addBtn, JLabel quantityLabel) {
        minusBtn.addActionListener(e -> {
            try {
                int qty = Integer.parseInt(quantityLabel.getText());
                if (qty > 0) {
                    quantityLabel.setText(String.valueOf(qty - 1));
                }
            } catch (NumberFormatException ex) {
                quantityLabel.setText("0");
            }
        });

        plusBtn.addActionListener(e -> {
            try {
                int qty = Integer.parseInt(quantityLabel.getText());
                quantityLabel.setText(String.valueOf(qty + 1));
            } catch (NumberFormatException ex) {
                quantityLabel.setText("0");
            }
        });

        addBtn.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(quantityLabel.getText());
                if (quantity > 0) {
                    if (currentOrder != null) {
                        addToOrder(item, quantity);
                        quantityLabel.setText("0");
                        JOptionPane.showMessageDialog(this,
                                "✅ " + LanguageService.getString("added") + " " + item.getName() + " × " + quantity,
                                LanguageService.getString("add.success"), JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                LanguageService.getString("please.start.order.first"),
                                LanguageService.getString("prompt"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        LanguageService.getString("invalid.quantity"),
                        LanguageService.getString("error"), JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // 创建订单详情和状态的分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 订单详情区域
        orderTextArea = new JTextArea();
        orderTextArea.setEditable(false);
        orderTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        orderTextArea.setBackground(Color.WHITE);
        orderTextArea.setText(LanguageService.getString("please.start.order.first"));

        JScrollPane textScrollPane = new JScrollPane(orderTextArea);
        textScrollPane.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("order.details")));

        // 订单状态跟踪区域
        statusContainerPanel = createOrderStatusPanel();

        splitPane.setTopComponent(textScrollPane);
        splitPane.setBottomComponent(statusContainerPanel);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(245, 245, 245));

        submitBtn = createModernButton(LanguageService.getString("submit.pay"), new Color(50, 205, 50));
        clearBtn = createModernButton(LanguageService.getString("clear.order"), new Color(220, 20, 60));
        printBtn = createModernButton(LanguageService.getString("print.order"), new Color(100, 149, 237));

        // 添加评价按钮
        JButton ratingBtn = createModernButton(LanguageService.getString("rate.dishes"), new Color(255, 165, 0));
        ratingBtn.addActionListener(e -> showOrderItemsForRating());

        submitBtn.addActionListener(e -> submitOrder());
        clearBtn.addActionListener(e -> clearOrder());
        printBtn.addActionListener(e -> printOrder());

        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(printBtn);
        buttonPanel.add(ratingBtn);

        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // 显示可评价的菜品列表
    private void showOrderItemsForRating() {
        if (currentOrder == null || currentOrder.getItems() == null || currentOrder.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("no.items.to.rate"),
                    LanguageService.getString("prompt"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultListModel<OrderItem> listModel = new DefaultListModel<>();
        for (OrderItem item : currentOrder.getItems()) {
            listModel.addElement(item);
        }

        JList<OrderItem> itemList = new JList<>(listModel);
        itemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                OrderItem item = (OrderItem) value;
                if (item != null && item.getFoodMenuItem() != null) {
                    setText(item.getFoodMenuItem().getName() + " × " + item.getQuantity());
                } else {
                    setText(LanguageService.getString("unknown.item"));
                }
                return this;
            }
        });

        int result = JOptionPane.showConfirmDialog(this,
                new JScrollPane(itemList),
                LanguageService.getString("select.dish.to.rate"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && itemList.getSelectedValue() != null) {
            showRatingDialog(itemList.getSelectedValue());
        }
    }

    // 订单状态跟踪面板
    private JPanel createOrderStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("order.status")));

        statusListModel = new DefaultListModel<>();
        JList<OrderStatusItem> statusList = new JList<>(statusListModel);
        statusList.setCellRenderer(new OrderStatusCellRenderer());

        // 初始化订单状态列表
        if (currentOrder != null && currentOrder.getItems() != null) {
            for (OrderItem item : currentOrder.getItems()) {
                statusListModel.addElement(new OrderStatusItem(item, false));
            }
        }

        panel.add(new JScrollPane(statusList), BorderLayout.CENTER);
        return panel;
    }

    // 订单状态项类 - 修改为public以便渲染器访问
    public class OrderStatusItem {
        private OrderItem orderItem;
        private boolean isServed;

        public OrderStatusItem(OrderItem item, boolean served) {
            this.orderItem = item;
            this.isServed = served;
        }

        public OrderItem getOrderItem() { return orderItem; }
        public boolean isServed() { return isServed; }
        public void setServed(boolean served) { isServed = served; }
    }

    // 自定义单元格渲染器（包含勾选框）
    private class OrderStatusCellRenderer extends JCheckBox implements ListCellRenderer<OrderStatusItem> {
        @Override
        public Component getListCellRendererComponent(JList<? extends OrderStatusItem> list,
                                                      OrderStatusItem value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(""); // 默认空文本

            if (value != null && value.getOrderItem() != null && value.getOrderItem().getFoodMenuItem() != null) {
                setText(value.getOrderItem().getFoodMenuItem().getName() + " × " + value.getOrderItem().getQuantity());
                setSelected(value.isServed());
            }

            // 勾选事件处理
            addActionListener(e -> {
                if (value != null) {
                    value.setServed(isSelected());
                    // 检查是否所有菜品都已上齐
                    checkAllServed();
                }
            });

            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }

    // 检查是否所有菜品都已上齐
    private void checkAllServed() {
        if (currentOrder == null || currentOrder.getItems() == null || statusListModel.isEmpty()) return;

        boolean allServed = true;
        for (int i = 0; i < statusListModel.size(); i++) {
            OrderStatusItem item = statusListModel.getElementAt(i);
            if (item == null || !item.isServed()) {
                allServed = false;
                break;
            }
        }

        if (allServed) {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("enjoy.meal"),
                    LanguageService.getString("all.served"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 显示扫码点餐对话框
     */
    private void showQRCodeDialog() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("📱 " + LanguageService.getString("scan.order"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));

        JTextArea qrInfo = new JTextArea();
        qrInfo.setText(LanguageService.getString("scan.qr.instructions") + "\n\n" +
                "1. " + LanguageService.getString("scan.step1") + "\n" +
                "2. " + LanguageService.getString("scan.step2") + "\n" +
                "3. " + LanguageService.getString("scan.step3") + "\n" +
                "4. " + LanguageService.getString("scan.step4") + "\n\n" +
                "💡 " + LanguageService.getString("scan.tip"));
        qrInfo.setEditable(false);
        qrInfo.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        qrInfo.setBackground(panel.getBackground());

        // 生成当前桌台的二维码
        String table = (String) tableComboBox.getSelectedItem();
        String tableNumber = table != null ? table.replace(LanguageService.getString("table.suffix"), "") : "1";
        JPanel qrPanel = generateTableQRCodePanel(tableNumber);

        // 添加生成二维码按钮
        JButton generateBtn = createModernButton(LanguageService.getString("generate.qrcode"), new Color(70, 130, 180));
        generateBtn.addActionListener(e -> {
            String t = (String) tableComboBox.getSelectedItem();
            String tNumber = t != null ? t.replace(LanguageService.getString("table.suffix"), "") : "1";
            generateTableQRCode(tNumber);
            panel.remove(qrPanel);
            panel.add(generateTableQRCodePanel(tNumber), BorderLayout.SOUTH);
            panel.revalidate();
            panel.repaint();
        });

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(generateBtn, BorderLayout.NORTH);
        southPanel.add(qrPanel, BorderLayout.SOUTH);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(qrInfo), BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, LanguageService.getString("scan.order"), JOptionPane.INFORMATION_MESSAGE);
    }

    // 生成桌台二维码并返回面板
    private JPanel generateTableQRCodePanel(String tableNumber) {
        JPanel qrPanel = new JPanel();
        qrPanel.setPreferredSize(new Dimension(200, 200));
        qrPanel.setBackground(Color.WHITE);
        qrPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        try {
            // 生成二维码图片
            BufferedImage qrImage = generateTableQRCodeImage(tableNumber);
            qrPanel.add(new JLabel(new ImageIcon(qrImage)));
        } catch (Exception e) {
            qrPanel.add(new JLabel(LanguageService.getString("qrcode.generate.failed")));
        }

        return qrPanel;
    }

    // 生成桌台二维码图片
    private BufferedImage generateTableQRCodeImage(String tableNumber) throws Exception {
        // 二维码内容：可以是点餐系统的URL或桌台标识
        String content = "http://your-restaurant-domain.com/order?table=" + (tableNumber != null ? tableNumber : "");

        int width = 200;
        int height = 200;
        String format = "png";

        HashMap<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);

        // 转换为BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return image;
    }

    // 生成并保存桌台二维码
    private void generateTableQRCode(String tableNumber) {
        try {
            // 生成二维码图片
            BufferedImage qrImage = generateTableQRCodeImage(tableNumber);

            // 保存二维码图片
            File qrDir = new File("qrcodes");
            if (!qrDir.exists()) {
                qrDir.mkdirs();
            }

            String tNumber = tableNumber != null ? tableNumber : "unknown";
            File qrFile = new File(qrDir, "table_" + tNumber + ".png");
            ImageIO.write(qrImage, "png", qrFile);

            // 显示生成的二维码
            JOptionPane.showMessageDialog(this,
                    new JLabel(new ImageIcon(qrImage)),
                    LanguageService.getString("table.qrcode") + tNumber,
                    JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("qrcode.generate.failed") + e.getMessage(),
                    LanguageService.getString("error"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startNewOrder() {
        String table = (String) tableComboBox.getSelectedItem();
        if (table != null) {
            String tableNumber = table.replace(LanguageService.getString("table.suffix"), "");
            currentOrder = new Order(tableNumber);
            updateOrderDisplay();

            JOptionPane.showMessageDialog(this,
                    "✅ " + LanguageService.getString("new.order.created") + table,
                    LanguageService.getString("new.order"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addToOrder(FoodMenuItem item, int quantity) {
        if (currentOrder != null && item != null) {
            // 检查是否已存在该菜品，存在则更新数量
            boolean found = false;
            if (currentOrder.getItems() != null) {
                for (OrderItem orderItem : currentOrder.getItems()) {
                    if (orderItem != null && orderItem.getFoodMenuItem() != null &&
                            item.getId() != null && item.getId().equals(orderItem.getFoodMenuItem().getId())) {
                        orderItem.setQuantity(orderItem.getQuantity() + quantity);
                        found = true;
                        break;
                    }
                }
            }

            // 不存在则添加新订单项
            if (!found) {
                currentOrder.addItem(new OrderItem(item, quantity));
            }

            updateOrderDisplay();
            tabbedPane.setSelectedIndex(1); // 切换到订单标签页
        }
    }

    private void updateOrderDisplay() {
        if (currentOrder == null || currentOrder.getItems() == null || currentOrder.getItems().isEmpty()) {
            orderTextArea.setText(LanguageService.getString("no.order.items"));
            if (statusListModel != null) {
                statusListModel.clear();
            }
            return;
        }

        // 更新订单详情
        StringBuilder sb = new StringBuilder();
        sb.append(LanguageService.getString("table") + ": ").append(currentOrder.getTableNumber()).append(LanguageService.getString("table.suffix")).append("\n\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append("             " + LanguageService.getString("order.details") + "\n");
        sb.append("═══════════════════════════════════════\n\n");

        for (OrderItem orderItem : currentOrder.getItems()) {
            if (orderItem != null && orderItem.getFoodMenuItem() != null) {
                sb.append(String.format(" %s × %d\n",
                        orderItem.getFoodMenuItem().getName(),
                        orderItem.getQuantity()));
                sb.append(String.format("     " + LanguageService.getString("subtotal") + ": ¥%.2f\n\n",
                        orderItem.getSubtotal()));
            }
        }

        sb.append("═══════════════════════════════════════\n");
        sb.append(String.format(LanguageService.getString("total") + ": ¥%.2f\n", currentOrder.getTotalAmount()));
        sb.append("═══════════════════════════════════════\n");

        orderTextArea.setText(sb.toString());

        // 更新订单状态列表
        if (statusListModel != null) {
            statusListModel.clear();
            for (OrderItem item : currentOrder.getItems()) {
                if (item != null) {
                    statusListModel.addElement(new OrderStatusItem(item, false));
                }
            }
        }
    }

    private void submitOrder() {
        if (currentOrder == null || currentOrder.getItems() == null || currentOrder.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("no.order.to.submit"),
                    LanguageService.getString("prompt"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 显示支付对话框
        showPaymentDialog();
    }

    /**
     * 显示支付对话框
     */
    private void showPaymentDialog() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("💰 " + LanguageService.getString("select.payment.method"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));

        // 支付方式选择
        JPanel paymentPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        paymentPanel.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("payment.method")));

        JRadioButton wechatBtn = new JRadioButton("💚 " + LanguageService.getString("wechat.pay"));
        JRadioButton alipayBtn = new JRadioButton("💙 " + LanguageService.getString("alipay"));
        JRadioButton cashBtn = new JRadioButton("💵 " + LanguageService.getString("cash.payment"));
        JRadioButton cardBtn = new JRadioButton("💳 " + LanguageService.getString("card.payment"));

        ButtonGroup group = new ButtonGroup();
        group.add(wechatBtn);
        group.add(alipayBtn);
        group.add(cashBtn);
        group.add(cardBtn);

        wechatBtn.setSelected(true);

        paymentPanel.add(wechatBtn);
        paymentPanel.add(alipayBtn);
        paymentPanel.add(cashBtn);
        paymentPanel.add(cardBtn);

        // 订单摘要
        JTextArea orderSummary = new JTextArea();
        orderSummary.setText(buildOrderSummary());
        orderSummary.setEditable(false);
        orderSummary.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        orderSummary.setBackground(panel.getBackground());

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(orderSummary), BorderLayout.CENTER);
        panel.add(paymentPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(this, panel, LanguageService.getString("pay.order"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // 保存订单
            saveOrderToFile(currentOrder);

            // 模拟支付成功
            JOptionPane.showMessageDialog(this,
                    "🎉 " + LanguageService.getString("payment.success") + "\n" +
                            LanguageService.getString("order.amount") + ": ¥" + currentOrder.getTotalAmount() +
                            "\n\n" + LanguageService.getString("enjoy.your.meal"),
                    LanguageService.getString("payment.success"), JOptionPane.INFORMATION_MESSAGE);

            // 重置当前订单
            currentOrder = null;
            updateOrderDisplay();
        }
    }

    /**
     * 构建订单摘要
     */
    private String buildOrderSummary() {
        if (currentOrder == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(LanguageService.getString("order.summary") + ":\n");
        sb.append(LanguageService.getString("table") + ": ").append(currentOrder.getTableNumber()).append(LanguageService.getString("table.suffix")).append("\n");
        sb.append(LanguageService.getString("total.amount") + ": ¥").append(currentOrder.getTotalAmount()).append("\n\n");
        sb.append(LanguageService.getString("dish.list") + ":\n");

        if (currentOrder.getItems() != null) {
            for (OrderItem item : currentOrder.getItems()) {
                if (item != null && item.getFoodMenuItem() != null) {
                    sb.append("• ").append(item.getFoodMenuItem().getName())
                            .append(" × ").append(item.getQuantity())
                            .append(" = ¥").append(item.getSubtotal()).append("\n");
                }
            }
        }

        return sb.toString();
    }

    private void saveOrderToFile(Order order) {
        // 实际应用中应保存到数据库
        if (order != null) {
            System.out.println(LanguageService.getString("saving.order") + ": " + order.getTableNumber() +
                    LanguageService.getString("table.suffix") + ", " +
                    LanguageService.getString("amount") + ": " + order.getTotalAmount());
        }
    }

    private void clearOrder() {
        if (currentOrder != null && currentOrder.getItems() != null) {
            currentOrder.getItems().clear();
            updateOrderDisplay();
        }
    }

    private void printOrder() {
        if (currentOrder != null && currentOrder.getItems() != null && !currentOrder.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("order.sent.to.printer"),
                    LanguageService.getString("print"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    LanguageService.getString("no.order.to.print"),
                    LanguageService.getString("prompt"), JOptionPane.WARNING_MESSAGE);
        }
    }

    private void viewOrderHistory() {
        JOptionPane.showMessageDialog(this,
                LanguageService.getString("order.history.developing"),
                LanguageService.getString("prompt"), JOptionPane.INFORMATION_MESSAGE);
    }

    // 启动应用
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}