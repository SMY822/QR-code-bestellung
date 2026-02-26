package com.restaurant.desktop.ui;

import com.restaurant.desktop.entity.Order;
import javax.swing.*;

public class Navigator {
    private JFrame frame;
    private Order currentOrder; // 当前订单，用于跨界面传递

    public Navigator(JFrame frame) {
        this.frame = frame;
    }

    public void showEntrance() {
        frame.setContentPane(new EntrancePanel(this));
        frame.revalidate();
    }

    // 新建订单进入菜单
    public void showMenu(String tableNumber) {
        currentOrder = new Order(tableNumber);
        frame.setContentPane(new MenuPanel(this, currentOrder));
        frame.revalidate();
    }

    // 恢复已有订单进入菜单（用于从支付界面返回）
    public void showMenu(Order order) {
        this.currentOrder = order;
        frame.setContentPane(new MenuPanel(this, order));
        frame.revalidate();
    }

    public void showPayment() {
        frame.setContentPane(new PaymentPanel(this, currentOrder));
        frame.revalidate();
    }

    public void showProcessing() {
        frame.setContentPane(new ProcessingPanel(this));
        frame.revalidate();
    }

    public void showMealReady() {
        frame.setContentPane(new MealReadyPanel(this));
        frame.revalidate();
    }

    public void showRating() {
        frame.setContentPane(new RatingPanel(this));
        frame.revalidate();
    }

    public void showGoodbye() {
        frame.setContentPane(new GoodbyePanel(this));
        frame.revalidate();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }
}