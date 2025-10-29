package com.restaurant.desktop;

import com.restaurant.desktop.gui.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 在EDT线程中启动应用程序，确保Swing组件线程安全
        SwingUtilities.invokeLater(() -> {
            try {
                // 创建并显示主窗口
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            } catch (Exception e) {
                System.err.println("应用程序启动失败: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}