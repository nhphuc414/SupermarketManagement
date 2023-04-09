/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.app;
import com.ktpm.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author ad
 */
public class FXMLAdminMenuController implements Initializable {
    
    
    @FXML
    void handleSignOut(ActionEvent event) throws IOException {
        Alert alert = Utils.getBox("Confirm Sign Out", null, "Are you sure you want to sign out?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            App.setCurrentEmployee(null);
            App.setRoot("FXMLLogin","Login");
        }
    }
    @FXML
    void handleBranchManager(ActionEvent event) throws IOException{
        App.setRoot("FXMLBranchManager", "Branch Manager");
    } 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
