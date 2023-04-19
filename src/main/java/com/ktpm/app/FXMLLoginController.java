/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import com.ktpm.services.EmployeeService;
import com.ktpm.services.impl.EmployeeServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    /**
     *
     */
    public void loginAccount() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        EmployeeService employeeService = new EmployeeServiceImpl();
        try {
            Employee employee = employeeService.getEmployeeByUsernameAndPassword(username, password);
            if (employee != null) {
                App.setCurrentEmployee(employee);
                if (employee.getEmployeeRole() == Employee.EmployeeRole.Manager) {
                    App.setRoot("FXMLAdminMenu", "Supermarket Manager");
                } else if (employee.getEmployeeRole() == Employee.EmployeeRole.Employee) {
                    App.setRoot("FXMLEmployeeMenu", "Employee");
                }
            } else {
                Utils.getBox("Lỗi", "Đăng nhập không hợp lệ", "Tài khoản hoặc mật khẩu không chính xác", Alert.AlertType.ERROR).showAndWait();
            }
        } catch (SQLException e) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", e.getMessage(), Alert.AlertType.ERROR).showAndWait();
        } catch (IOException e) {
            Utils.getBox("Thất bại", e.getLocalizedMessage(), e.getMessage(), Alert.AlertType.ERROR).showAndWait();
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
        addEnterEvent(btnLogin);
    }

    public void addEnterEvent(Button add) {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(txtUsername);
        nodeList.add(txtPassword);
        EventHandler<KeyEvent> eventHandler = event -> {
            if (null != event.getCode()) {
                switch (event.getCode()) {
                    case ENTER:
                        event.consume();
                        add.fire();
                        break;
                    case ESCAPE:
                        event.consume();
                        ((Stage)txtUsername.getScene().getWindow()).close();
                        break;
                    default:
                        break;
                }
            }
        };
        for (Node node : nodeList) {
            node.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
        }
    }
}
