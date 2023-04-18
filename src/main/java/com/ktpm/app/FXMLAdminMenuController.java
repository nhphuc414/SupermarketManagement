/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLAdminMenuController implements Initializable {

    public void handleSignOut(ActionEvent event) {
        Alert alert = Utils.getBox("Xác nhận đăng xuất", null, "Bạn có chắc chắn rằng muốn đăng xuất?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.setCurrentEmployee(null);
                App.setRoot("FXMLLogin", "Login");
            } catch (IOException io) {
                Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    public void handleBranchManager() {
        try {
            App.setRoot("FXMLBranchManager", "Branch Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void handleEmployeeManager() {
        try {
            App.setRoot("FXMLEmployeeManager", "Employee Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }
    
    public void handleProductManager() {
        try {
            App.setRoot("FXMLProductManager", "Product Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }
    
    public void handleBranchProductManager() {
        try {
            App.setRoot("FXMLBranchProductManager", "Stock Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }
     public void handleDiscountManager() {
        try {
            App.setRoot("FXMLDiscountManager", "Discount Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
