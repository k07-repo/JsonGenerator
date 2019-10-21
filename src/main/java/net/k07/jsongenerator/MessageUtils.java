package net.k07.jsongenerator;

import javax.swing.*;

public class MessageUtils {
    public static void showSuccessMessage(String message) {
        showMessage(message, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String message) {
        showMessage(message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
}
