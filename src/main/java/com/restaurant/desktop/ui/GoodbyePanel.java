package com.restaurant.desktop.ui;

import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

/**
 * Goodbye-Panel, zeigt "Auf Wiedersehen" und kehrt nach 5 Sekunden automatisch zum Eingang zurück.
 */
public class GoodbyePanel extends JPanel {
    private Navigator navigator;

    /**
     * Konstruktor für das Goodbye-Panel.
     * @param navigator Navigator-Instanz
     */
    public GoodbyePanel(Navigator navigator) {
        this.navigator = navigator;
        initUI();
    }

    /**
     * Initialisiert die Benutzeroberfläche.
     */
    private void initUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel(LanguageManager.get("goodbye.message"), SwingConstants.CENTER);
        label.setFont(new Font("微软雅黑", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);

        // Nach 5 Sekunden automatisch zum Eingang zurückkehren
        Timer timer = new Timer(5000, e -> navigator.showEntrance());
        timer.setRepeats(false);
        timer.start();
    }
}