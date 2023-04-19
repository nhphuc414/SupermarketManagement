/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLAdminMenuController implements Initializable {
    
    @FXML
    private Label labelName;
    
    @FXML
    private Button btnBranch;
    
    @FXML
    private Button btnDiscount;
    
    @FXML
    private Button btnEmployee;
    
    @FXML
    private Button btnProduct;
    
    @FXML
    private Button btnSignOut;
    
    @FXML
    private Button btnStock;
    
    public void handleSignOut() {
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addEnterEvent();
        labelName.setText(App.getCurrentEmployee().getEmployeeName());
    }

    private void addEnterEvent() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(btnBranch);
        nodes.add(btnDiscount);
        nodes.add(btnEmployee);
        nodes.add(btnProduct);
        nodes.add(btnStock);
        nodes.add(btnSignOut);
        EventHandler<KeyEvent> eventHandler = event -> {
            if (null != event.getCode()) {
                switch (event.getCode()) {
                    case DIGIT1:
                    case NUMPAD1:
                        event.consume();
                        btnBranch.fire();
                        break;
                    case DIGIT2:
                    case NUMPAD2:    
                        event.consume();
                        btnEmployee.fire();
                        break;
                    case DIGIT3:
                    case NUMPAD3:
                        event.consume();
                        btnStock.fire();
                        break;
                    case DIGIT4:
                        case NUMPAD4:
                        event.consume();
                        btnProduct.fire();
                        break;
                    case DIGIT5:
                    case NUMPAD5:
                        event.consume();
                        btnDiscount.fire();
                        break;
                    case ESCAPE:
                        event.consume();
                        btnSignOut.fire();
                        break;
                    default:
                        break;
                }
            }
        };
        for (Node node:nodes){
            node.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
        }
    }
}
