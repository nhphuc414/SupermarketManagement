/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Account;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.ktpm.services.AccountService;
import com.ktpm.services.impl.AccountServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        AccountService accountService = new AccountServiceImpl();
        try {
            Account account = accountService.getAccountByUsernameAndPassword(username, password);
            if (account != null) {
                App.setCurrentAccount(account);
                if (account.getAccountType() == Account.AccountType.Admin) {
                    App.setRoot("FXMLAdminMenu");
                } else if (account.getAccountType() == Account.AccountType.Employee) {
                    App.setRoot("FXMLEmployeeMenu");
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
