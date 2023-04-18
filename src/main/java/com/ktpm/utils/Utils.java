/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.utils;

import com.ktpm.pojo.Product;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
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

    public static Alert getBox(String title, String header, String content, Alert.AlertType type) {
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

    public static boolean checkEmpty(Object object) {
        if (object == null) {
            Utils.getBox("Lỗi", "", "Không được để trống thông tin", Alert.AlertType.ERROR).showAndWait();
            return false;
        } else if (object.equals("")) {
            Utils.getBox("Lỗi", "", "Không được để trống thông tin", Alert.AlertType.ERROR).showAndWait();
            return false;
        } else if (String.valueOf(object).length()>50){
            Utils.getBox("Lỗi", "", "Trường nhập vào quá lớn", Alert.AlertType.ERROR).showAndWait();
            return false;
        }
        return true;

    }

    public static boolean checkName(String name) {
        if (checkEmpty(name)) {
            if (checkTextField(name, "Tên", 30, 4) && !name.matches("[\\p{L}\\s]+")) {
                Utils.getBox("Lỗi", "Tên không được chứa số hoặc ký tự đặc biệt", "", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checkTextField(String field, String error, int max, int min) {
        if (checkEmpty(field)) {
            if (field.length() > max) {
                Utils.getBox("Lỗi", "Lỗi nhập liệu", "Vui lòng nhập " + error + " ít hơn " + max + " ký tự", Alert.AlertType.ERROR).showAndWait();
                return false;
            } else if (field.length() < min) {
                Utils.getBox("Lỗi", "Lỗi nhập liệu", error + " quá ngắn", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checkNumber(String number) {
        if (checkEmpty(number)) {
            if (!number.matches("^0\\d{9}$")) {
                Utils.getBox("Lỗi", "Lỗi nhập liệu", "SDT phải bắt đầu từ 0 và phải có 10 số", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checkBirthday(LocalDate birthday) {
        if (checkEmpty(birthday)) {
            if (!Date.valueOf(birthday).before(Date.valueOf(LocalDate.now()))) {
                Utils.getBox("Lỗi", "Ngày sinh không đúng", "Vui lòng chọn ngày sinh trước ngày hiện tại", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checkProductQuantity(Product product, String textQuantity) {
        if (checkEmpty(product) && checkEmpty(textQuantity)) {
            if (product.getProductType() == Product.ProductType.Quantity && !textQuantity.matches("\\d*")) {
                Utils.getBox("Lỗi", "Sai số lượng", "Vui lòng nhập số lượng chính xác", Alert.AlertType.ERROR).showAndWait();
                return false;
            } else try {
                Double quantity = Double.valueOf(textQuantity);
                if (textQuantity.length() > 20) {
                    Utils.getBox("Lỗi", "Quá lớn", "Số lượng quá lớn", Alert.AlertType.ERROR).showAndWait();
                    return false;
                }
                if ((product.getProductType() == Product.ProductType.Quantity && quantity < 1)
                        || (product.getProductType() == Product.ProductType.Weight && quantity <= 0)) {
                    Utils.getBox("Lỗi", "", "Vui lòng nhập số lượng chính xác", Alert.AlertType.ERROR).showAndWait();
                    return false;
                }
            } catch (NumberFormatException e) {
                Utils.getBox("Lỗi", "Sai số lượng", "Vui lòng nhập đúng số lượng", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }
}
