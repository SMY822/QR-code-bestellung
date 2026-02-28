package com.restaurant.desktop.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Utility-Klasse zur Generierung von QR-Code-Bildern.
 */
public class QRCodeGenerator {

    /**
     * Generiert ein QR-Code-Bild mit dem angegebenen Inhalt.
     * @param content QR-Code-Inhalt
     * @param width Breite
     * @param height Höhe
     * @return generiertes QR-Code-Bild
     * @throws Exception wirft Exception bei Fehlschlag
     */
    public static BufferedImage generateQRCode(String content, int width, int height) throws Exception {
        HashMap<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}