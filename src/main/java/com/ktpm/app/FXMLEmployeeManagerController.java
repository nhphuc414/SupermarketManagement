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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.DELETE;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLEmployeeManagerController implements Initializable {

    @FXML
    private Label labelName;
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReturn;

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

    private final BranchService branchService = new BranchServiceImpl();
    private final EmployeeService employeeService = new EmployeeServiceImpl();
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

    private EventHandler<KeyEvent> addEnterEvent(Button add, Button esc) {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(comboBoxBranch);
        nodeList.add(comboboxRole);
        nodeList.add(datePickerBirthday);
        nodeList.add(textFieldConfirm);
        nodeList.add(textFieldNumber);
        nodeList.add(textFieldPassword);
        nodeList.add(textFieldUsername);
        nodeList.add(textFieldName);
        nodeList.add(tableEmployee);
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
                    case DELETE:
                        event.consume();
                        btnDelete.fire();
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
        nodeList.add(comboBoxBranch);
        nodeList.add(comboboxRole);
        nodeList.add(datePickerBirthday);
        nodeList.add(textFieldConfirm);
        nodeList.add(textFieldNumber);
        nodeList.add(textFieldPassword);
        nodeList.add(textFieldUsername);
        nodeList.add(textFieldName);
        nodeList.add(tableEmployee);
        for (Node node : nodeList) {
            node.removeEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
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

    public void onLoad() {
        loadColumns();
        labelName.setText(App.getCurrentEmployee().getEmployeeName());
        addEnterEvent(btnAdd, btnReturn);
        //Load role
        comboboxRole.getItems().addAll(Employee.EmployeeRole.Employee, Employee.EmployeeRole.Manager);
        comboboxRole.setValue(Employee.EmployeeRole.Employee);
        try {
            //Load branches
            List<Branch> branches = branchService.getAllBranches();
            branchTableData.addAll(branches);
            //Load employee
            List<Employee> employees = employeeService.getAllEmployees();
            employeeTableData.addAll(employees);

            comboBoxBranch.setItems(branchTableData);
            tableEmployee.setItems(employeeTableData);
        } catch (SQLException ex) {
            Utils.getBox("Lỗi kết nối cơ sở dữ liệu", "", ex.getMessage(), Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void resetField() {
        comboBoxBranch.setValue(null);
        textFieldName.setText("");
        textFieldNumber.setText("");
        textFieldUsername.setText("");
        textFieldPassword.setText("");
        textFieldConfirm.setText("");
        datePickerBirthday.setValue(null);
        comboBoxBranch.requestFocus();
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
        textFieldPassword.setText(employee.getPassword());
        textFieldConfirm.setText(employee.getPassword());
        textFieldPassword.setDisable(true);
        textFieldConfirm.setDisable(true);
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
        if (Utils.checkEmpty(branch)
                && Utils.checkName(name)
                && Utils.checkNumber(number)
                && Utils.checkBirthday(birthday)
                && Utils.checkEmpty(username)
                && Utils.checkEmpty(password)
                && Utils.checkEmpty(confirmPassword)) {

            if (!username.matches("^[a-zA-Z0-9_]+$")) {
                Utils.getBox("Lỗi", "Lỗi nhập liệu", "username nên chỉ bao gồm các ký tự a-z, A-Z, 0-9 và dấu gạch dưới", Alert.AlertType.ERROR).showAndWait();
                resetPassword();
                return false;
            } else if (employeeTableData.
                    stream().anyMatch(e -> !e.getId().equals(id) && e.getUsername().equals(username))) {
                Utils.getBox("Lỗi", "Thêm thất bại", "Tài khoản đã có người sử dụng", Alert.AlertType.ERROR).showAndWait();
                resetPassword();
                return false;
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
                return false;
            } else if (!password.matches("^(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,}$")) {
                Utils.getBox("Lỗi", "Nhập sai", "Mật khẩu có độ dài tối thiểu là 8 ký tự, chứa ít nhất một chữ cái viết thường và một số", Alert.AlertType.ERROR).showAndWait();
                resetPassword();
                return false;
            }
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
        private EventHandler<KeyEvent> eventEnter;

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
            btnReturn.setDisable(true);
            eventEnter = addEnterEvent(this.editButton, this.deleteButton);
            setButton(true);
            comboBoxBranch.requestFocus();
        }

        private void afterCommitOrCancel() {
            resetField();
            setButton(false);
            removeEnterEvent(eventEnter);
            btnAdd.setDisable(false);
            btnReturn.setDisable(false);
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
                            textFieldPassword.setDisable(false);
                            textFieldConfirm.setDisable(false);
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

    public void returnMain(ActionEvent event) {
        try {
            App.setRoot("FXMLAdminMenu", " Manager");
        } catch (IOException io) {
            Utils.getBox("Thất bại", "", "Không thể chuyển trang", Alert.AlertType.ERROR).showAndWait();
        }
    }
}
