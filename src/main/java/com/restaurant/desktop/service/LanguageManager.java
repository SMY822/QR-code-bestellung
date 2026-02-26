package com.restaurant.desktop.service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LanguageManager {
    private static ResourceBundle bundle;
    private static String currentLang = "zh";

    static {
        setLanguage("de");
    }

    public static void setLanguage(String lang) {
        currentLang = lang;
        try {
            Locale locale = new Locale(lang);
            bundle = ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            bundle = ResourceBundle.getBundle("messages", new Locale("zh"));
        }
    }

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

    public static String formatPrice(java.math.BigDecimal price) {
        switch (currentLang) {
            case "en": return "$" + price.toString();
            case "de": return "€" + price.toString();
            default:   return "¥" + price.toString();
        }
    }
}