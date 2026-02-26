package com.restaurant.desktop.ui;

import com.restaurant.desktop.entity.Order;
import com.restaurant.desktop.entity.OrderItem;
import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {
    private Navigator navigator;
    private Order order;
    private ButtonGroup group;
    private JRadioButton mobileBtn, cashBtn, cardBtn;
    private JTextArea orderArea;

    public PaymentPanel(Navigator navigator, Order order) {
        this.navigator = navigator;
        this.order = order;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(LanguageManager.get("order.payment"), SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        orderArea = new JTextArea(10, 30);
        orderArea.setEditable(false);
        orderArea.setFont(new Font("等线", Font.PLAIN, 14));
        updateOrderDisplay();
        JScrollPane orderScroll = new JScrollPane(orderArea);
        orderScroll.setBorder(BorderFactory.createTitledBorder(LanguageManager.get("order.details")));
        centerPanel.add(orderScroll, BorderLayout.CENTER);

        JPanel methodPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        methodPanel.setBorder(BorderFactory.createTitledBorder(LanguageManager.get("select.payment.method")));

        mobileBtn = new JRadioButton(LanguageManager.get("mobile.payment"));
        cashBtn = new JRadioButton(LanguageManager.get("cash.payment"));
        cardBtn = new JRadioButton(LanguageManager.get("card.payment"));

        group = new ButtonGroup();
        group.add(mobileBtn);
        group.add(cashBtn);
        group.add(cardBtn);

        methodPanel.add(mobileBtn);
        methodPanel.add(cashBtn);
        methodPanel.add(cardBtn);

        centerPanel.add(methodPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton backBtn = new JButton(LanguageManager.get("back.to.menu"));
        backBtn.addActionListener(e -> navigator.showMenu(order));
        bottomPanel.add(backBtn);

        JButton payBtn = new JButton(LanguageManager.get("pay.now"));
        payBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        payBtn.addActionListener(e -> {
            String method = null;
            if (mobileBtn.isSelected()) method = "mobile";
            else if (cashBtn.isSelected()) method = "cash";
            else if (cardBtn.isSelected()) method = "card";
            else {
                String dialogTitle = LanguageManager.get("prompt");
                String message = LanguageManager.get("select.payment");
                String[] options = { LanguageManager.get("confirm") };
                JOptionPane.showOptionDialog(this, message, dialogTitle,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                return;
            }
            order.setPaymentMethod(method);
            order.setPaid(true);
            navigator.showProcessing();
        });
        bottomPanel.add(payBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateOrderDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(LanguageManager.get("order.details")).append("\n");
        sb.append("====================\n");
        for (OrderItem oi : order.getItems()) {
            sb.append(oi.getFoodItem().getName(LanguageManager.getCurrentLang()))
                    .append(" × ").append(oi.getQuantity())
                    // 修改点：使用 formatPrice 显示小计
                    .append(" = ").append(LanguageManager.formatPrice(oi.getSubtotal())).append("\n");
        }
        sb.append("====================\n");
        // 修改点：使用 formatPrice 显示总计
        sb.append(LanguageManager.get("total")).append(": ").append(LanguageManager.formatPrice(order.getTotalAmount()));
        orderArea.setText(sb.toString());
    }
}