package com.restaurant.desktop;

import com.restaurant.desktop.ui.Navigator;
import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame(LanguageManager.get("app.title"));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            // 设置窗口图标（如果图片存在）
            try {
                ImageIcon icon = new ImageIcon(MainApp.class.getResource("/images/icon.png"));
                frame.setIconImage(icon.getImage());
            } catch (Exception e) {
                System.err.println("图标加载失败: " + e.getMessage());
            }

            Navigator navigator = new Navigator(frame);
            navigator.showEntrance();

            frame.setVisible(true);
        });
    }
}