package java_ebook_search.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import java_ebook_search.MyFile;
import java_ebook_search.model.Search;
import java_ebook_search.model.SpellCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * The SearchController class acts as the controller for actions perofrmed in
 * the search view.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class SearchController implements Initializable {

	@FXML
	private ListView<File> results;

	@FXML
	private TextField query;

	@FXML
	private WebView webView;

	/**
	 * Web Engine that renders the web pages.
	 */
	private WebEngine webEngine;

	/**
	 * Handles spell checking of search queries.
	 */
	private SpellCheck spellCheck;

	private List<String> suggestions;

	private Search search;

	final ObservableList<File> listItems = FXCollections.observableArrayList();

	public void initialize(URL location, ResourceBundle resources) {

		results.setItems(listItems);

		suggestions = new ArrayList<>();
		spellCheck = new SpellCheck();

		String index = "index";
		String home = "/java_ebook_search/html/index.html";
		String css = "/java_ebook_search/html/style.css";

		try {
			search = new Search(index);
			webEngine = webView.getEngine();
			webEngine.load(getClass().getResource(home).toString());
			String cssLoc = getClass().getResource(css).toString();
			webEngine.setUserStyleSheetLocation(cssLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		results.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				File clickedOn = results.getSelectionModel().getSelectedItem();
				
				//if not clicked on null
				if (null != clickedOn ) {
					System.out.println("clicked on " + clickedOn);
					
					String url = "file://" + clickedOn.getAbsolutePath();
					webEngine.load(url);

				}
			}
		});

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
	public void search() throws IOException, ParseException {

		// clear old list
		listItems.clear();

		String term = query.getText();
		System.out.println("Search Term = " + term);

		// Get file paths
		List<MyFile> files = search.search(term);
		int i = 1;
		for (MyFile file : files) {
			file.setData(i);
			listItems.add(file);
			i++;
		}

	}


}