package java_ebook_search.controller;

import java_ebook_search.model.SpellCheck;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The SearchController class acts as the controller for actions perofrmed in
 * the search view.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class SearchController implements Initializable {

    @FXML
    private ListView<String> results;

    @FXML
    private TextField query;

    @FXML
    private WebView webView;

    /**
     * Handles spell checking of search queries.
     */
    private SpellCheck spellCheck;

    private List<String> suggestions;

    /**
     * Web Engine that renders the web pages.
     */
    private WebEngine webEngine;

    public void initialize(URL location, ResourceBundle resources) {

        suggestions = new ArrayList<String>();

        spellCheck = new SpellCheck();

        String home = "/java_ebook_search/files/index.htm";
        String css = "/java_ebook_search/html/style.css";

        try {
            webEngine = webView.getEngine();
            webEngine.load(getClass().getResource(home).toString());
            String cssLoc = getClass().getResource(css).toString();
            webEngine.setUserStyleSheetLocation(cssLoc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        results.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> loadResult(newValue)
        );
    }

    public void loadResult(String result) {
        webEngine.load(result);
    }

    /**
     * Opens the filter window to enable filters.
     *
     * TODO: Create filters window.
     */
    public void filters() {
        System.out.println("Filters");
    }

    /**
     * Executes search.
     *
     * TODO: Implement search.
     */
    public void search() throws IOException {
        String term = query.getText();
        System.out.println("Search: " + term);
    }

}
