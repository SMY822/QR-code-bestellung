package com.restaurant.desktop.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet Benutzerbewertungen, speichert in Datei und lädt historische Bewertungen.
 */
public class RatingManager {
    private static final String FILE_NAME = "ratings.txt";

    /**
     * Speichert eine Bewertung in der Datei.
     * @param foodRating Bewertung für Gerichte (1-5)
     * @param serviceRating Bewertung für Service (1-5)
     * @param comment Kommentar
     */
    public static void saveRating(int foodRating, int serviceRating, String comment) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(foodRating + "," + serviceRating + "," + comment + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt alle gespeicherten Bewertungen aus der Datei.
     * @return Liste der Bewertungen als Zeichenketten
     */
    public static List<String> loadAllRatings() {
        List<String> ratings = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    ratings.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ratings;
    }
}