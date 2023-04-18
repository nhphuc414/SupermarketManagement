/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Product;
import com.ktpm.pojo.Product.ProductType;
import com.ktpm.services.BranchProductService;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.BranchProductServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
    private final BranchProductService branchProductService = new BranchProductServiceImpl();
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

        TableColumn<Product, String> priceColumn = new TableColumn<>("Giá");
        priceColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(Utils.df.format(cellData.getValue().getPrice()));
        });

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
            productTableData.addAll(products);
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

    public boolean checkValid() {
        String name = textFieldName.getText().trim();
        String origin = textFieldOrigin.getText().trim();
        String price = textFieldPrice.getText().trim();
        if (Utils.checkTextField(name, "Tên sản phẩm", 30, 0)
                && Utils.checkTextField(origin, "Xuất xứ", 30, 0)
                && Utils.checkEmpty(price)) {
            try {
                Double priceProduct = Double.valueOf(price);
                if (priceProduct <= 0) {
                    Utils.getBox("Lỗi", "", "Vui lòng nhập số lượng chính xác", Alert.AlertType.ERROR).showAndWait();
                    return false;
                }
            } catch (NumberFormatException e) {
                Utils.getBox("Lỗi", "Sai số lượng", "Vui lòng nhập đúng số lượng", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public Product checkexistProduct(Product product) {
        for (Product p : productTableData) {
            if (!(p.getId() == product.getId()) && p.getOrigin().equals(product.getOrigin()) && p.getProductType().equals(product.getProductType()) && p.getProductName().equals(product.getProductName())) {
                return p;
            }
        }
        return null;
    }

    public boolean checkStock(Product product) {
        try {
            return !branchProductService.getBranchProductsByProductId(product.getId()).isEmpty();
        } catch (SQLException ex) {
            Utils.getBox("Thất bại", "Có lỗi", "Không thể kết nối đến cơ sở dữ liệu", Alert.AlertType.ERROR).showAndWait();
        }
        return true;
    }

    public void addProduct(ActionEvent event) {
        Product product = new Product();
        if (checkValid()) {
            getProductInField(product);
            try {
                product.setId(-1);
                Product existProduct = checkexistProduct(product);
                if (existProduct != null) {
                    existProduct.setPrice(product.getPrice());
                    productService.updateProduct(existProduct);
                    tableProduct.refresh();
                    Utils.getBox("Thành công", "", "Cập nhật thành công", Alert.AlertType.INFORMATION).showAndWait();
                    resetField();
                } else {
                    product.setId(productService.addProduct(product));
                    productTableData.add(product);
                    Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                    resetField();
                }
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
                    if (checkValid()) {
                        getProductInField(product);
                        if (checkexistProduct(product) != null) {
                            Utils.getBox("Cập nhật thất bại", "Không thể cập nhật", "Đã tồn tại sản phẩm", Alert.AlertType.ERROR).showAndWait();
                            textFieldName.requestFocus();

                        } else if (checkStock(product)) {
                            Utils.getBox("Cập nhật thất bại", "Không thể cập nhật", "Kho vẫn còn sản phẩm", Alert.AlertType.ERROR).showAndWait();
                            textFieldName.requestFocus();
                        } else {
                            try {
                                productService.updateProduct(product);
                                tableProduct.refresh();
                                Utils.getBox("Thành công", "", "Cập nhật thành công", Alert.AlertType.INFORMATION).showAndWait();
                                afterCommitOrCancel();
                            } catch (SQLException ex) {
                                Utils.getBox("Cập nhật thất bại", "Không thể cập nhật", "Lỗi không cập nhật được", Alert.AlertType.ERROR).showAndWait();
                                textFieldName.requestFocus();
                            }
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
                        resetField();
                    } catch (SQLException ex) {
                        Utils.getBox("Xóa thất bại", "", "Kho vẫn còn sản phẩm", Alert.AlertType.ERROR).showAndWait();
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
