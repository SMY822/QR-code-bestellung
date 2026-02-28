package com.restaurant.desktop.exception;

/**
 * Wird ausgelöst, wenn versucht wird, ohne vorherigen QR-Code-Scan fortzufahren.
 */
public class NoQRScannedException extends Exception {
    public NoQRScannedException(String message) {
        super(message);
    }
}