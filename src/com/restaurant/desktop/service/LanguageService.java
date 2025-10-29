package com.restaurant.desktop.service;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

public class LanguageService {
    private static ResourceBundle bundle;
    private static String currentLanguage = "zh";

    static {
        setLanguage("zh"); // 默认中文
    }

    public static void setLanguage(String lang) {
        currentLanguage = lang;
        Locale locale = new Locale(lang);
        try {
            bundle = ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            System.err.println("找不到语言资源文件: messages_" + lang + ".properties");
            // 尝试加载默认的（中文）
            try {
                bundle = ResourceBundle.getBundle("messages", new Locale("zh"));
            } catch (MissingResourceException e2) {
                System.err.println("也找不到默认语言资源文件");
                // 创建空的资源包
                bundle = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        return "[" + key + "]";
                    }
                    @Override
                    public java.util.Enumeration<String> getKeys() {
                        return java.util.Collections.emptyEnumeration();
                    }
                };
            }
        }
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("找不到语言键: " + key);
            return "[" + key + "]";
        }
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }

    // 获取当前语言的显示名称
    public static String getCurrentLanguageDisplayName() {
        switch (currentLanguage) {
            case "zh": return "中文";
            case "en": return "English";
            case "de": return "Deutsch";
            default: return "中文";
        }
    }
}