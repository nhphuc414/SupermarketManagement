/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.pojo.BranchProduct;
import com.ktpm.pojo.Product;
import com.ktpm.services.BranchProductService;
import com.ktpm.services.BranchService;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.BranchProductServiceImpl;
import com.ktpm.services.impl.BranchServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLBranchProductManagerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private SearchableComboBox<Branch> comboBoxBranch;

    @FXML
    private SearchableComboBox<Product> comboBoxProduct;

    @FXML
    private TableView<BranchProduct> tableBranchProduct;

    @FXML
    private TextField textFieldQuantity;
    private final ObservableList<Product> productTableData = FXCollections.observableArrayList();
    private final ObservableList<Branch> branchTableData = FXCollections.observableArrayList();
    private final ObservableList<BranchProduct> branchProductTableData = FXCollections.observableArrayList();

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
            App.setRoot("FXMLAdminMenu", " Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void loadColumns() {
        TableColumn<BranchProduct, ?> branchProductIdColumn = new TableColumn<>("Stock ID");
        branchProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BranchProduct, ?> quantityColumn = new TableColumn<>("Số lượng");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<BranchProduct, String> productNameColumn = new TableColumn<>("Sản phẩm");
        productNameColumn.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getProductId();
            String productName = "";
            try {
                ProductService productService = new ProductServiceImpl();
                productName = productService.getProductById(productId).toString();
            } catch (SQLException ex) {
                Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
            return new SimpleStringProperty(productName);
        });

        TableColumn<BranchProduct, String> branchNameColumn = new TableColumn<>("Chi nhánh");
        branchNameColumn.setCellValueFactory(cellData -> {
            String branchId = cellData.getValue().getBranchId();
            String branchName = "";
            try {
                BranchService branchService = new BranchServiceImpl();
                branchName = branchService.getBranchById(branchId).getBranchName();
            } catch (SQLException ex) {
                Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
            return new SimpleStringProperty(branchName);
        });
        tableBranchProduct.getColumns().addAll(branchProductIdColumn, branchNameColumn, productNameColumn, quantityColumn);

        TableColumn<BranchProduct, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);
        tableBranchProduct.getColumns().add(actionColumn);
    }

    public void onLoad() {
        loadColumns();
        try {
            //Load branches
            BranchService branchService = new BranchServiceImpl();
            List<Branch> branches = branchService.getAllBranches();
            branchTableData.addAll(branches);
            //Load product
            ProductService productService = new ProductServiceImpl();
            List<Product> products = productService.getAllProducts();
            productTableData.addAll(products);
            comboBoxBranch.setItems(branchTableData);
            comboBoxBranch.setValue(branchTableData.get(0));
            comboBoxProduct.setItems(productTableData);
            comboBoxProduct.setValue(productTableData.get(0));
            BranchProductService branchProductService = new BranchProductServiceImpl();
            List<BranchProduct> branchProducts = branchProductService.getAllBranchProducts();
            branchProductTableData.addAll(branchProducts);
            tableBranchProduct.setItems(branchProductTableData);
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void resetField() {
        comboBoxBranch.setValue(branchTableData.get(0));
        comboBoxProduct.setValue(productTableData.get(0));
        textFieldQuantity.setText("");
        textFieldQuantity.requestFocus();
    }

    public void loadOnField(BranchProduct branchProduct) {
        for (Branch branch : branchTableData) {
            if (branch.getId().equals(branchProduct.getBranchId())) {
                comboBoxBranch.setValue(branch);
                break;
            }
        }
        for (Product product : productTableData) {
            if (product.getId() == branchProduct.getProductId()) {
                comboBoxProduct.setValue(product);
                break;
            }
        }
        comboBoxBranch.setDisable(true);
        comboBoxProduct.setDisable(true);
        textFieldQuantity.setText(String.valueOf(branchProduct.getQuantity()));
        textFieldQuantity.requestFocus();
    }

    public void getBranchProductInField(BranchProduct branchProduct) {
        branchProduct.setBranchId(comboBoxBranch.getValue().getId());
        branchProduct.setProductId(comboBoxProduct.getValue().getId());
        branchProduct.setQuantity(Double.parseDouble(textFieldQuantity.getText()));
    }

    public boolean checkValid(String id) {
        Branch branch = comboBoxBranch.getValue();
        Product product = comboBoxProduct.getValue();
        String quantity = textFieldQuantity.getText();
        if (branch == null || product == null || "".equals(quantity)) {
            Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng điền đủ thông tin", Alert.AlertType.ERROR).showAndWait();
        } else {
            if (branchProductTableData.stream().anyMatch(bp ->
            !bp.getId().equals(id) && bp.getBranchId().equals(branch.getId()) && bp.getProductId()==product.getId())){
                Utils.getBox("Lỗi", "Thêm thất bại", "Kho đã có sản phẩm này", Alert.AlertType.ERROR).showAndWait();  
            }
            else return true;
        }
        return false;
    }

    public void addBranchProduct(ActionEvent event) {
        BranchProduct branchProduct = new BranchProduct();
        if (checkValid("")) {
            getBranchProductInField(branchProduct);
            try {
                BranchProductService branchProductService = new BranchProductServiceImpl();
                branchProductService.addBranchProduct(branchProduct);
                branchProductTableData.add(branchProduct);
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                resetField();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm thất bại", Alert.AlertType.ERROR).showAndWait();
            }
        }
    }
    
    private class ButtonCell extends TableCell<BranchProduct, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");
        private EventHandler<ActionEvent> originalEditEvent;
        private EventHandler<ActionEvent> originalDeleteEvent;

        private void setButton(Boolean value) {
            tableBranchProduct.lookupAll("Button").forEach(node -> {
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
                BranchProduct branchProduct = getTableView().getItems().get(getIndex());
                loadOnField(branchProduct);
                deleteButton.setOnAction(cancelEvent -> {
                    afterCommitOrCancel();
                });
                editButton.setOnAction(commitEvent -> {
                    
                    if (checkValid(branchProduct.getId())) {
                        getBranchProductInField(branchProduct);
                        try {
                            BranchProductService branchProductService = new BranchProductServiceImpl();
                            branchProductService.updateBranchProduct(branchProduct);
                            tableBranchProduct.refresh();
                            Utils.getBox("Thành công", "", "Cập nhật thành công", Alert.AlertType.INFORMATION).showAndWait();
                            afterCommitOrCancel();
                            comboBoxBranch.setDisable(false);
                            comboBoxProduct.setDisable(false);
                        } catch (SQLException ex) {
                            Utils.getBox("Sửa thất bại", "Không sửa được", "Có lỗi với cơ sở dữ liệu", Alert.AlertType.ERROR).showAndWait();

                        }
                    }
                });
            });
            deleteButton.setOnAction(event -> {
                BranchProduct branchProduct = getTableView().getItems().get(getIndex());
                Alert alert = Utils.getBox("Xác nhận xóa", "Bạn có chắc chắn muốn xóa?","Stock sẽ bị xóa vĩnh viễn.", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        BranchProductService branchProductService = new BranchProductServiceImpl();
                        branchProductService.deleteBranchProduct(branchProduct.getId());
                        branchProductTableData.remove(branchProduct);
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
