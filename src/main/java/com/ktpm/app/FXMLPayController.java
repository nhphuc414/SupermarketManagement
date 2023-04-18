/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Order;
import com.ktpm.pojo.OrderDetail;
import com.ktpm.services.OrderDetailService;
import com.ktpm.services.OrderService;
import com.ktpm.services.impl.OrderDetailServiceImpl;
import com.ktpm.services.impl.OrderServiceImpl;
import com.ktpm.utils.Utils;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLPayController implements Initializable {

    @FXML
    private Label labelpriceChange;

    @FXML
    private TextField textFieldPriceFromCustomer;

    @FXML
    private Label labelpricePay;
    private double total;
    private String customeId;
    private ObservableList<OrderDetail> orderDetailTableData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onLoad();
    }

    public void onLoad() {
        DecimalFormat df = new DecimalFormat("#.##");
        labelpricePay.setText(df.format(total));
        labelpriceChange.setText("0");
    }

    public boolean checkValid() {
        try {
            double number = Double.parseDouble(textFieldPriceFromCustomer.getText());
            if (number < total) {
                Utils.getBox("Lỗi", "", "Tiền khách nhập nhỏ hơn tổng tiền phải trả", Alert.AlertType.ERROR).showAndWait();
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng nhập đúng số tiền", Alert.AlertType.ERROR).showAndWait();
        }
        return false;
    }

    public void addOrder() {
        Order order = new Order();
        if (checkValid()) {
            OrderService orderService = new OrderServiceImpl();
            OrderDetailService orderDetailService = new OrderDetailServiceImpl();
            order.setEmployeeId(App.getCurrentEmployee().getId());
            order.setCustomerId(customeId);
            order.setTotal(total);
            order.setCashReceived(Double.parseDouble(textFieldPriceFromCustomer.getText()));
            order.setOrderDate(Date.valueOf(LocalDate.now()));
            try {
                orderService.addOrder(order);
                for (OrderDetail orderDetail : orderDetailTableData) {
                    orderDetail.setOrderId(order.getId());
                    orderDetailService.addOrderDetail(orderDetail);
                }
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                FXMLEmployeeMenuController.resetOrderDetail = true;
                Stage stage = (Stage) labelpriceChange.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm Hóa đơn thất bại", Alert.AlertType.ERROR).showAndWait();
            }
        }

    }

    public void returnMain() {
        Alert alert = Utils.getBox("Xác nhận", "Bạn có chắc chắn muốn trở về", "", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) labelpricePay.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void deleteOrder() {
        Alert alert = Utils.getBox("Xác nhận", "Bạn có chắc chắn muốn xóa hóa đơn", "", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                FXMLEmployeeMenuController.resetOrderDetail = true;
                Stage stage = (Stage) labelpricePay.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setOrderDetailTableData(ObservableList<OrderDetail> orderDetailTableData) {
        this.orderDetailTableData = orderDetailTableData;
    }

    public void setCustomeId(String customeId) {
        this.customeId = customeId;
    }

}
