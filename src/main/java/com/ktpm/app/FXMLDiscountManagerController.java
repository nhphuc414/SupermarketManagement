/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Discount;
import com.ktpm.pojo.Product;
import com.ktpm.services.DiscountService;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.DiscountServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
public class FXMLDiscountManagerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private SearchableComboBox<Product> comboBoxProduct;

    @FXML
    private DatePicker datePickerEndDate;

    @FXML
    private DatePicker datePickerStartDate;

    @FXML
    private TableView<Discount> tableDiscount;

    @FXML
    private TextField textFieldPercent;

    final private ProductService productService = new ProductServiceImpl();
    final private DiscountService discountService = new DiscountServiceImpl();
    private final ObservableList<Product> productTableData = FXCollections.observableArrayList();
    private final ObservableList<Discount> discountTableData = FXCollections.observableArrayList();

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
        TableColumn<Discount, ?> idColumn = new TableColumn<>("Mã giảm giá");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Discount, String> productNameColumn = new TableColumn<>("Sản phẩm");
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

        TableColumn<Discount, ?> startColumn = new TableColumn<>("Ngày bắt đầu");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<Discount, ?> endColumn = new TableColumn<>("Ngày kết thúc");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<Discount, ?> percentColumn = new TableColumn<>("Giảm(%)");
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        tableDiscount.getColumns().addAll(idColumn,productNameColumn, startColumn, endColumn, percentColumn);
        TableColumn<Discount, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);
        tableDiscount.getColumns().add(actionColumn);
    }

    public void onLoad() {
        loadColumns();
        try {
            List<Product> products = productService.getAllProducts();
            productTableData.addAll(products);
            comboBoxProduct.setItems(productTableData);
            comboBoxProduct.setValue(productTableData.get(0));
            List<Discount> discounts = discountService.getAllDiscounts();
            discountTableData.addAll(discounts);
            tableDiscount.setItems(discountTableData);
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void resetField() {
        textFieldPercent.setText("");
        comboBoxProduct.setValue(productTableData.get(0));
        datePickerStartDate.setValue(null);
        datePickerEndDate.setValue(null);
        textFieldPercent.requestFocus();
    }

    public void loadOnField(Discount discount) {
        for (Product product : productTableData) {
            if (product.getId() == discount.getProductId()) {
                comboBoxProduct.setValue(product);
                break;
            }
        }
        datePickerStartDate.setValue(LocalDate.parse(discount.getStartDate().toString()));
        datePickerEndDate.setValue(LocalDate.parse(discount.getEndDate().toString()));
        textFieldPercent.setText(String.valueOf(discount.getDiscountPercent()));

    }

    public void getDiscountInField(Discount discount) {
        discount.setProductId(comboBoxProduct.getValue().getId());
        discount.setStartDate(Date.valueOf(datePickerStartDate.getValue()));
        discount.setEndDate(Date.valueOf(datePickerEndDate.getValue()));
        discount.setDiscountPercent(Double.parseDouble(textFieldPercent.getText().trim()));
    }

    public boolean checkValid(int id) {
        Product product = comboBoxProduct.getValue();
        LocalDate startDate = datePickerStartDate.getValue();
        LocalDate endDate = datePickerEndDate.getValue();
        String percent = textFieldPercent.getText();
        if (product == null || "".equals(percent)) {
            Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng nhập đủ thông tin", Alert.AlertType.ERROR).showAndWait();
        } else if (startDate == null || endDate == null) {
            Utils.getBox("Lỗi", "Lỗi nhập liệu", "Vui lòng nhập đúng định dạng ngày tháng (MM/dd/yyyy)", Alert.AlertType.ERROR).showAndWait();
        } else if (discountTableData.stream().anyMatch(d -> !(id == d.getId()) && product.getId() == d.getProductId() && d.getStartDate().equals(Date.valueOf(startDate)) && d.getEndDate().equals(Date.valueOf(endDate)))) {
            Utils.getBox("Lỗi", "Thêm thất bại", "Đã có giảm giá này", Alert.AlertType.ERROR).showAndWait();
        } else {
            return true;
        }
        return false;
    }

    public void addDiscount(ActionEvent event) {
        Discount discount = new Discount();
        if (checkValid(0)) {
            getDiscountInField(discount);
            try {
                discount.setId(discountService.addDiscount(discount));
                discountTableData.add(discount);
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                resetField();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm thất bại", Alert.AlertType.ERROR).showAndWait();
            }
        }
    }

    private class ButtonCell extends TableCell<Discount, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");
        EventHandler<ActionEvent> originalEditEvent;
        EventHandler<ActionEvent> originalDeleteEvent;

        private void setButton(Boolean value) {
            tableDiscount.lookupAll("Button").forEach(node -> {
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
                Discount discount = getTableView().getItems().get(getIndex());
                loadOnField(discount);
                deleteButton.setOnAction(cancelEvent -> {
                    afterCommitOrCancel();
                });
                editButton.setOnAction(commitEvent -> {
                    if (checkValid(discount.getId())){
                        getDiscountInField(discount);
                        try {
                            discountService.updateDiscount(discount);
                            tableDiscount.refresh();
                            Utils.getBox("Thành công", "", "Cập nhật thành công", Alert.AlertType.INFORMATION).showAndWait();
                            afterCommitOrCancel();
                        } catch (SQLException ex) {
                            Utils.getBox("Cập nhật thất bại", "Không thể cập nhật", "Có lỗi với cơ sở dữ liệu", Alert.AlertType.ERROR).showAndWait();
                        }
                    }
                });
            });
            deleteButton.setOnAction(event -> {
                Discount discount = getTableView().getItems().get(getIndex());
                Alert alert = Utils.getBox("Xác nhận xóa", "Bạn có chắc chắn muốn xóa?", " Mã sẽ bị xóa vĩnh viễn.", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try { 
                        
                        discountService.deleteDiscount(discount.getId());
                        discountTableData.remove(discount);
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
