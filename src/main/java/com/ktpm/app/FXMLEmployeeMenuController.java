/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;
import com.ktpm.pojo.Branch;
import com.ktpm.pojo.Discount;
import com.ktpm.pojo.OrderDetail;
import com.ktpm.pojo.Product;
import com.ktpm.services.BranchProductService;
import com.ktpm.services.BranchService;
import com.ktpm.services.DiscountService;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.BranchProductServiceImpl;
import com.ktpm.services.impl.BranchServiceImpl;
import com.ktpm.services.impl.DiscountServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLEmployeeMenuController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private SearchableComboBox<Product> comboBoxProduct;


    @FXML
    private TableView<OrderDetail> tableOrderDetail;

    @FXML
    private TextField textFieldQuantity;
    
    final private BranchService branchService = new BranchServiceImpl();
    final private BranchProductService branchProductService = new BranchProductServiceImpl();
    final private ProductService productService = new ProductServiceImpl();
    final private DiscountService discountService = new DiscountServiceImpl();
    private final ObservableList<Product> productTableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void handleSignOut(ActionEvent event){
        Alert alert = Utils.getBox("Confirm Sign Out", null, "Are you sure you want to sign out?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try{
            App.setCurrentEmployee(null);
            App.setRoot("FXMLLogin", "Login");
            } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
        }
    }
    
    public void loadColumns(){
        TableColumn<OrderDetail, String> productNameColumn = new TableColumn<>("Sản phẩm");
        productNameColumn.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getProductId();
            String productName = "";
            try {
                productName = productService.getProductById(productId).toString();
            } catch (SQLException ex) {
                Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
            return new SimpleStringProperty(productName);
        });
        TableColumn<OrderDetail, String> priceColumn = new TableColumn<>("Giá");
        priceColumn.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getProductId();
            String price = "";
            try {
                price = String.valueOf(productService.getProductById(productId).getPrice());
            } catch (SQLException ex) {
                Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
            return new SimpleStringProperty(price);
        });
        TableColumn<Branch, ?> quantityColumn = new TableColumn<>("Số lượng");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        
    }

    public void addCustomer(ActionEvent event) {

    }

    public void addOrderDetail(ActionEvent event) {

    }

    public void btnPay(ActionEvent event) {

    }

    public void onChoose(ActionEvent event) {
        
    }

}
