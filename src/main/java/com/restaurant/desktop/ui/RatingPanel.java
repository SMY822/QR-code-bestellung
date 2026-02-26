package com.restaurant.desktop.ui;

import com.restaurant.desktop.service.LanguageManager;
import com.restaurant.desktop.service.RatingManager;
import javax.swing.*;
import java.awt.*;

public class RatingPanel extends JPanel {
    private Navigator navigator;
    private JSlider foodSlider, serviceSlider;
    private JTextArea commentArea;

    public RatingPanel(Navigator navigator) {
        this.navigator = navigator;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(LanguageManager.get("please.rate"), SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // 菜品评分
        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(new JLabel(LanguageManager.get("rate.food")), BorderLayout.NORTH);
        foodSlider = new JSlider(1, 5, 5);
        foodSlider.setMajorTickSpacing(1);
        foodSlider.setPaintTicks(true);
        foodSlider.setPaintLabels(true);
        foodPanel.add(foodSlider, BorderLayout.CENTER);

        // 服务评分
        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.add(new JLabel(LanguageManager.get("rate.service")), BorderLayout.NORTH);
        serviceSlider = new JSlider(1, 5, 5);
        serviceSlider.setMajorTickSpacing(1);
        serviceSlider.setPaintTicks(true);
        serviceSlider.setPaintLabels(true);
        servicePanel.add(serviceSlider, BorderLayout.CENTER);

        // 评论
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.add(new JLabel(LanguageManager.get("comment")), BorderLayout.NORTH);
        commentArea = new JTextArea(4, 30);
        commentPanel.add(new JScrollPane(commentArea), BorderLayout.CENTER);

        centerPanel.add(foodPanel);
        centerPanel.add(servicePanel);
        centerPanel.add(commentPanel);
        add(centerPanel, BorderLayout.CENTER);

        JButton submitBtn = new JButton(LanguageManager.get("submit"));
        submitBtn.addActionListener(e -> {
            int foodRating = foodSlider.getValue();
            int serviceRating = serviceSlider.getValue();
            String comment = commentArea.getText();
            RatingManager.saveRating(foodRating, serviceRating, comment);
            navigator.showGoodbye();
        });
        add(submitBtn, BorderLayout.SOUTH);
    }
}