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
        // 现有的5个菜品...

        // === 新增6个主食 ===
        // 新增菜品：松仁玉米（素食）
        Map<String, String> names6 = new HashMap<>();
        names6.put("zh", "松仁玉米");
        names6.put("en", "Pine Nut Corn");
        names6.put("de", "Kiefernnuss-Mais");
        Map<String, String> descs6 = new HashMap<>();
        descs6.put("zh", "香甜可口，营养丰富");
        descs6.put("en", "Sweet and delicious, rich in nutrition");
        descs6.put("de", "Süß und lecker, nahrhaftreich");
        menuItems.add(new FoodMenuItem(6L, names6, descs6, new BigDecimal("36.00"), "素菜", "/images/food/corn.png"));

// 新增菜品：香菇青菜（菌类+蔬菜）
        Map<String, String> names7 = new HashMap<>();
        names7.put("zh", "香菇青菜");
        names7.put("en", "Mushroom and Greens");
        names7.put("de", "Pilz und Grüne");
        Map<String, String> descs7 = new HashMap<>();
        descs7.put("zh", "清爽解腻，鲜香可口");
        descs7.put("en", "Refreshing, not greasy, fresh and tasty");
        descs7.put("de", "Erfrischend, nicht fettig, frisch und lecker");
        menuItems.add(new FoodMenuItem(7L, names7, descs7, new BigDecimal("28.00"), "素菜", "/images/food/mushroom.png"));
        Map<String, String> names8 = new HashMap<>();
        names8.put("zh", "清蒸鲈鱼");
        names8.put("en", "Steamed Sea Bass");
        names8.put("de", "Gedämpfter Seebarsch");
        Map<String, String> descs8 = new HashMap<>();
        descs8.put("zh", "鲜嫩爽滑，营养丰富");
        descs8.put("en", "Tender and smooth, nutritious");
        descs8.put("de", "Zart und glatt, nahrhaft");
        menuItems.add(new FoodMenuItem(8L, names8, descs8, new BigDecimal("88.00"), "主食", "/images/food/fish.png"));

        Map<String, String> names9 = new HashMap<>();
        names9.put("zh", "担担面");
        names9.put("en", "Dandan Noodles");
        names9.put("de", "Dandan-Nudeln");
        Map<String, String> descs9 = new HashMap<>();
        descs9.put("zh", "麻辣鲜香，四川特色");
        descs9.put("en", "Spicy and fragrant, Sichuan specialty");
        descs9.put("de", "Scharf und duftend, Sichuan-Spezialität");
        menuItems.add(new FoodMenuItem(9L, names9, descs9, new BigDecimal("28.00"), "主食", "/images/food/noodles.png"));

        Map<String, String> names10 = new HashMap<>();
        names10.put("zh", "小笼包");
        names10.put("en", "Soup Dumplings");
        names10.put("de", "Suppenknödel");
        Map<String, String> descs10 = new HashMap<>();
        descs10.put("zh", "皮薄馅大，汤汁鲜美");
        descs10.put("en", "Thin skin, rich filling, delicious soup");
        descs10.put("de", "Dünne Haut, reichhaltige Füllung, köstliche Suppe");
        menuItems.add(new FoodMenuItem(10L, names10, descs10, new BigDecimal("35.00"), "主食", "/images/food/dumplings.png"));

        Map<String, String> names11 = new HashMap<>();
        names11.put("zh", "扬州炒饭");
        names11.put("en", "Yangzhou Fried Rice");
        names11.put("de", "Yangzhou gebratener Reis");
        Map<String, String> descs11 = new HashMap<>();
        descs11.put("zh", "粒粒分明，香气扑鼻");
        descs11.put("en", "Separate grains, aromatic");
        descs11.put("de", "Getrennte Körner, aromatisch");
        menuItems.add(new FoodMenuItem(11L, names11, descs11, new BigDecimal("25.00"), "主食", "/images/food/fried_rice.png"));

        // === 新增4个饮料 ===
        Map<String, String> names12 = new HashMap<>();
        names12.put("zh", "橙汁");
        names12.put("en", "Orange Juice");
        names12.put("de", "Orangensaft");
        Map<String, String> descs12 = new HashMap<>();
        descs12.put("zh", "鲜榨橙汁，维生素丰富");
        descs12.put("en", "Freshly squeezed orange juice, rich in vitamins");
        descs12.put("de", "Frisch gepresster Orangensaft, reich an Vitaminen");
        menuItems.add(new FoodMenuItem(12L, names12, descs12, new BigDecimal("18.00"), "饮料", "/images/food/orange_juice.png"));

        Map<String, String> names13 = new HashMap<>();
        names13.put("zh", "拿铁咖啡");
        names13.put("en", "Latte");
        names13.put("de", "Latte");
        Map<String, String> descs13 = new HashMap<>();
        descs13.put("zh", "香浓咖啡，绵密奶泡");
        descs13.put("en", "Rich coffee, creamy foam");
        descs13.put("de", "Kräftiger Kaffee, cremiger Schaum");
        menuItems.add(new FoodMenuItem(13L, names13, descs13, new BigDecimal("32.00"), "饮料", "/images/food/latte.png"));

        Map<String, String> names14 = new HashMap<>();
        names14.put("zh", "普洱茶");
        names14.put("en", "Pu'er Tea");
        names14.put("de", "Pu-Erh-Tee");
        Map<String, String> descs14 = new HashMap<>();
        descs14.put("zh", "陈香醇厚，养生佳品");
        descs14.put("en", "Mellow aroma, healthy drink");
        descs14.put("de", "Milder Duft, gesundes Getränk");
        menuItems.add(new FoodMenuItem(14L, names14, descs14, new BigDecimal("25.00"), "饮料", "/images/food/tea.png"));

        Map<String, String> names15 = new HashMap<>();
        names15.put("zh", "芒果冰沙");
        names15.put("en", "Mango Smoothie");
        names15.put("de", "Mango-Smoothie");
        Map<String, String> descs15 = new HashMap<>();
        descs15.put("zh", "新鲜芒果，冰爽可口");
        descs15.put("en", "Fresh mango, cool and delicious");
        descs15.put("de", "Frische Mango, kühl und lecker");
        menuItems.add(new FoodMenuItem(15L, names15, descs15, new BigDecimal("28.00"), "饮料", "/images/food/smoothie.png"));

        // === 新增4个甜点 ===
        Map<String, String> names16 = new HashMap<>();
        names16.put("zh", "巧克力蛋糕");
        names16.put("en", "Chocolate Cake");
        names16.put("de", "Schokoladenkuchen");
        Map<String, String> descs16 = new HashMap<>();
        descs16.put("zh", "浓郁巧克力，丝滑口感");
        descs16.put("en", "Rich chocolate, silky texture");
        descs16.put("de", "Kräftige Schokolade, seidige Textur");
        menuItems.add(new FoodMenuItem(16L, names16, descs16, new BigDecimal("38.00"), "甜点", "/images/food/chocolate_cake.png"));

        Map<String, String> names17 = new HashMap<>();
        names17.put("zh", "抹茶冰淇淋");
        names17.put("en", "Matcha Ice Cream");
        names17.put("de", "Matcha-Eis");
        Map<String, String> descs17 = new HashMap<>();
        descs17.put("zh", "清新抹茶，香甜冰凉");
        descs17.put("en", "Refreshing matcha, sweet and cool");
        descs17.put("de", "Erfrischender Matcha, süß und kühl");
        menuItems.add(new FoodMenuItem(17L, names17, descs17, new BigDecimal("22.00"), "甜点", "/images/food/ice_cream.png"));

        Map<String, String> names18 = new HashMap<>();
        names18.put("zh", "芝士蛋糕");
        names18.put("en", "Cheesecake");
        names18.put("de", "Käsekuchen");
        Map<String, String> descs18 = new HashMap<>();
        descs18.put("zh", "绵密芝士，入口即化");
        descs18.put("en", "Creamy cheese, melts in your mouth");
        descs18.put("de", "Cremiger Käse, zergeht im Mund");
        menuItems.add(new FoodMenuItem(18L, names18, descs18, new BigDecimal("35.00"), "甜点", "/images/food/cheesecake.png"));

        Map<String, String> names19 = new HashMap<>();
        names19.put("zh", "水果拼盘");
        names19.put("en", "Fruit Platter");
        names19.put("de", "Obstplatte");
        Map<String, String> descs19 = new HashMap<>();
        descs19.put("zh", "时令水果，新鲜健康");
        descs19.put("en", "Seasonal fruits, fresh and healthy");
        descs19.put("de", "Saisonale Früchte, frisch und gesund");
        menuItems.add(new FoodMenuItem(19L, names19, descs19, new BigDecimal("45.00"), "甜点", "/images/food/fruit_platter.png"));
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
     * 设置现代化外观和颜色方案
     */
    private void setModernLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // 设置对话框按钮颜色
            UIManager.put("OptionPane.background", Color.WHITE);
            UIManager.put("OptionPane.messageForeground", new Color(44, 62, 80));
            UIManager.put("OptionPane.buttonFont", new Font("Microsoft YaHei", Font.PLAIN, 12));

            // 设置按钮颜色 - 这是关键！
            UIManager.put("Button.background", new Color(52, 152, 219));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(41, 128, 185));

            // 其他现有设置...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改顶部面板
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setBackground(Color.WHITE);

        // 显示当前桌号（从TableSession获取）
        JLabel tableLabel = new JLabel("当前桌号: " + TableSession.getCurrentTable() + "号桌");
        tableLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        tableLabel.setForeground(new Color(52, 152, 219));
        panel.add(tableLabel, BorderLayout.WEST);

        // 切换桌号按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton switchTableBtn = createModernButton("切换桌号", new Color(155, 89, 182));
        switchTableBtn.addActionListener(e -> switchTable());
        buttonPanel.add(switchTableBtn);

        panel.add(buttonPanel, BorderLayout.EAST);
        return panel;
    }

    private void switchTable() {
        int result = JOptionPane.showConfirmDialog(this,
                "确定要切换桌号吗？当前订单将会清空。",
                "切换桌号",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            this.dispose();
            new QRScanFrame().setVisible(true);
        }
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
        String[] languages = {"中文", "English", "Deutsch"};
        String[] codes = {"zh", "en", "de"};

        String choice = (String) JOptionPane.showInputDialog(this,
                "选择语言",
                "语言设置",
                JOptionPane.QUESTION_MESSAGE,
                null,
                languages,
                LanguageService.getCurrentLanguageDisplayName());

        if (choice != null) {
            int index = Arrays.asList(languages).indexOf(choice);
            LanguageService.setLanguage(codes[index]);
            updateUILanguage();
            JOptionPane.showMessageDialog(this,
                    "语言已切换到: " + choice,
                    "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 更新界面语言
// 更新界面语言
    private void updateUILanguage() {
        // 更新窗口标题
        setTitle(LanguageService.getString("app.title"));

        // 更新顶部面板
        updateTopPanelLanguage();

        // 更新标签页
        updateTabbedPaneLanguage();

        // 更新订单面板
        updateOrderPanelLanguage();

        // 更新菜单面板（包含菜品名称、描述、分类的多语言切换）
        refreshMenuPanel();

        // 更新当前订单显示
        updateOrderDisplay();
    }

    /**
     * 更新顶部面板语言
     */
    private void updateTopPanelLanguage() {
        if (topPanel.getComponentCount() > 0) {
            // 更新桌号标签
            Component[] components = topPanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    if (label.getText().contains("选择桌台") || label.getText().contains("Select Table") || label.getText().contains("Tisch wählen")) {
                        label.setText(LanguageService.getString("select.table") + ":");
                        break;
                    }
                }
            }
        }

        // 更新按钮文本
        newOrderBtn.setText(LanguageService.getString("new.order"));
        viewOrdersBtn.setText(LanguageService.getString("view.history"));
        qrBtn.setText("📱 " + LanguageService.getString("scan.order"));
        languageBtn.setText("🌐 " + LanguageService.getString(LanguageService.getCurrentLanguage()));
        searchField.setBorder(BorderFactory.createTitledBorder(LanguageService.getString("search.dish")));
    }

    /**
     * 更新标签页语言
     */
    private void updateTabbedPaneLanguage() {
        tabbedPane.setTitleAt(0, LanguageService.getString("menu.order"));
        tabbedPane.setTitleAt(1, LanguageService.getString("current.order"));
    }

    /**
     * 更新订单面板语言
     */
    private void updateOrderPanelLanguage() {
        // 更新订单面板按钮
        if (submitBtn != null) submitBtn.setText(LanguageService.getString("submit.pay"));
        if (clearBtn != null) clearBtn.setText(LanguageService.getString("clear.order"));
        if (printBtn != null) printBtn.setText(LanguageService.getString("print.order"));

        // 如果有评价按钮，也更新
        Component[] orderPanelComponents = orderPanel.getComponents();
        for (Component comp : orderPanelComponents) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                String buttonText = button.getText();
                if (buttonText.contains("评价菜品") || buttonText.contains("Rate Dishes") || buttonText.contains("Gerichte bewerten")) {
                    button.setText(LanguageService.getString("rate.dishes"));
                }
            }
        }

        // 更新订单状态面板标题
        if (statusContainerPanel != null) {
            Border border = BorderFactory.createTitledBorder(LanguageService.getString("order.status"));
            statusContainerPanel.setBorder(border);
        }
    }

    /**
     * 刷新菜单面板（重新创建以应用多语言）
     */
    private void refreshMenuPanel() {
        // 保存当前搜索关键词
        String currentSearch = currentSearchKeyword;

        // 重新创建菜单面板
        Component oldMenuPanel = tabbedPane.getComponentAt(0);
        menuPanel = createMenuPanel();
        tabbedPane.setComponentAt(0, menuPanel);

        // 如果之前有旧面板，移除它
        if (oldMenuPanel != menuPanel) {
            tabbedPane.remove(oldMenuPanel);
        }

        // 恢复搜索状态
        currentSearchKeyword = currentSearch;
        if (searchField != null) {
            searchField.setText(currentSearch);
        }

        // 刷新显示
        menuPanel.revalidate();
        menuPanel.repaint();

        System.out.println("菜单面板已刷新，当前语言: " + LanguageService.getCurrentLanguage());
    }

    /**
     * 更新订单显示内容
     */
    private void updateOrderDisplay() {
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            orderTextArea.setText(LanguageService.getString("no.order.items"));
            if (statusListModel != null) {
                statusListModel.clear();
            }
            return;
        }

        // 构建多语言订单详情
        StringBuilder sb = new StringBuilder();
        sb.append(LanguageService.getString("table") + ": ").append(currentOrder.getTableNumber())
                .append(LanguageService.getString("table.suffix")).append("\n\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append("             " + LanguageService.getString("order.details") + "\n");
        sb.append("═══════════════════════════════════════\n\n");

        for (OrderItem orderItem : currentOrder.getItems()) {
            sb.append(String.format(" %s × %d\n",
                    orderItem.getFoodMenuItem().getName(), // 这里会自动调用多语言的getName()
                    orderItem.getQuantity()));
            sb.append(String.format("     " + LanguageService.getString("subtotal") + ": ¥%.2f\n\n",
                    orderItem.getSubtotal()));
        }

        sb.append("═══════════════════════════════════════\n");
        sb.append(String.format(LanguageService.getString("total") + ": ¥%.2f\n", currentOrder.getTotalAmount()));
        sb.append("═══════════════════════════════════════\n");

        orderTextArea.setText(sb.toString());

        // 更新订单状态列表
        if (statusListModel != null) {
            statusListModel.clear();
            for (OrderItem item : currentOrder.getItems()) {
                statusListModel.addElement(new OrderStatusItem(item, false));
            }
        }
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
     * 创建现代化按钮（改进版）
     */
    private JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 渐变背景
                if (getModel().isPressed()) {
                    g2.setColor(backgroundColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(backgroundColor.brighter());
                } else {
                    g2.setColor(backgroundColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // 无边框，由背景处理
            }
        };

        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        // 设置字体
        button.setFont(new Font("微软雅黑", Font.BOLD, 12));

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

    /**
     * 创建现代化的菜单项面板
     */
    private JPanel createMenuItemPanel(FoodMenuItem item) {
        JPanel panel = new JPanel(new BorderLayout(15, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 添加鼠标悬停效果
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(250, 250, 250));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 152, 219)),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(Color.WHITE);
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(230, 230, 230)),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });

        // 左侧：菜品图片
        JLabel imageLabel = createImageLabel(item);

        // 中间：菜品信息
        JPanel infoPanel = createInfoPanel(item);

        // 右侧：数量控制和添加按钮
        JPanel controlPanel = createControlPanel(item);

        JPanel contentPanel = new JPanel(new BorderLayout(15, 0));
        contentPanel.add(imageLabel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        contentPanel.add(controlPanel, BorderLayout.EAST);
        contentPanel.setBackground(Color.WHITE);

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * 创建菜品图片标签（带加载失败处理）
     */
// 替换原来的createImageLabel方法
    private JLabel createImageLabel(FoodMenuItem item) {
        JLabel imageLabel = new JLabel();
        String imagePath = "/images/food/" + item.getImagePath().substring(item.getImagePath().lastIndexOf("/") + 1);
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            if (originalIcon != null && originalIcon.getImage() != null) {
                // 缩放图片到合适大小（保持比例）
                Image originalImage = originalIcon.getImage();
                Image scaledImage = scaleImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                // 加载默认占位图
                ImageIcon placeholder = new ImageIcon(getClass().getResource("/images/placeholder.png"));
                if (placeholder != null && placeholder.getImage() != null) {
                    Image scaledPlaceholder = scaleImage(placeholder.getImage(), IMAGE_WIDTH, IMAGE_HEIGHT);
                    imageLabel.setIcon(new ImageIcon(scaledPlaceholder));
                } else {
                    imageLabel.setText("?");
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                }
                imageLabel.setToolTipText(LanguageService.getString("image.load.failed"));
            }
        } catch (Exception e) {
            imageLabel.setText("?");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
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

        JLabel quantityLabel = new JLabel("0");
        quantityLabel.setPreferredSize(new Dimension(40, 30));
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        quantityLabel.setOpaque(true);
        quantityLabel.setBackground(Color.WHITE);

        // 使用表情符号按钮
        JButton minusBtn = createModernButton("➖", new Color(231, 76, 60)); // 红色
        minusBtn.setForeground(Color.WHITE);
        minusBtn.setPreferredSize(new Dimension(35, 30));

        JButton plusBtn = createModernButton("➕", new Color(46, 204, 113)); // 绿色
        plusBtn.setForeground(Color.WHITE);
        plusBtn.setPreferredSize(new Dimension(35, 30));

        JButton addBtn = createModernButton(LanguageService.getString("add.to.order"), new Color(52, 152, 219));

        setupButtonActions(item, minusBtn, plusBtn, addBtn, quantityLabel);

        // 使用多语言标签
        JLabel quantityTextLabel = new JLabel(LanguageService.getString("quantity") + ":");
        quantityTextLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));

        controlPanel.add(quantityTextLabel);
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