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
    private final ObservableList<Employee> employeeTableData = FXCollections.observableArrayList();
    private final ObservableList<Branch> branchTableData = FXCollections.observableArrayList();

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
        //Load Collumn
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

        TableColumn<Employee, Void> actionColumn = new TableColumn<>("Hành động");
        actionColumn.setCellFactory(param -> new ButtonCell());
        actionColumn.setPrefWidth(300);
        tableEmployee.getColumns().add(actionColumn);

    }

    public void onLoad(){
        loadColumns();
        //Load role
        comboboxRole.getItems().addAll(Employee.EmployeeRole.Employee, Employee.EmployeeRole.Manager);
        comboboxRole.setValue(Employee.EmployeeRole.Employee);
        try {
            //Load branches
            List<Branch> branches = branchService.getAllBranches();
            for (Branch branch : branches) {
                branchTableData.add(new Branch(branch.getId(), branch.getBranchName(), branch.getAddress()));
            }
            //Load employee
            List<Employee> employees = employeeService.getAllEmployees();
            for (Employee employee : employees) {
                employeeTableData.add(employee);
            }
            comboBoxBranch.setItems(branchTableData);
            comboBoxBranch.setValue(branchTableData.get(0));

            tableEmployee.setItems(employeeTableData);
        } catch (SQLException ex){
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
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

    public void loadOnField(Employee employee) {
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
        comboboxRole.setValue(employee.getEmployeeRole());
    }

    public void getEmployeeInField(Employee employee) {
        employee.setBranchId(comboBoxBranch.getValue().getId());
        employee.setEmployeeName(textFieldName.getText().trim());
        employee.setNumber(textFieldNumber.getText().trim());
        employee.setBirthday(Date.valueOf(datePickerBirthday.getValue()));
        employee.setUsername(textFieldUsername.getText().trim());
        employee.setPassword(textFieldPassword.getText().trim());
        employee.setEmployeeRole(comboboxRole.getValue());
    }

    private void resetPassword() {
        textFieldName.requestFocus();
        textFieldPassword.setText("");
        textFieldConfirm.setText("");
    }

    public boolean checkValid(String id) {
        Branch branch = comboBoxBranch.getValue();
        String name = textFieldName.getText().trim();
        String number = textFieldNumber.getText().trim();
        LocalDate birthday = datePickerBirthday.getValue();
        String username = textFieldUsername.getText().trim();
        String password = textFieldPassword.getText().trim();
        String confirmPassword = textFieldConfirm.getText().trim();
        Employee.EmployeeRole role = comboboxRole.getValue();
        if ("".equals(confirmPassword) || "".equals(name) || branch == null || "".equals(number) || "".equals(username) || "".equals(password)) {
            Utils.getBox("Lỗi", "Không đủ thông tin", "Vui lòng nhập đủ thông tin", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
        } else if (birthday == null) {
            Utils.getBox("Lỗi", "Lỗi nhập liệu", "Vui lòng nhập đúng định dạng ngày tháng (MM/dd/yyyy)", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
            datePickerBirthday.requestFocus();
        } else if (username.contains(" ")) {
            Utils.getBox("Lỗi", "Lỗi nhập liệu", "username không nên có khoảng trắng", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
        } else if (!number.matches("[0-9]+")) {
            Utils.getBox("Lỗi", "Lỗi nhập liệu", "Lỗi nhập SDT", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
            textFieldNumber.requestFocus();
        } else if (employeeTableData.
                stream().anyMatch(e -> !e.getId().equals(id) && e.getUsername().equals(username))) {
            Utils.getBox("Lỗi", "Thêm thất bại", "Tài khoản đã có người sử dụng", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
        } else if (employeeTableData.stream().anyMatch(e
                -> !e.getId().equals(id)
                && (e.getEmployeeName().equalsIgnoreCase(name)
                && e.getBirthday().equals(Date.valueOf(birthday))
                && e.getNumber().equals(number))
        )) {
            Utils.getBox("Lỗi", "Thêm thất bại", "Đã có nhân viên này", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
        } else if (!password.equals(confirmPassword)) {
            Utils.getBox("Lỗi", "Nhập sai", "Mật khẩu không trùng", Alert.AlertType.ERROR).showAndWait();
            resetPassword();
        } else {
            return true;
        }
        return false;
    }

    public void addEmployee(ActionEvent event) {
        Employee employee = new Employee();
        if (checkValid("")) {
            getEmployeeInField(employee);
            employee.setJoinDate(Date.valueOf(LocalDate.now()));
            try {
                employeeService.addEmployee(employee);
                employeeTableData.add(employee);
                Utils.getBox("Thành công", "", "Thêm thành công", Alert.AlertType.INFORMATION).showAndWait();
                resetField();
            } catch (SQLException ex) {
                Utils.getBox("Thất bại", "Có lỗi", "Thêm thất bại", Alert.AlertType.ERROR).showAndWait();
                textFieldName.requestFocus();
            }

        }
    }

    private class ButtonCell extends TableCell<Employee, Void> {

        private final Button editButton = new Button("Sửa");
        private final Button deleteButton = new Button("Xóa");
        private EventHandler<ActionEvent> originalEditEvent;
        private EventHandler<ActionEvent> originalDeleteEvent;

        private void setButton(Boolean value) {
            tableEmployee.lookupAll("Button").forEach(node -> {
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
            textFieldUsername.setEditable(true);
        }

        public ButtonCell() {
            editButton.setOnAction(event -> {
                beforeCommit();
                originalEditEvent = editButton.getOnAction();
                originalDeleteEvent = deleteButton.getOnAction();
                Employee employee = getTableView().getItems().get(getIndex());
                loadOnField(employee);

                deleteButton.setOnAction(cancelEvent -> {
                    afterCommitOrCancel();
                });

                editButton.setOnAction(commitEvent -> {
                    if (checkValid(employee.getId())) {
                        getEmployeeInField(employee);
                        try {
                            employeeService.updateEmployee(employee);
                            tableEmployee.refresh();
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
                Employee employee = getTableView().getItems().get(getIndex());
                Alert alert = Utils.getBox("Xác nhận xóa", "Bạn có chắc chắn muốn xóa?", employee.getEmployeeName() + " sẽ bị xóa vĩnh viễn.", Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        employeeService.deleteEmployee(employee.getId());
                        employeeTableData.remove(employee);
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
