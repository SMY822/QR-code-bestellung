package com.restaurant.desktop.entity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Stellt ein Gericht dar, mit mehrsprachigem Namen, Beschreibung, Preis, Kategorie und Bildpfad.
 */
public class FoodItem {
    private Long id;
    private Map<String, String> names;       // mehrsprachige Namen
    private Map<String, String> descriptions;
    private BigDecimal price;
    private Category category;
    private String imagePath;                 // Bildpfad

    /**
     * Konstruktor für ein Gericht.
     * @param id Gericht-ID
     * @param names mehrsprachige Namenszuordnung
     * @param descriptions mehrsprachige Beschreibungszuordnung
     * @param price Preis
     * @param category Kategorie
     * @param imagePath Bildpfad (kann null sein)
     */
    public FoodItem(Long id, Map<String, String> names, Map<String, String> descriptions,
                    BigDecimal price, Category category, String imagePath) {
        this.id = id;
        this.names = names;
        this.descriptions = descriptions;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public Long getId() { return id; }
    public Map<String, String> getNames() { return names; }
    public Map<String, String> getDescriptions() { return descriptions; }
    public BigDecimal getPrice() { return price; }
    public Category getCategory() { return category; }
    public String getImagePath() { return imagePath; }

    /**
     * Gibt den Namen des Gerichts basierend auf der aktuellen Sprache zurück.
     * @param lang Sprachcode ("zh", "en", "de")
     * @return Name in der entsprechenden Sprache, falls nicht vorhanden Chinesisch
     */
    public String getName(String lang) {
        return names.getOrDefault(lang, names.get("zh"));
    }

    public String getDescription(String lang) {
        return descriptions.getOrDefault(lang, descriptions.get("zh"));
    }
}