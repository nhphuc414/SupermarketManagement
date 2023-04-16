/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.pojo.Employee;
import com.ktpm.services.BranchService;
import com.ktpm.services.EmployeeService;
import com.ktpm.services.impl.BranchServiceImpl;
import com.ktpm.services.impl.EmployeeServiceImpl;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
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
public class FXMLEmployeeManagerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private SearchableComboBox<Branch> comboBoxBranch;

    @FXML
    private ComboBox<Employee.EmployeeRole> comboboxRole;

    @FXML
    private DatePicker datePickerBirthday;

    @FXML
    private TableView<Employee> tableEmployee;

    @FXML
    private PasswordField textFieldConfirm;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldNumber;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUsername;

    final private BranchService branchService = new BranchServiceImpl();
    final private EmployeeService employeeService = new EmployeeServiceImpl();
    private ObservableList<Employee> employeeTableData = FXCollections.observableArrayList();
    private ObservableList<Branch> branchTableData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            onLoad();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEmployeeManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void returnMain(ActionEvent event) throws IOException {
        App.setRoot("FXMLAdminMenu", " Manager");
    }

    public void loadBranchAndRole() throws SQLException {
        List<Branch> branches = branchService.getAllBranches();
        for (Branch branch : branches) {
            branchTableData.add(new Branch(branch.getId(), branch.getBranchName(), branch.getAddress()));
        }
        comboBoxBranch.setItems(branchTableData);
        comboBoxBranch.setValue(branchTableData.get(0));
        comboboxRole.getItems().addAll(Employee.EmployeeRole.Employee, Employee.EmployeeRole.Manager);
        comboboxRole.setValue(Employee.EmployeeRole.Employee);
    }

    public void loadEmployee() throws SQLException {
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee employee : employees) {
            employeeTableData.add(employee);
        }
    }

    public void onLoad() throws SQLException {
        loadBranchAndRole();
        loadEmployee();
        TableColumn<Employee, ?> nameColumn = new TableColumn<>("Họ tên");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        TableColumn<Employee, ?> numberColumn = new TableColumn<>("Số điện thoại");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Employee, ?> dateColumn = new TableColumn<>("Ngày sinh");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        TableColumn<Employee, ?> joinColumn = new TableColumn<>("Ngày gia nhập");
        joinColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

        TableColumn<Employee, ?> usernameColumn = new TableColumn<>("Tài khoản");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Employee, ?> roleColumn = new TableColumn<>("Chức vụ");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("employeeRole"));

        TableColumn<Employee, ?> branchColumn = new TableColumn<>("Chi nhánh");
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        tableEmployee.getColumns().addAll(nameColumn, numberColumn, dateColumn, joinColumn, usernameColumn, roleColumn, branchColumn);
        tableEmployee.setItems(employeeTableData);

        TableColumn<Employee, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);
        tableEmployee.getColumns().add(actionColumn);
    }

    public boolean test(String id, Branch branch, String name, String number, LocalDate birthday, String username, String password, String confirmPassword) {
        if ("".equals(confirmPassword) || "".equals(name) || branch == null || "".equals(number) || "".equals(username) || "".equals(password)) {
            Alert alert = Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng nhập đủ thông tin", Alert.AlertType.ERROR);
            textFieldName.requestFocus();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            alert.showAndWait();
        } else if (birthday == null) {
            Alert alert = Utils.getBox("Lỗi", "Lỗi nhập liệu", "Vui lòng nhập đúng định dạng ngày tháng (MM/dd/yyyy)", Alert.AlertType.ERROR);
            datePickerBirthday.requestFocus();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            alert.showAndWait();
        } else if (username.contains(" ")) {
            Alert alert = Utils.getBox("Lỗi", "Lỗi nhập liệu", "username không nên có khoảng trắng", Alert.AlertType.ERROR);
            textFieldUsername.requestFocus();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            alert.showAndWait();
        } else if (!number.matches("[0-9]+")) {
            Alert alert = Utils.getBox("Lỗi", "Lỗi nhập liệu", "Lỗi nhập SDT", Alert.AlertType.ERROR);
            textFieldNumber.requestFocus();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            alert.showAndWait();
        } else if (employeeTableData.
                stream().anyMatch(e -> !e.getId().equals(id) && e.getUsername().equals(username))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Tài khoản đã có người sử dụng");
            alert.showAndWait();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            textFieldName.requestFocus();
        } else if (employeeTableData.stream().anyMatch(e
                -> !e.getId().equals(id)
                && (e.getEmployeeName().equalsIgnoreCase(name)
                && e.getBirthday().equals(Date.valueOf(birthday))
                && e.getNumber().equals(number))
        )) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Đã có nhân viên này");
            alert.showAndWait();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            textFieldName.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nhập sai");
            alert.setHeaderText("Mật khẩu không trùng");
            alert.showAndWait();
            textFieldPassword.setText("");
            textFieldConfirm.setText("");
            textFieldNumber.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    public void resetField() {
        comboBoxBranch.setValue(branchTableData.get(0));
        textFieldName.setText("");
        textFieldNumber.setText("");
        textFieldUsername.setText("");
        textFieldPassword.setText("");
        textFieldConfirm.setText("");
        datePickerBirthday.setValue(null);
        textFieldName.requestFocus();
        btnAdd.setDisable(false);
    }
    public void addEmployee(ActionEvent event) throws SQLException {
        Branch branch = comboBoxBranch.getValue();
        String name = textFieldName.getText().trim();
        String number = textFieldNumber.getText().trim();
        LocalDate birthday = datePickerBirthday.getValue();
        String username = textFieldUsername.getText().trim();
        String password = textFieldPassword.getText().trim();
        String confirmPassword = textFieldConfirm.getText().trim();
        Employee.EmployeeRole role = comboboxRole.getValue();
        if (test("", branch, name, number, birthday, username, password, confirmPassword)) {
            Employee employee = new Employee(name, number, Date.valueOf(birthday), Date.valueOf(LocalDate.now()), username, password, role, branch.getId());
            employeeTableData.add(employee);
            employeeService.addEmployee(employee);
            resetField();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thêm thành công");
            alert.setContentText("Thêm thành công");
            alert.showAndWait();
        }

    }

    private class ButtonCell extends TableCell<Employee, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");

        private void setCommit(Employee employee) throws SQLException {
            editButton.setText("Cập nhật");
            deleteButton.setText("Hủy bỏ");
            for (Branch branch : branchTableData) {
                if (branch.getId().equals(employee.getBranchId())) {
                    comboBoxBranch.setValue(branch);
                    break;
                }
            }
            textFieldName.setText(employee.getEmployeeName());
            textFieldNumber.setText(employee.getNumber());
            textFieldUsername.setText(employee.getUsername());
            textFieldUsername.setEditable(false);
            datePickerBirthday.setValue(LocalDate.parse(employee.getBirthday().toString()));
            btnAdd.setDisable(true);
        }

        public ButtonCell() {
            editButton.setOnAction(event -> {
                Employee employee = getTableView().getItems().get(getIndex());
                try {
                    setCommit(employee);
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLEmployeeManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                EventHandler<ActionEvent> originalEditEvent = editButton.getOnAction();
                EventHandler<ActionEvent> originalDeleteEvent = deleteButton.getOnAction();
                deleteButton.setOnAction(cancelEvent -> {
                    editButton.setText("Sửa");
                    deleteButton.setText("Xóa");
                    editButton.setOnAction(originalEditEvent);
                    deleteButton.setOnAction(originalDeleteEvent);
                    resetField();
                    textFieldUsername.setEditable(true);
                });
                editButton.setOnAction(commitEvent -> {
                    Branch branch = comboBoxBranch.getValue();
                    String name = textFieldName.getText().trim();
                    String number = textFieldNumber.getText().trim();
                    LocalDate birthday = datePickerBirthday.getValue();
                    String password = textFieldPassword.getText().trim();
                    String confirmPassword = textFieldConfirm.getText().trim();
                    
                    if (test(employee.getId(), branch, name, number, birthday, employee.getUsername(), password, confirmPassword)) {
                        try {
                            employee.setBranchId(branch.getId());
                            employee.setEmployeeName(name);
                            employee.setNumber(number);
                            employee.setBirthday(Date.valueOf(birthday));
                            employee.setPassword(password);
                            employee.setEmployeeRole(comboboxRole.getValue());
                            employeeService.updateEmployee(employee);
                            tableEmployee.refresh();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Cập nhật thành công");
                            alert.setContentText("Cập nhật thành công");
                            alert.showAndWait();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLEmployeeManagerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        editButton.setText("Sửa");
                        deleteButton.setText("Xóa");
                        editButton.setOnAction(originalEditEvent);
                        deleteButton.setOnAction(originalDeleteEvent);
                        resetField();
                        textFieldUsername.setEditable(true);
                    }

                });
            });
            deleteButton.setOnAction(event -> {
                Employee employee = getTableView().getItems().get(getIndex());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận xóa");
                alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
                alert.setContentText(employee.getEmployeeName() + " sẽ bị xóa vĩnh viễn.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        employeeService.deleteEmployee(employee.getId());
                        employeeTableData.remove(employee);
                    } catch (SQLException ex) {
                        Alert alertSQL = new Alert(Alert.AlertType.ERROR);
                        alertSQL.setTitle("Xóa thất bại");
                        alertSQL.setHeaderText("Không thể xóa nhân viên");
                        alertSQL.setContentText("Lỗi không thể xóa nhân viên");
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
}
