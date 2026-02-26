package com.restaurant.desktop.ui;

import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

public class ProcessingPanel extends JPanel {
    private Navigator navigator;

    public ProcessingPanel(Navigator navigator) {
        this.navigator = navigator;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel(LanguageManager.get("processing.message"), SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, 18));
        add(label, BorderLayout.CENTER);

        JButton okBtn = new JButton(LanguageManager.get("confirm"));
        okBtn.addActionListener(e -> navigator.showMealReady());
        add(okBtn, BorderLayout.SOUTH);
    }
}