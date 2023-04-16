package com.ktpm.app;

import com.ktpm.pojo.Employee;
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
    private static Employee currentEmployee;
    public static Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public static void setCurrentEmployee(Employee employee) {
        currentEmployee = employee;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("FXMLProductManager"));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setOnCloseRequest(eh -> {
            eh.consume();
            Utils.confirmExit();
        });
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    static void setRoot(String fxml,String title) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.sizeToScene();
        currentStage.setTitle(title);
        currentStage.centerOnScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
