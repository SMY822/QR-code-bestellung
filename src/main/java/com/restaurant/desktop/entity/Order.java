package com.restaurant.desktop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert eine Bestellung mit Tischnummer und Liste der Bestellpositionen.
 */
public class Order {
    private String tableNumber;
    private List<OrderItem> items;
    private String paymentMethod; // Zahlungsmethode
    private boolean paid;

    public Order(String tableNumber) {
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.paid = false;
    }

    public String getTableNumber() { return tableNumber; }
    public List<OrderItem> getItems() { return items; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isPaid() { return paid; }

    /**
     * Fügt eine Bestellposition zur Bestellung hinzu.
     * @param item die hinzuzufügende Bestellposition
     */
    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Berechnet den Gesamtbetrag der Bestellung (unter Verwendung der Stream API).
     * @return Gesamtbetrag
     */
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        items.clear();
    }
}