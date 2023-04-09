/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.pojo.BranchProduct;
import com.ktpm.pojo.Employee;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLEmployeeMenuController implements Initializable {
    private Branch branch = null;
    private List<Product> products = new ArrayList<>(); 
    private List<BranchProduct> branchProducts = new ArrayList<>();
    @FXML
    private ComboBox<Product> cbProduct;
    @FXML
    private Label lbBranch;

    @FXML
    private Label lbName;
    
    void onLoad(){
        try {
            ProductService productService = new ProductServiceImpl();
            BranchProductService branchProductService = new BranchProductServiceImpl();
            BranchServiceImpl branchService = new BranchServiceImpl();
            branch= branchService.getBranchById(App.getCurrentEmployee().getBranchId());
            branchProducts = branchProductService.getBranchProductsByBranchId(branch.getId());
            for (BranchProduct bp: branchProducts){
                products.add(productService.getProductById(bp.getProductId()));
            }
             ObservableList<Product> productList = FXCollections.observableArrayList();
             productList.addAll(products);
             cbProduct.setItems(productList);
             lbName.setText(App.getCurrentEmployee().getUsername());
             lbName.setText(branch.getAddress());
        } catch (SQLException e){
            Alert alert = Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", e.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }
    @FXML
    void handleSignOut(ActionEvent event) throws IOException {
        Alert alert = Utils.getBox("Confirm Sign Out", null, "Are you sure you want to sign out?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            App.setCurrentEmployee(null);
            App.setRoot("FXMLLogin","Login");
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
