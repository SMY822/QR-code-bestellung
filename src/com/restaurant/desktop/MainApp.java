package com.restaurant.desktop;

import com.restaurant.desktop.gui.MainFrame;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // 简化版本：不使用系统外观
        new MainFrame().setVisible(true);
        try {
            // 使用跨平台外观
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建并显示GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}