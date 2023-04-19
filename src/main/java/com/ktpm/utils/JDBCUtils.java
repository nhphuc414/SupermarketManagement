/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author ad
 */
public class JDBCUtils {

    static {
        try {
            // B1 Nap driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Utils.getBox("Lỗi", "", "Không thể nạp driver", Alert.AlertType.ERROR).showAndWait();
        }
    }

    public static Connection getConn() {
        try {
            // B2 Thiet lap ket noi
            return DriverManager.getConnection("jdbc:mysql://localhost/supermarket", "root", "Admin@123");
        } catch (SQLException ex) {
            Utils.getBox("Lỗi", "", "Connection String không chính xác hoặc lỗi kết nối tới Database", Alert.AlertType.ERROR).showAndWait();
        }
        return null;
    }

    public static Connection getTestConn() throws SQLException {
        // B2 Thiet lap ket noi
        return DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "Admin@123");

    }

}
