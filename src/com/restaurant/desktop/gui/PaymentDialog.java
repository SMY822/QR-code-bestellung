package com.restaurant.desktop.gui;

import com.restaurant.desktop.entity.Order;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class PaymentDialog extends JDialog {
    private Order order;
    private boolean paymentSuccess = false;

    public PaymentDialog(Frame parent, Order order) {
        super(parent, "支付订单", true);
        this.order = order;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 订单摘要
        JPanel summaryPanel = createSummaryPanel();

        // 支付方式选择
        JPanel paymentMethodPanel = createPaymentMethodPanel();

        // 按钮面板
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(paymentMethodPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("订单摘要"));

        StringBuilder summary = new StringBuilder();
        summary.append("桌号: ").append(order.getTableNumber()).append("号桌\n");
        summary.append("订单金额: ¥").append(order.getTotalAmount()).append("\n\n");
        summary.append("包含菜品:\n");

        for (var item : order.getItems()) {
            summary.append("• ")
                    .append(item.getFoodMenuItem().getName())
                    .append(" × ")
                    .append(item.getQuantity())
                    .append("\n");
        }

        JTextArea textArea = new JTextArea(summary.toString());
        textArea.setEditable(false);
        textArea.setBackground(getBackground());

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPaymentMethodPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("选择支付方式"));

        JRadioButton cashBtn = new JRadioButton("💵 现金支付");
        JRadioButton wechatBtn = new JRadioButton("💚 微信支付");
        JRadioButton alipayBtn = new JRadioButton("💙 支付宝");
        JRadioButton cardBtn = new JRadioButton("💳 银行卡");

        ButtonGroup group = new ButtonGroup();
        group.add(cashBtn);
        group.add(wechatBtn);
        group.add(alipayBtn);
        group.add(cardBtn);

        cashBtn.setSelected(true);

        panel.add(cashBtn);
        panel.add(wechatBtn);
        panel.add(alipayBtn);
        panel.add(cardBtn);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton confirmBtn = new JButton("✅ 确认支付");
        JButton cancelBtn = new JButton("❌ 取消");

        confirmBtn.addActionListener(e -> {
            paymentSuccess = true;
            JOptionPane.showMessageDialog(this,
                    "支付成功！感谢您的光临！\n订单金额: ¥" + order.getTotalAmount(),
                    "支付成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            paymentSuccess = false;
            dispose();
        });

        panel.add(confirmBtn);
        panel.add(cancelBtn);

        return panel;
    }

    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }
}