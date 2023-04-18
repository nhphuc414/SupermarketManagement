/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Customer;
import com.ktpm.services.CustomerService;
import com.ktpm.services.impl.CustomerServiceImpl;
import com.ktpm.utils.Utils;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLCustomerController implements Initializable {

    @FXML
    private DatePicker datePickerBirthday;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldNumber;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void resetField() {
        textFieldName.setText("");
        textFieldNumber.setText("");
        datePickerBirthday.setValue(null);
        textFieldName.requestFocus();
    }

    public void getCustomerInField(Customer customer) {
        customer.setCustomerName(textFieldName.getText());
        customer.setNumber(textFieldNumber.getText());
        customer.setBirthday(Date.valueOf(datePickerBirthday.getValue()));
    }

    public boolean checkValid() {
        String name = textFieldName.getText().trim();
        String number = textFieldNumber.getText().trim();
        LocalDate birthday = datePickerBirthday.getValue();
        if (Utils.checkName(name) && Utils.checkNumber(number) && Utils.checkBirthday(birthday)) {
            CustomerService customerService = new CustomerServiceImpl();
            Customer existCustomer;
            try {
                existCustomer = customerService.getCustomerByNumber(number);
                if (existCustomer != null) {
                    Utils.getBox("Thất bại", "Trùng số điện thoại", "Đã có số điện thoại này", Alert.AlertType.ERROR).showAndWait();
                    return false;
                }
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Lỗi kết nối cơ sở dữ liệu", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public void addCustomer(ActionEvent event) {
        Customer customer = new Customer();
        if (checkValid()) {
            getCustomerInField(customer);
            try {
                CustomerService customerService = new CustomerServiceImpl();
                customerService.addCustomer(customer);
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                resetField();
                Stage stage = (Stage) textFieldNumber.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm thất bại", Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    public void returnMain() {
        Alert alert = Utils.getBox("Xác nhận", "Bạn có chắc chắn muốn thoát", "", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) textFieldNumber.getScene().getWindow();
                stage.close();
            }
        });
    }
}
