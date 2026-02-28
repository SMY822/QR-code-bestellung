package com.restaurant.desktop.ui;

import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

/**
 * Panel, das anzeigt, dass alle Gerichte serviert sind.
 */
public class MealReadyPanel extends JPanel {
    private Navigator navigator;

    /**
     * Konstruktor für das MealReady-Panel.
     * @param navigator Navigator-Instanz
     */
    public MealReadyPanel(Navigator navigator) {
        this.navigator = navigator;
        initUI();
    }

    /**
     * Initialisiert die Benutzeroberfläche.
     */
    private void initUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel(LanguageManager.get("meal.ready.message"), SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, 18));
        add(label, BorderLayout.CENTER);

        JButton okBtn = new JButton(LanguageManager.get("confirm"));
        okBtn.addActionListener(e -> navigator.showRating());
        add(okBtn, BorderLayout.SOUTH);
    }
}