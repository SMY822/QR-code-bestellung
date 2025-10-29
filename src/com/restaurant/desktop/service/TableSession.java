package com.restaurant.desktop.service;

public class TableSession {
    private static String currentTableNumber;

    public static void setCurrentTable(String tableNumber) {
        currentTableNumber = tableNumber;
        System.out.println("当前桌号设置为: " + tableNumber);
    }

    public static String getCurrentTable() {
        return currentTableNumber != null ? currentTableNumber : "未选择";
    }

    public static boolean hasActiveTable() {
        return currentTableNumber != null && !currentTableNumber.isEmpty();
    }
}