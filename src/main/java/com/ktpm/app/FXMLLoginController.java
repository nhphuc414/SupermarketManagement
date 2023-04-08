/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.ktpm.services.EmployeeService;
import com.ktpm.services.impl.EmployeeServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private StackPane stackPaneId;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    /**
     * @param handler
     *
     */
    public void loginAccount(ActionEvent handler) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        EmployeeService employeeService = new EmployeeServiceImpl();
        try {
            Employee employee = employeeService.getEmployeeByUsernameAndPassword(username, password);
            if (employee != null) {
                App.setCurrentEmployee(employee);
                if (employee.getEmployeeRole() == Employee.EmployeeRole.Manager) {
                    App.setRoot("FXMLAdminMenu","Admin");
                } else if (employee.getEmployeeRole() == Employee.EmployeeRole.Employee) {
                    App.setRoot("FXMLEmployeeMenu","Admin");
                }
            } else {
                Alert alert = Utils.getBox("Lỗi", "Đăng nhập không hợp lệ", "Tài khoản hoặc mật khẩu không chính xác", Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = Utils.getBox("Lỗi", "FXML Lỗi", "An error occurred while loading the FXML file", Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
