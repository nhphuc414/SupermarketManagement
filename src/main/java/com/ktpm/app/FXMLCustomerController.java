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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLCustomerController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReturn;

    @FXML
    private DatePicker datePickerBirthday;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldNumber;
    
    private final CustomerService customerService = new CustomerServiceImpl();
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addEnterEvent(btnAdd, btnReturn);
    }
    private void addEnterEvent(Button add, Button esc) {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(textFieldName);
        nodeList.add(textFieldNumber);
        EventHandler<KeyEvent> eventHandler = event -> {
            if (null != event.getCode()) {
                switch (event.getCode()) {
                    case ENTER:
                        event.consume();
                        add.fire();
                        break;
                    case ESCAPE:
                        event.consume();
                        esc.fire();
                        break;
                    case DELETE:
                        event.consume();
                        btnDelete.fire();
                    default:
                        break;
                }
            }
        };
        for (Node node : nodeList) {
            node.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
        }
        datePickerBirthday.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) ->{
            if (event.getCode()==KeyCode.ESCAPE){
                event.consume();
                        esc.fire();
            }
        });
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

    public void addCustomer() {
        Customer customer = new Customer();
        if (checkValid()) {
            getCustomerInField(customer);
            try {
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
