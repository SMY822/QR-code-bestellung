package com.restaurant.desktop.ui;

import com.restaurant.desktop.exception.NoQRScannedException;
import com.restaurant.desktop.service.LanguageManager;
import com.restaurant.desktop.service.QRCodeGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EntrancePanel extends JPanel {
    private Navigator navigator;
    private JLabel qrLabel;
    private JButton refreshBtn;
    private JButton nextBtn;
    private JButton langBtn;
    private JLabel promptLabel;
    private int refreshCount = 0;
    private final int MAX_REFRESH = 5;

    public EntrancePanel(Navigator navigator) {
        this.navigator = navigator;
        initUI();
        generateQRCode();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        promptLabel = new JLabel(LanguageManager.get("scan.prompt"), SwingConstants.CENTER);
        promptLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(promptLabel, BorderLayout.NORTH);

        qrLabel = new JLabel();
        qrLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(qrLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        refreshBtn = new JButton(LanguageManager.get("refresh.qr"));
        refreshBtn.addActionListener(e -> refreshQRCode());
        bottomPanel.add(refreshBtn);

        nextBtn = new JButton(LanguageManager.get("next.step"));
        nextBtn.addActionListener(e -> {
            String tableNumber = String.valueOf(refreshCount + 1);
            navigator.showMenu(tableNumber);
        });
        bottomPanel.add(nextBtn);

        langBtn = new JButton(LanguageManager.get("language"));
        langBtn.addActionListener(e -> switchLanguage());
        bottomPanel.add(langBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void switchLanguage() {
        String[] options = {"中文", "English", "Deutsch"};
        int choice = JOptionPane.showOptionDialog(this,
                LanguageManager.get("select.language"),
                "Language",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) LanguageManager.setLanguage("zh");
        else if (choice == 1) LanguageManager.setLanguage("en");
        else if (choice == 2) LanguageManager.setLanguage("de");
        else return;

        // 更新当前面板文字
        promptLabel.setText(LanguageManager.get("scan.prompt"));
        refreshBtn.setText(LanguageManager.get("refresh.qr"));
        nextBtn.setText(LanguageManager.get("next.step"));
        langBtn.setText(LanguageManager.get("language"));

        // 更新窗口标题
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.setTitle(LanguageManager.get("app.title"));
        }
    }
    private void generateQRCode() {
        try {
            String content = "http://restaurant.com/order?table=" + (refreshCount + 1);
            BufferedImage qrImage = QRCodeGenerator.generateQRCode(content, 200, 200);
            qrLabel.setIcon(new ImageIcon(qrImage));
            qrLabel.setText("");
        } catch (Exception e) {
            qrLabel.setText(LanguageManager.get("qr.error"));
        }
    }

    private void refreshQRCode() {
        if (refreshCount < MAX_REFRESH) {
            refreshCount++;
            generateQRCode();
        } else {
            JOptionPane.showMessageDialog(this, LanguageManager.get("no.more.refresh"));
        }
    }
}