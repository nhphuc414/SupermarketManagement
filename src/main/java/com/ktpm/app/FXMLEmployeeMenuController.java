/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.pojo.BranchProduct;
import com.ktpm.pojo.Employee;
import com.ktpm.pojo.Order;
import com.ktpm.pojo.OrderDetail;
import com.ktpm.pojo.Product;
import com.ktpm.services.BranchProductService;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.BranchProductServiceImpl;
import com.ktpm.services.impl.BranchServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLEmployeeMenuController implements Initializable {

    private Branch branch = null;
    private Order order = null;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<OrderDetail> orderdetails = FXCollections.observableArrayList();
    private List<BranchProduct> branchProducts = new ArrayList<>();
    private Product currentProduct = null;

    @FXML
    private TableView<?> tbvOrderDetail;

    @FXML
    private SearchableComboBox<Product> cbProductName;

    @FXML
    private CustomTextField custxtQuantity;

    void addOrderDetails(ActionEvent event) {
        OrderDetail orderdetail = new OrderDetail(Double.parseDouble(custxtQuantity.getText()),
                currentProduct.getId(),
                order.getId());
        orderdetails.add(orderdetail);
    }

    void onLoad() {
        try {
            BranchServiceImpl branchService = new BranchServiceImpl();
            branch = branchService.getBranchById(App.getCurrentEmployee().getBranchId());
            loadProduct();
        } catch (SQLException e) {
            Alert alert = Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    void loadProduct() throws SQLException {
        ProductService productService = new ProductServiceImpl();
        BranchProductService branchProductService = new BranchProductServiceImpl();
        order = new Order();
        order.setEmployeeId(App.getCurrentEmployee().getId());
        products.clear();
        orderdetails.clear();
        branchProducts = branchProductService.getBranchProductsByBranchId(branch.getId());
        for (BranchProduct bp : branchProducts) {
            if (bp.getQuantity() != 0) {
                products.add(productService.getProductById(bp.getProductId()));
            }
        }
        cbProductName.setItems(products);
    }

    @FXML
    void onChoose(ActionEvent event) {
        currentProduct = cbProductName.getValue();
    }

    @FXML
    void handleSignOut(ActionEvent event) throws IOException {
        Alert alert = Utils.getBox("Confirm Sign Out", null, "Are you sure you want to sign out?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            App.setCurrentEmployee(null);
            App.setRoot("FXMLLogin", "Login");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
    }
}
