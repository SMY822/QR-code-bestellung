package com.restaurant.desktop;

import com.restaurant.desktop.ui.Navigator;
import com.restaurant.desktop.service.LanguageManager;
import javax.swing.*;
import java.awt.*;

/**
 * Haupt-Einstiegsklasse der Anwendung.
 */
public class MainApp {

    /**
     * Einstiegsmethode des Programms, startet die Swing-Anwendung.
     * @param args Kommandozeilenargumente
     */
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

            // Setzt das Fenster-Icon (falls vorhanden)
            try {
                ImageIcon icon = new ImageIcon(MainApp.class.getResource("/images/icon.png"));
                frame.setIconImage(icon.getImage());
            } catch (Exception e) {
                System.err.println("Fehler beim Laden des Icons: " + e.getMessage());
            }

            Navigator navigator = new Navigator(frame);
            navigator.showEntrance();

            frame.setVisible(true);
        });
    }
}