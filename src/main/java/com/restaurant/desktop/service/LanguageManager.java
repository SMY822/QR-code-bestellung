package com.restaurant.desktop.service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Verwaltet die Sprachressourcendateien und bietet Internationalisierung.
 */
public class LanguageManager {
    private static ResourceBundle bundle;
    private static String currentLang = "zh";

    static {
        setLanguage("de");
    }

    /**
     * Setzt die aktuelle Sprache und lädt das ResourceBundle neu.
     * @param lang Sprachcode ("zh", "en", "de")
     */
    public static void setLanguage(String lang) {
        currentLang = lang;
        try {
            Locale locale = new Locale(lang);
            bundle = ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            bundle = ResourceBundle.getBundle("messages", new Locale("zh"));
        }
    }

    /**
     * Holt den Text für einen Schlüssel in der aktuellen Sprache.
     * @param key Ressourcenschlüssel
     * @return übersetzter Text
     */
    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "[" + key + "]";
        }
    }

    public static String getCurrentLang() {
        return currentLang;
    }

    /**
     * Formatiert den Preis mit dem entsprechenden Währungssymbol der aktuellen Sprache.
     * @param price Preis
     * @return Preisstring mit Währungssymbol
     */
    public static String formatPrice(java.math.BigDecimal price) {
        switch (currentLang) {
            case "en": return "$" + price.toString();
            case "de": return "€" + price.toString();
            default:   return "¥" + price.toString();
        }
    }
}