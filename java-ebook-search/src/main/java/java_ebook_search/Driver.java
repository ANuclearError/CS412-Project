package java_ebook_search;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Driver class starts the application, loading the GUI and sets up the
 * system.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Driver extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Java eBook Search");

        initRootLayout();
        showSearchView();
    }

    /**
     * Loads the root layout containing the menu bar and the general structure.
     */
    private void initRootLayout() {
        String fxml = "/java_ebook_search/fxml/RootLayout.fxml";
        try {
            rootLayout = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the search view and injects it into the root layout.
     */
    private void showSearchView() {
        String fxml = "/java_ebook_search/fxml/SearchView.fxml";
        try {
            VBox searchView;
            searchView = FXMLLoader.load(getClass().getResource(fxml));
            rootLayout.setCenter(searchView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}