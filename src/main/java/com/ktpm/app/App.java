package com.ktpm.app;

import com.ktpm.pojo.Account;
import com.ktpm.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Account currentAccount;

    public static Account getCurrentAccount() {
        return currentAccount;
    }
    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("FXMLAdminMenu"));
        stage.setScene(scene);
        stage.setTitle("My App");
        stage.setResizable(true);
        stage.setOnCloseRequest(eh ->{
            eh.consume();
            Utils.confirmExit();
        });
        stage.centerOnScreen();
        stage.show();
        
    }
     
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}