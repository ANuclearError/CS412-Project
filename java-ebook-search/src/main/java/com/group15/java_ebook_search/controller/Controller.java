package com.group15.java_ebook_search.controller;

import com.group15.java_ebook_search.model.SpellCheck;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The Controller class handles the GUI events, calling the methods that will
 * interact with Lucene.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Controller implements Initializable {

    @FXML
    private TextField query;

    @FXML
    private WebView webView;

    private SpellCheck spellCheck;

    /**
     * Web Engine that renders the web pages.
     */
    private WebEngine webEngine;

    public void initialize(URL location, ResourceBundle resources) {

        spellCheck = new SpellCheck();

        String home = "/com/group15/java_ebook_search/files/index.htm";
        String css = "/com/group15/java_ebook_search/style/html/style.css";

        try {
            webEngine = webView.getEngine();
            webEngine.load(getClass().getResource(home).toString());
            String cssLoc = getClass().getResource(css).toString();
            webEngine.setUserStyleSheetLocation(cssLoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays about info.
     *
     * TODO: Create about window.
     */
    public void about() {
        System.out.println("About");
    }

    /**
     * Quits the system.
     */
    public void exit() {
        System.exit(0);
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
        System.out.println(spellCheck.getSuggestions(term));
    }
}
