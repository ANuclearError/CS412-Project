package com.group15.java_ebook_search;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Driver class starts the application, loading the GUI and sets up the
 * system.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Driver extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxml = "/com/group15/java_ebook_search/fxml/view.fxml";
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Java eBook Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
