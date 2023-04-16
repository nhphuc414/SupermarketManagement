/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.services.BranchService;
import com.ktpm.services.impl.BranchServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    final private BranchService branchService = new BranchServiceImpl();
    private ObservableList<Branch> branchTableData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Load branches into table
            loadBranches();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBranchManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void addBranch(ActionEvent event) throws SQLException {
        String name = textFieldName.getText().trim();
        String address = textFieldAddress.getText().trim();
        if ("".equals(name) || "".equals(address)) {
            // Show an error message if there is a duplicate
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Nhập sai");
            alert.setHeaderText("Không được để trống tên hoặc địa chỉ");
            alert.showAndWait();
            textFieldName.requestFocus();
        } else {
            // Check if the new branch has the same name or address as an existing branch
            boolean hasDuplicate = branchTableData.stream().anyMatch(
                    b -> b.getBranchName().equalsIgnoreCase(name) || b.getAddress().equalsIgnoreCase(address));

            if (hasDuplicate) {
                // Show an error message if there is a duplicate
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Trùng branch");
                alert.setHeaderText("Trùng tên hoặc địa chỉ chi nhánh ");
                alert.showAndWait();
                textFieldName.requestFocus();
            } else {
                // Add the new branch if there are no duplicates
                Branch newBranch = new Branch(name, address);
                branchService.addBranch(newBranch);
                branchTableData.add(newBranch);
                // Clear the input fields
                textFieldName.setText("");
                textFieldAddress.setText("");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thêm thành công");
                alert.setContentText("Thêm thành công");
                alert.showAndWait();
                textFieldName.requestFocus();
            }
        }
    }

    private void loadBranches() throws SQLException {
        List<Branch> branches = branchService.getAllBranches();
        for (Branch branch : branches) {
            branchTableData.add(new Branch(branch.getId(), branch.getBranchName(), branch.getAddress()));
        }
        TableColumn<Branch, String> nameColumn = new TableColumn<>("Tên chi nhánh");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        nameColumn.setPrefWidth(200);
        TableColumn<Branch, String> addressColumn = new TableColumn<>("Địa chỉ");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setPrefWidth(300);
        tableBranch.getColumns().addAll(nameColumn, addressColumn);
        tableBranch.setItems(branchTableData);

        TableColumn<Branch, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);
        tableBranch.getColumns().add(actionColumn);
    }

    // Custom cell factory for creating ButtonCells
    private class ButtonCell extends TableCell<Branch, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");

        private void updateCommitOrCancel() {
            textFieldName.setText("");
            textFieldAddress.setText("");
            textFieldName.requestFocus();
            editButton.setText("Sửa");
            deleteButton.setText("Xóa");
            btnAdd.setDisable(false);
        }

        private void beforeCommit() {
            editButton.setText("Cập nhật");
            deleteButton.setText("Hủy bỏ");
            btnAdd.setDisable(true);
        }

        public ButtonCell() {
            editButton.setOnAction(event -> {
                beforeCommit();
                EventHandler<ActionEvent> originalEditEvent = editButton.getOnAction();
                EventHandler<ActionEvent> originalDeleteEvent = deleteButton.getOnAction();
                deleteButton.setOnAction(cancelEvent -> {
                    updateCommitOrCancel();
                    editButton.setOnAction(originalEditEvent);
                    deleteButton.setOnAction(originalDeleteEvent);
                });
                Branch branch = getTableView().getItems().get(getIndex());
                textFieldName.setText(branch.getBranchName());
                textFieldAddress.setText(branch.getAddress());
                editButton.setOnAction(commitEvent -> {
                    String name = textFieldName.getText().trim();
                    String address = textFieldAddress.getText().trim();
                    if ("".equals(name) || "".equals(address)) {
                        // Show an error message if there is a duplicate
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Nhập sai");
                        alert.setHeaderText("Không được để trống tên hoặc địa chỉ");
                        alert.showAndWait();
                        textFieldName.requestFocus();
                    } else {
                        // Check if the new branch has the same name or address as an existing branch
                        boolean hasDuplicate = branchTableData.stream().anyMatch(
                                b -> !b.getId().equals(branch.getId()) && (b.getBranchName().equalsIgnoreCase(name) || b.getAddress().equalsIgnoreCase(address)));
                        if (hasDuplicate) {
                            // Show an error message if there is a duplicate
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Trùng địa chỉ");
                            alert.setHeaderText("Trùng tên hoặc địa chỉ chi nhánh ");
                            alert.showAndWait();
                            textFieldName.requestFocus();
                        } else {
                            branch.setBranchName(name);
                            branch.setAddress(address);
                            try {
                                branchService.updateBranch(branch);
                                tableBranch.refresh();
                                updateCommitOrCancel();
                                editButton.setOnAction(originalEditEvent);
                                deleteButton.setOnAction(originalDeleteEvent);
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Cập nhật thành công");
                                alert.setContentText("Cập nhật thành công");
                                alert.showAndWait();
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLBranchManagerController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                });
            });
            deleteButton.setOnAction(event -> {
                // Get the selected branch and confirm deletion
                Branch branch = getTableView().getItems().get(getIndex());
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận xóa");
                alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
                alert.setContentText(branch.getBranchName() + " sẽ bị xóa vĩnh viễn.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        branchService.deleteBranch(branch.getId());
                        branchTableData.remove(branch);
                    } catch (SQLException ex) {
                        Alert alertSQL = new Alert(AlertType.ERROR);
                        alertSQL.setTitle("Xóa thất bại");
                        alertSQL.setHeaderText("Không thể xóa chi nhánh");
                        alertSQL.setContentText(branch.getBranchName() + " vẫn còn nhân viên hoặc hàng hóa");
                        alertSQL.showAndWait();
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

    @FXML
    void returnMain(ActionEvent event) throws IOException {
        App.setRoot("FXMLAdminMenu", " Manager");
    }
}
