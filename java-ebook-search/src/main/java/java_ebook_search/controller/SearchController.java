package java_ebook_search.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.lucene.queryparser.classic.ParseException;

import java_ebook_search.model.MyFile;
import java_ebook_search.model.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	 * The search engine the user interacts with.
	 */
	private Search search;

	/**
	 * The results obtained from the search.
	 */
	final ObservableList<File> listItems = FXCollections.observableArrayList();

	/**
	 * Sets up the GUI, ensuring that everything is loaded nicely.
	 *
	 * @param location
	 * @param resources
	 */
	public void initialize(URL location, ResourceBundle resources) {
		results.setItems(listItems);

		String index = "java_ebook_search/index";
		String home = "/java_ebook_search/view/index.html";
		String css = "/java_ebook_search/style/style.css";

		try {
			search = new Search(index);
			webEngine = webView.getEngine();
			webEngine.load(getClass().getResource(home).toString());
			String cssLoc = getClass().getResource(css).toString();
			webEngine.setUserStyleSheetLocation(cssLoc);

			results.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> loadResult(newValue)
			);

		} catch (NullPointerException e) {
			System.out.println("It's happened again, ignore it");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Given a file, it loads the web page into the web engine.
	 * @param result - the file loaded.
	 */
	private void loadResult(File result) {
		String path = result.getPath();
		// Need to deal with difference between indexed files without HTML tags
		// and files with HTML tags.
		path = path.replace("src/main/resources", "");
		path = path.replace("indexed_files", "files");
		System.out.println(path);
		
		webEngine.load(getClass().getResource(path).toString());
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