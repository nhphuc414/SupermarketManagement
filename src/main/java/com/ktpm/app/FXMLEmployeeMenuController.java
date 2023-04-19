/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.BranchProduct;
import com.ktpm.pojo.Customer;
import com.ktpm.pojo.Discount;
import com.ktpm.pojo.OrderDetail;
import com.ktpm.pojo.Product;
import com.ktpm.services.BranchProductService;
import com.ktpm.services.CustomerService;
import com.ktpm.services.DiscountService;
import com.ktpm.services.ProductService;
import com.ktpm.services.impl.BranchProductServiceImpl;
import com.ktpm.services.impl.CustomerServiceImpl;
import com.ktpm.services.impl.DiscountServiceImpl;
import com.ktpm.services.impl.ProductServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLEmployeeMenuController implements Initializable {

    @FXML
    private Label labelName;
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnCheckCustomer;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnReturn;

    @FXML
    private Label labelFind;

    @FXML
    private SearchableComboBox<Product> comboBoxProduct;

    @FXML
    private TableView<OrderDetail> tableOrderDetail;

    @FXML
    private TextField textFieldNumber;

    @FXML
    private TextField textFieldQuantity;

    @FXML
    private Label labelTotal;

    @FXML
    private Label labelTotalBirthday;

    private final ProductService productService = new ProductServiceImpl();
    private final BranchProductService branchProductService = new BranchProductServiceImpl();
    private final CustomerService customerService = new CustomerServiceImpl();
    private final ObservableList<Product> productTableData = FXCollections.observableArrayList();
    private final ObservableList<OrderDetail> orderDetailTableData = FXCollections.observableArrayList();
    private final TableColumn<OrderDetail, String> priceFinalColumn = new TableColumn<>("Giá cuối cùng");
    public static boolean resetOrderDetail = false;
    private double pricePay = 0;
    Customer customer;

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
    public void onLoad() {
        labelName.setText(App.getCurrentEmployee().getEmployeeName());
        loadProducts();
        loadColumns();
        labelTotal.setText("0");
        addEnterEvent(btnAdd, btnReturn);
        textFieldNumber.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) ->{
            if (event.getCode() == KeyCode.ENTER){
                event.consume();
                btnCheckCustomer.fire();
            }
        });
    }
    
    private EventHandler<KeyEvent> addEnterEvent(Button add, Button esc) {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(textFieldQuantity);
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
                    case F1:
                        event.consume();
                        btnPay.fire();
                        break;
                    case F2:
                        event.consume();
                        btnAddCustomer.fire();
                        break;
                    default:
                        break;
                }
            }
        };
        for (Node node : nodeList) {
                node.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
        }
        return eventHandler;
    }
    private void removeEnterEvent(EventHandler<KeyEvent> eventHandler) {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(textFieldQuantity);
        for (Node node : nodeList) {
            node.removeEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
        }
    }

    public void loadColumns() {
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
                price = Utils.df.format(productService.getProductById(productId).getPrice());
            } catch (SQLException ex) {
                Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
            return new SimpleStringProperty(price);
        });
        priceFinalColumn.setCellValueFactory(cellData -> {
            int productId = cellData.getValue().getProductId();
            String price = "";
            try {
                price = Utils.df.format(productService.getProductById(productId).getPrice());
            } catch (SQLException ex) {
                Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
            return new SimpleStringProperty(price);
        });
        TableColumn<OrderDetail, ?> quantityColumn = new TableColumn<>("Số lượng");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableOrderDetail.getColumns().addAll(productNameColumn, quantityColumn, priceColumn, priceFinalColumn);

        tableOrderDetail.setItems(orderDetailTableData);

        TableColumn<OrderDetail, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);
        tableOrderDetail.getColumns().add(actionColumn);

    }

    public void loadProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            double quantityInStock;
            for (Product product : products) {
                BranchProduct stock = branchProductService.getBranchProductsByBranchIdAndProductId(App.getCurrentEmployee().getBranchId(), product.getId());
                quantityInStock = stock == null ? 0 : stock.getQuantity();
                if (quantityInStock > 0) {
                    productTableData.add(product);
                }
            }
            comboBoxProduct.setItems(productTableData);
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void onChoose() {
        Product product = comboBoxProduct.getValue();
        if (product == null) {
            textFieldQuantity.setText("");
        } else {
            textFieldQuantity.setText(product.getProductType() == Product.ProductType.Quantity ? "1" : "0");
        }
        textFieldQuantity.requestFocus();
    }

    public void resetField() {
        comboBoxProduct.setValue(null);
        textFieldNumber.setText("");
        comboBoxProduct.requestFocus();
    }

    public void resetAll() {
        productTableData.removeAll(productTableData);
        loadProducts();
        textFieldNumber.setText("");
        labelFind.setText("");
        labelTotal.setText("0");
        labelTotalBirthday.setText("");
        orderDetailTableData.removeAll(orderDetailTableData);
        pricePay = 0;
        comboBoxProduct.requestFocus();
    }
    
    public void loadOnField(OrderDetail orderDetail) {
        for (Product product : productTableData) {
            if (product.getId() == orderDetail.getProductId()) {
                comboBoxProduct.setValue(product);
                break;
            }
        }
        textFieldQuantity.setText(String.valueOf(orderDetail.getQuantity()));
        textFieldQuantity.requestFocus();
    }

    public void getOrderDetailInField(OrderDetail orderDetail) {
        orderDetail.setProductId(comboBoxProduct.getValue().getId());
        orderDetail.setQuantity(Double.parseDouble(textFieldQuantity.getText().trim()));
    }

    public Double getDiscountValue(int productId, double price) {
        DiscountService discountService = new DiscountServiceImpl();
        try {
            List<Discount> discounts = discountService.getDiscountsByProductId(productId);
            for (Discount discount : discounts) {
                if (discount.getStartDate().before(Date.valueOf(LocalDate.now())) && discount.getEndDate().after(Date.valueOf(LocalDate.now()))) {
                    return price - price * discount.getDiscountPercent() / 100;
                }
            }
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
        return price;
    }

    public boolean checkValid() {
        Product product = comboBoxProduct.getValue();
        if (Utils.checkProductQuantity(product, textFieldQuantity.getText())) {
            double quantity = Double.parseDouble(textFieldQuantity.getText());
            try {
                Double quantityInStock;
                quantityInStock = branchProductService.getBranchProductsByBranchIdAndProductId(App.getCurrentEmployee().getBranchId(), product.getId()).getQuantity();
                if (quantity > quantityInStock) {
                    Utils.getBox("Lỗi", "", "Số lượng lớn hơn trong kho", Alert.AlertType.ERROR).showAndWait();
                    return false;
                }
            } catch (SQLException ex) {
                Utils.getBox("Lỗi", "", "Lỗi kết nối đến Database", Alert.AlertType.ERROR).showAndWait();
                return false;
            }
            return true;
        }
        return false;
    }

    public void checkCustomer() {
        String number = textFieldNumber.getText();
        try {
            Customer findcustomer = customerService.getCustomerByNumber(number);
            if (findcustomer == null) {
                labelFind.setText("Không tìm thấy khách hàng");
                customer = null;
                priceFinalColumn.setCellValueFactory(cellData -> {
                    int productId = cellData.getValue().getProductId();
                    String price = "";
                    try {
                        price = Utils.df.format(productService.getProductById(productId).getPrice());
                    } catch (SQLException ex) {
                        Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
                    }
                    return new SimpleStringProperty(price);
                });
                tableOrderDetail.refresh();
            } else {
                labelFind.setText("Khách hàng là thành viên của hệ thống");
                customer = findcustomer;
                comboBoxProduct.requestFocus();
                priceFinalColumn.setCellValueFactory(cellData -> {
                    int productId = cellData.getValue().getProductId();
                    String price = "";
                    try {
                        price = Utils.df.format(getDiscountValue(productId, productService.getProductById(productId).getPrice()));
                    } catch (SQLException ex) {
                        Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
                    }
                    return new SimpleStringProperty(price);
                });
                countTotal();
                tableOrderDetail.refresh();
            }
        } catch (SQLException ex) {
            Utils.getBox("Thất bại", "Có lỗi", "Kết nối cơ sở dữ liệu thất bại", Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void countTotal() {
        double sum = 0;
        for (var item : tableOrderDetail.getItems()) {
            sum += item.getQuantity() * Double.parseDouble(priceFinalColumn.getCellData(item));
        }
        if (customer != null && customer.getBirthday().toLocalDate().getMonth() == LocalDate.now().getMonth()
                && customer.getBirthday().toLocalDate().getDayOfMonth() == LocalDate.now().getDayOfMonth()
                && sum >= 1000000) {
            labelTotal.setText(Utils.df.format(sum) + "     --->");
            sum = sum - sum * 0.1;
            pricePay = sum;
            labelTotalBirthday.setText(Utils.df.format(sum) + "(-10%)");
        } else {
            pricePay = sum;
            labelTotal.setText(Utils.df.format(sum));
            labelTotalBirthday.setText("");
        }
    }

    public void addCustomer() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("FXMLCustomer.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Thêm khách hàng");
        stage.setResizable(false);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setOnCloseRequest(eh -> {
            Alert alert = Utils.getBox("Xác nhận", "Bạn có chắc chắn muốn thoát", "", Alert.AlertType.CONFIRMATION);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    eh.consume();
                    stage.close();
                }
            });
        });
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            comboBoxProduct.requestFocus();
        } catch (IOException ex) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }

    }

    public void addOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        if (checkValid()) {
            getOrderDetailInField(orderDetail);
            boolean inTable = false;
            for (OrderDetail orderDetailInTable : orderDetailTableData) {
                if (orderDetailInTable.getProductId() == orderDetail.getProductId()) {
                    double totalQuantity = orderDetailInTable.getQuantity() + orderDetail.getQuantity();
                    double quantityInStock;
                    try {
                        quantityInStock = branchProductService.getBranchProductsByBranchIdAndProductId(App.getCurrentEmployee().getBranchId(), orderDetailInTable.getProductId()).getQuantity();
                    } catch (SQLException ex) {
                        Utils.getBox("Lỗi", "", "Lỗi kết nối đến Database", Alert.AlertType.ERROR).showAndWait();
                        inTable = true;
                        break;
                    }
                    if (totalQuantity > quantityInStock) {
                        Utils.getBox("Lỗi", "", "Số lượng lớn hơn trong kho", Alert.AlertType.ERROR).showAndWait();
                        inTable = true;
                        break;
                    }
                    orderDetailInTable.setQuantity(orderDetailInTable.getQuantity() + orderDetail.getQuantity());
                    inTable = true;
                    countTotal();
                    tableOrderDetail.refresh();
                    break;
                }
            }
            if (!inTable) {
                orderDetailTableData.add(orderDetail);
                countTotal();
            }

        }
        comboBoxProduct.requestFocus();
    }

    public void payOrder() {
        if (!orderDetailTableData.isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLPay.fxml"));
            FXMLPayController payController = new FXMLPayController();
            payController.setTotal(pricePay);
            payController.setOrderDetailTableData(orderDetailTableData);
            payController.setCustomeId(customer == null ? null : customer.getId());
            fxmlLoader.setController(payController);
            resetOrderDetail = false;
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Thêm khách hàng");
            stage.setResizable(false);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setOnCloseRequest(eh -> {
                Alert alert = Utils.getBox("Xác nhận", "Bạn có chắc chắn muốn thoát", "", Alert.AlertType.CONFIRMATION);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        stage.close();
                    }
                });
            });
            try {
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.showAndWait();
                if (resetOrderDetail && !stage.isShowing()) {
                    loadProducts();
                    resetAll();
                }
            } catch (IOException ex) {
                Utils.getBox("Thất bại", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
            }
        } else {
            Utils.getBox("Thất bại", "", "Không có đơn hàng", Alert.AlertType.ERROR).showAndWait();

        }
    }

    private class ButtonCell extends TableCell<OrderDetail, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");
        EventHandler<ActionEvent> originalEditEvent;
        EventHandler<ActionEvent> originalDeleteEvent;
        private EventHandler<KeyEvent> eventEnter;
        private void setButton(Boolean value) {
            tableOrderDetail.lookupAll("Button").forEach(node -> {
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
            btnPay.setDisable(true);
            btnReturn.setDisable(true);
            btnCheckCustomer.setDisable(true);
            textFieldNumber.setDisable(true);
            btnAddCustomer.setDisable(true);
            eventEnter=addEnterEvent(this.editButton, this.deleteButton);
            setButton(true);
            comboBoxProduct.requestFocus();
        }

        private void afterCommitOrCancel() {
            resetField();
            setButton(false);
            removeEnterEvent(eventEnter);
            btnAdd.setDisable(false);
            btnPay.setDisable(false);
            btnReturn.setDisable(false);
            btnAddCustomer.setDisable(false);
            btnCheckCustomer.setDisable(false);
            textFieldNumber.setDisable(false);
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
                OrderDetail orderDetail = getTableView().getItems().get(getIndex());
                loadOnField(orderDetail);
                deleteButton.setOnAction(cancelEvent -> {
                    afterCommitOrCancel();
                });
                editButton.setOnAction(commitEvent -> {
                    if (checkValid()) {
                        getOrderDetailInField(orderDetail);
                        for (OrderDetail orderDetailInTable : orderDetailTableData) {
                            if (orderDetailInTable.getProductId() == orderDetail.getProductId() && !orderDetailInTable.getId().equals(orderDetail.getId())) {
                                double totalQuantity = orderDetailInTable.getQuantity() + orderDetail.getQuantity();
                                double quantityInStock;
                                try {
                                    quantityInStock = branchProductService.getBranchProductsByBranchIdAndProductId(App.getCurrentEmployee().getBranchId(), orderDetailInTable.getProductId()).getQuantity();
                                } catch (SQLException ex) {
                                    Utils.getBox("Lỗi", "", "Lỗi kết nối đến Database", Alert.AlertType.ERROR).showAndWait();  
                                    break;
                                }
                                if (totalQuantity > quantityInStock) {
                                    Utils.getBox("Lỗi", "", "Số lượng lớn hơn trong kho", Alert.AlertType.ERROR).showAndWait();
                                    break;
                                }
                                orderDetailInTable.setQuantity(orderDetailInTable.getQuantity() + orderDetail.getQuantity());
                                orderDetailTableData.remove(orderDetail);
                                break;
                            }
                        }
                        countTotal();
                        tableOrderDetail.refresh();
                        afterCommitOrCancel();
                    }
                });
            });
            deleteButton.setOnAction(event -> {
                OrderDetail orderDetail = getTableView().getItems().get(getIndex());
                Alert alert = Utils.getBox("Xác nhận xóa", "Bạn có chắc chắn muốn xóa?", "", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    orderDetailTableData.remove(orderDetail);
                    Utils.getBox("Thành công", "", "Xóa thành công", Alert.AlertType.INFORMATION).showAndWait();
                    countTotal();
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
    public void handleSignOut(ActionEvent event) {
        Alert alert = Utils.getBox("Confirm Sign Out", null, "Are you sure you want to sign out?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.setCurrentEmployee(null);
                App.setRoot("FXMLLogin", "Login");
            } catch (IOException io) {
                Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
            }
        }
    }
}
