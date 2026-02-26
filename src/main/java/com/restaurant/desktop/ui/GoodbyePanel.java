package com.restaurant.desktop.ui;

import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

public class GoodbyePanel extends JPanel {
    private Navigator navigator;

    public GoodbyePanel(Navigator navigator) {
        this.navigator = navigator;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel(LanguageManager.get("goodbye.message"), SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);

        // 5秒后自动回到入口
        Timer timer = new Timer(5000, e -> navigator.showEntrance());
        timer.setRepeats(false);
        timer.start();
    }
}