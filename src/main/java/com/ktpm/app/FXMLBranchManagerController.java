/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;

import com.ktpm.pojo.Branch;
import com.ktpm.services.BranchService;
import com.ktpm.services.impl.BranchServiceImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLBranchManagerController implements Initializable {

    @FXML
    private TableView tableBranch;
    
    
    @FXML
    private TextField txtAddress;
    

    @FXML
    private TextField txtName;

    @FXML
    private Label lbName;

    /**
     * Initializes the controller class.
     *
     * @throws java.sql.SQLException
     */
    public void loadTableColums() throws SQLException{
        BranchService branchService = new BranchServiceImpl();
        ObservableList<Branch> brancherTableData = FXCollections.observableArrayList();
        List<Branch> branches = branchService.getAllBranches();
        for (Branch branch : branches) {
            brancherTableData.add(new Branch(branch.getId(), branch.getBranchName(), branch.getAddress()));
        }
        
    }
    public void loadBranches() throws SQLException {
        BranchService branchService = new BranchServiceImpl();
        ObservableList<Branch> brancherTableData = FXCollections.observableArrayList();
        List<Branch> branches = branchService.getAllBranches();
        for (Branch branch : branches) {
            brancherTableData.add(new Branch(branch.getId(), branch.getBranchName(), branch.getAddress()));
        }
        TableColumn<Branch, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        TableColumn<Branch, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableBranch.getColumns().addAll(nameColumn, addressColumn);
        tableBranch.setItems(brancherTableData);
    }

    public void loadOnField(MouseEvent event) {
        Branch selectedBranch = (Branch) tableBranch.getSelectionModel().getSelectedItem();
        if (selectedBranch != null) {
            txtName.setText(selectedBranch.getBranchName());
            txtAddress.setText(selectedBranch.getAddress());

            // ...
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            loadBranches();
            lbName.setText(App.getCurrentEmployee().getEmployeeName());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBranchManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
