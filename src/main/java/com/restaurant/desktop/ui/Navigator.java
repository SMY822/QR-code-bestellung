package com.restaurant.desktop.ui;

import com.restaurant.desktop.entity.Order;
import javax.swing.*;

/**
 * Navigator-Klasse, zuständig für das Wechseln zwischen verschiedenen Bildschirmen der Anwendung.
 */
public class Navigator {
    private JFrame frame;
    private Order currentOrder; // aktuelle Bestellung, wird zwischen Bildschirmen übergeben

    public Navigator(JFrame frame) {
        this.frame = frame;
    }

    public void showEntrance() {
        frame.setContentPane(new EntrancePanel(this));
        frame.revalidate();
    }

    /**
     * Wechselt zum Menü-Bildschirm (neue Bestellung).
     * @param tableNumber Tischnummer
     */
    public void showMenu(String tableNumber) {
        currentOrder = new Order(tableNumber);
        frame.setContentPane(new MenuPanel(this, currentOrder));
        frame.revalidate();
    }

    /**
     * Wechselt zum Menü-Bildschirm (mit vorhandener Bestellung, z.B. nach Rückkehr vom Bezahlen).
     * @param order vorhandene Bestellung
     */
    public void showMenu(Order order) {
        this.currentOrder = order;
        frame.setContentPane(new MenuPanel(this, order));
        frame.revalidate();
    }

    /** Wechselt zum Zahlungsbildschirm. */
    public void showPayment() {
        frame.setContentPane(new PaymentPanel(this, currentOrder));
        frame.revalidate();
    }

    /** Wechselt zum Bildschirm "In Zubereitung". */
    public void showProcessing() {
        frame.setContentPane(new ProcessingPanel(this));
        frame.revalidate();
    }

    /** Wechselt zum Bildschirm "Mahlzeit bereit". */
    public void showMealReady() {
        frame.setContentPane(new MealReadyPanel(this));
        frame.revalidate();
    }

    /** Wechselt zum Bewertungsbildschirm. */
    public void showRating() {
        frame.setContentPane(new RatingPanel(this));
        frame.revalidate();
    }

    /** Wechselt zum Abschiedsbildschirm. */
    public void showGoodbye() {
        frame.setContentPane(new GoodbyePanel(this));
        frame.revalidate();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }
}