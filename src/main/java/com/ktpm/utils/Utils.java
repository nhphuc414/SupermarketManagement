/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author ad
 */
public class Utils {

    public static String normalizeName(String inputName) {
        String[] nameParts = inputName.split(" ");
        StringBuilder normalizedBuilder = new StringBuilder();

        for (String namePart : nameParts) {
            if (namePart.trim().length() > 0) { // Check if the name part is not empty
                String normalizedPart = namePart.substring(0, 1).toUpperCase() + namePart.substring(1).toLowerCase();
                normalizedBuilder.append(normalizedPart).append(" ");
            }
        }

        return normalizedBuilder.toString().trim();
    }
    private static final String SALT = "MySalt"; // Salt để tăng tính bảo mật
    
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(SALT.getBytes());
        md.update(password.getBytes());
        byte[] hash = md.digest();
        return Arrays.toString(hash);
    }
    
    public static boolean verifyPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        String hashed = hashPassword(password);
        return hashed.equals(hashedPassword);
    }
    public static Alert getBox(String title,String header, String content, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        return a;
    }
    public static void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            System.exit(0);
        }
    }
}
