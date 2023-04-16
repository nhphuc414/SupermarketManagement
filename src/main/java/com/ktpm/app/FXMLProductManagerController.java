/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Product;
import com.ktpm.pojo.Product.ProductType;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

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

    private final ProductService productService = new ProductServiceImpl();
    private final ObservableList<Product> productTableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onLoad();
    }

    public void returnMain(ActionEvent event) {
        try {
            App.setRoot("FXMLAdminMenu", "Supermarket Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }

    }

    public void loadColumns() {
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

        TableColumn<Product, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);

        tableProduct.getColumns().add(actionColumn);
    }

    public void onLoad() {
        loadColumns();
        comboBoxType.getItems().addAll(ProductType.Quantity, ProductType.Weight);
        comboBoxType.setValue(ProductType.Quantity);
        try {
            List<Product> products = productService.getAllProducts();
            for (Product product : products) {
                productTableData.add(new Product(product.getId(), product.getProductName(), product.getPrice(), product.getOrigin(), product.getProductType()));
            }
            tableProduct.setItems(productTableData);
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void resetField() {
        textFieldName.setText("");
        textFieldOrigin.setText("");
        textFieldPrice.setText("");
        comboBoxType.setValue(ProductType.Quantity);
        textFieldName.requestFocus();
    }

    public void loadonField(Product product) {
        textFieldName.setText(product.getProductName());
        textFieldOrigin.setText(product.getOrigin());
        textFieldPrice.setText(String.valueOf(product.getPrice()));
        comboBoxType.setValue(product.getProductType());
        textFieldName.requestFocus();
    }

    public void getProductInField(Product product) {
        product.setProductName(textFieldName.getText().trim());
        product.setOrigin(textFieldOrigin.getText().trim());
        product.setPrice(Double.parseDouble(textFieldPrice.getText().trim()));
        product.setProductType(comboBoxType.getValue());
    }

    public boolean checkValid(int id) {
        String name = textFieldName.getText().trim();
        String origin = textFieldOrigin.getText().trim();
        String price = textFieldPrice.getText().trim();
        ProductType type = comboBoxType.getValue();
        if ("".equals(name) || "".equals(origin) || "".equals(price)) {
            Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng nhập đủ thông tin", Alert.AlertType.ERROR).showAndWait();
            textFieldName.requestFocus();
        } else if (productTableData.
                stream().anyMatch(p -> !(p.getId() == id) && (p.getProductName().equals(name) && p.getOrigin().equals(origin)))) {
            Utils.getBox("Lỗi", "Trùng sản phẩm", "Đã có sản phẩm này", Alert.AlertType.INFORMATION).showAndWait();
            textFieldName.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    @FXML
    public void addProduct(ActionEvent event) {
        Product product = new Product();
        if (checkValid(0)) {
            getProductInField(product);
            try {
                product.setId(productService.addProduct(product));
                productTableData.add(product);
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                resetField();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm thất bại", Alert.AlertType.ERROR).showAndWait();
                textFieldName.requestFocus();
            }
        }
    }

    private class ButtonCell extends TableCell<Product, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");
        EventHandler<ActionEvent> originalEditEvent;
        EventHandler<ActionEvent> originalDeleteEvent;

        private void setButton(Boolean value) {
            tableProduct.lookupAll("Button").forEach(node -> {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    button.setDisable(value);
                }
            });
            this.editButton.setDisable(false);
            this.deleteButton.setDisable(false);
        }

        private void beforeCommit() {
            editButton.setText("Cập nhật");
            deleteButton.setText("Hủy bỏ");
            btnAdd.setDisable(true);
            setButton(true);
        }

        private void afterCommitOrCancel() {
            resetField();
            setButton(false);
            btnAdd.setDisable(false);
            editButton.setText("Sửa");
            deleteButton.setText("Xóa");
            editButton.setOnAction(originalEditEvent);
            deleteButton.setOnAction(originalDeleteEvent);

        }

        public ButtonCell() {
            editButton.setOnAction(event -> {
                beforeCommit();
                originalEditEvent = editButton.getOnAction();
                originalDeleteEvent = deleteButton.getOnAction();
                Product product = getTableView().getItems().get(getIndex());
                loadonField(product);
                deleteButton.setOnAction(cancelEvent -> {
                    afterCommitOrCancel();
                });
                editButton.setOnAction(commitEvent -> {
                    if (checkValid(product.getId())) {
                        getProductInField(product);
                        try {
                            productService.updateProduct(product);
                            tableProduct.refresh();
                            Utils.getBox("Thành công", "", "Cập nhật thành công", Alert.AlertType.INFORMATION).showAndWait();
                            afterCommitOrCancel();
                        } catch (SQLException ex) {
                            Utils.getBox("Cập nhật thất bại", "Không thể cập nhật", "Có lỗi với cơ sở dữ liệu", Alert.AlertType.ERROR).showAndWait();
                            textFieldName.requestFocus();
                        }
                    }
                });
            });
            deleteButton.setOnAction(event -> {
                Product product = getTableView().getItems().get(getIndex());
                Alert alert = Utils.getBox("Xác nhận xóa", "Bạn có chắc chắn muốn xóa?", product.getProductName() + " sẽ bị xóa vĩnh viễn.", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        productService.deleteProduct(product.getId());
                        productTableData.remove(product);
                        Utils.getBox("Thành công", "", "Xóa thành công", Alert.AlertType.INFORMATION).showAndWait();
                    } catch (SQLException ex) {
                        Utils.getBox("Xóa thất bại", "", "Lỗi không thể xóa được", Alert.AlertType.ERROR).showAndWait();
                    }
                }
            });

        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox buttonBox = new HBox(editButton, deleteButton);
                setGraphic(buttonBox);
            }
        }
    }
}
