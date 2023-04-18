/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.services.BranchService;
import com.ktpm.services.impl.BranchServiceImpl;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class FXMLBranchManagerController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private TableView<Branch> tableBranch;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAddress;

    private final BranchService branchService = new BranchServiceImpl();
    private final  ObservableList<Branch> branchTableData = FXCollections.observableArrayList();

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
        TableColumn<Branch, String> nameColumn = new TableColumn<>("Tên chi nhánh");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        nameColumn.setPrefWidth(200);

        TableColumn<Branch, String> addressColumn = new TableColumn<>("Địa chỉ");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setPrefWidth(300);

        tableBranch.getColumns().addAll(nameColumn, addressColumn);

        TableColumn<Branch, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);

        tableBranch.getColumns().add(actionColumn);
    }

    public void onLoad() {
        loadColumns();
        try {
            List<Branch> branches = branchService.getAllBranches();
            branchTableData.addAll(branches);
            tableBranch.setItems(branchTableData);
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void resetField() {
        textFieldName.setText("");
        textFieldAddress.setText("");
        textFieldName.requestFocus();
    }

    public void loadOnField(Branch branch) {
        textFieldName.setText(branch.getBranchName());
        textFieldAddress.setText(branch.getAddress());
        textFieldName.requestFocus();
    }

    public void getBranchInField(Branch branch) {
        branch.setBranchName(textFieldName.getText().trim());
        branch.setAddress(textFieldAddress.getText().trim());
    }

    public boolean checkValid(String id) {
        String name = textFieldName.getText().trim();
        String address = textFieldAddress.getText().trim();
        if (Utils.checkTextField(name, "Tên chi nhánh", 30, 0)
            && Utils.checkTextField(address, "Tên chi nhánh", 50, 4)) {
            if (branchTableData.stream().
                    anyMatch(b -> !b.getId().equals(id)
                    && (   b.getBranchName().equalsIgnoreCase(name) 
                        || b.getAddress().equalsIgnoreCase(address)))) {
                Utils.getBox("Lỗi", "", "Trùng tên địa chỉ hoặc chi nhánh", AlertType.ERROR).showAndWait();
                textFieldName.requestFocus();
                return false;
            } else return true;
        }
        textFieldName.requestFocus();
        return false;
        
    }

    public void addBranch(ActionEvent event) {
        Branch branch = new Branch();
        if (checkValid("")) {
            getBranchInField(branch);
            try {
                branchService.addBranch(branch);
                branchTableData.add(branch);
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                resetField();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm thất bại", Alert.AlertType.ERROR).showAndWait();
                textFieldName.requestFocus();
            }
        }
    }

    // Custom cell factory for creating ButtonCells
    private class ButtonCell extends TableCell<Branch, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");
        private EventHandler<ActionEvent> originalEditEvent;
        private EventHandler<ActionEvent> originalDeleteEvent;

        private void setButton(Boolean value) {
            tableBranch.lookupAll("Button").forEach(node -> {
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
                Branch branch = getTableView().getItems().get(getIndex());
                loadOnField(branch);
                
                deleteButton.setOnAction(cancelEvent -> {
                    afterCommitOrCancel();
                });

                editButton.setOnAction(commitEvent -> {
                    if (checkValid(branch.getId())) {
                        getBranchInField(branch);
                        try {
                            branchService.updateBranch(branch);
                            tableBranch.refresh();
                            Utils.getBox("Thành công", "", "Cập nhật thành công", Alert.AlertType.INFORMATION).showAndWait();
                            afterCommitOrCancel();
                        } catch (SQLException ex) {
                            Utils.getBox("Sửa thất bại", "Không sửa được", "Có lỗi với cơ sở dữ liệu", Alert.AlertType.ERROR).showAndWait();
                            textFieldName.requestFocus();
                        }
                    }
                });
            });
            deleteButton.setOnAction(event -> {
                Branch branch = getTableView().getItems().get(getIndex());
                Alert alert = Utils.getBox("Xác nhận xóa", "Bạn có chắc chắn muốn xóa?", branch.getBranchName() + " sẽ bị xóa vĩnh viễn.", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        branchService.deleteBranch(branch.getId());
                        branchTableData.remove(branch);
                        Utils.getBox("Thành công", "", "Xóa thành công", Alert.AlertType.INFORMATION).showAndWait();
                    } catch (SQLException ex) {
                        Utils.getBox("Xóa thất bại", "Không thể xóa", "Lỗi không thể xóa được", Alert.AlertType.ERROR).showAndWait();
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
