/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Employee;
import com.ktpm.pojo.Product;
import com.ktpm.pojo.Product.ProductType;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLProductManagerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private ComboBox<ProductType> comboBoxType;

    @FXML
    private TableView<Product> tableProduct;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldOrigin;

    @FXML
    private TextField textFieldPrice;

    final private ProductService productService = new ProductServiceImpl();
    private ObservableList<Product> productTableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            onLoad();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void returnMain(ActionEvent event) throws IOException {
        App.setRoot("FXMLAdminMenu", " Manager");
    }

    public void loadProduct() throws SQLException {
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            productTableData.add(new Product(product.getId(), product.getProductName(), product.getPrice(), product.getOrigin(), product.getProductType()));
        }
    }

    public void onLoad() throws SQLException {
        loadProduct();
        TableColumn<Product, ?> idColumn = new TableColumn<>("Mã sản phẩm");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, ?> nameColumn = new TableColumn<>("Tên sản phẩm");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, ?> originColumn = new TableColumn<>("Xuất xứ");
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));

        TableColumn<Product, ?> priceColumn = new TableColumn<>("Giá");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, ?> typeColumn = new TableColumn<>("Kiểu định lượng");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));

        tableProduct.getColumns().addAll(idColumn, nameColumn, originColumn, priceColumn, typeColumn);
        tableProduct.setItems(productTableData);
        comboBoxType.getItems().addAll(ProductType.Quantity, ProductType.Weight);
        comboBoxType.setValue(ProductType.Quantity);
    }

    public void resetField() {
        textFieldName.setText("");
        textFieldOrigin.setText("");
        textFieldPrice.setText("");
        comboBoxType.setValue(ProductType.Quantity);
        textFieldName.requestFocus();
    }

    public boolean checkValid(int id, String name, String origin, String price, ProductType type) {
        if ("".equals(name) || "".equals(origin) || "".equals(price)) {
            Alert alert = Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng nhập đủ thông tin", Alert.AlertType.ERROR);
            textFieldName.requestFocus();
            alert.showAndWait();
        } else if (productTableData.
                stream().anyMatch(p -> !(p.getId() == id) && (p.getProductName().equals(name) && p.getOrigin().equals(origin)))) {
            Alert alert = Utils.getBox("Lỗi", "Trùng sản phẩm", "Đã có sản phẩm này", Alert.AlertType.INFORMATION);
            textFieldName.requestFocus();
            alert.showAndWait();
        } else {
            return true;
        }
        return false;
    }

    public Product getProductInField() {
        String name = textFieldName.getText().trim();
        String origin = textFieldOrigin.getText().trim();
        String price = textFieldPrice.getText().trim();
        ProductType type = comboBoxType.getValue();
        if (checkValid(0, name, origin, price, type)) {
             return new Product(name, Double.parseDouble(price), origin, type);
        }
        return null;
    }

    @FXML
    public void addProduct(ActionEvent event) throws SQLException {
        Product product = getProductInField();
        if (product!=null){
        product.setId(productService.addProduct(product));
        productTableData.add(product);
        Alert alert = Utils.getBox("Thành công", "Thêm thành công", "Thêm sản phẩm thành công", Alert.AlertType.INFORMATION);
        alert.showAndWait();
        }
    }
    
}
