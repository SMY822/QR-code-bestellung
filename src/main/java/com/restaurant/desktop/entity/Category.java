package com.restaurant.desktop.entity;

/**
 * Enum für Gerichtskategorien mit mehrsprachigen Anzeigenamen.
 */
public enum Category {
    APPETIZER("前菜", "Appetizer", "Vorspeise"),
    MAIN("主食", "Main Dish", "Hauptgericht"),
    DESSERT("甜点", "Dessert", "Nachspeise"),
    DRINK("饮料", "Drink", "Getränk");

    private final String zh;
    private final String en;
    private final String de;

    Category(String zh, String en, String de) {
        this.zh = zh;
        this.en = en;
        this.de = de;
    }

    /**
     * Gibt den Anzeigenamen der Kategorie basierend auf der Sprache zurück.
     * @param lang Sprachcode (z.B. "zh", "en", "de")
     * @return Kategoriename in der entsprechenden Sprache
     */
    public String getDisplayName(String lang) {
        switch (lang) {
            case "en": return en;
            case "de": return de;
            default: return zh;
        }
    }
}