package java_ebook_search.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import java_ebook_search.model.Filter;
import java_ebook_search.model.MyFile;
import java_ebook_search.model.Search;
import java_ebook_search.model.SpellCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The SearchController class acts as the controller for actions perofrmed in
 * the search view.
 *
 * @author Aidan O'Grady
 * @since 0.1
 */
public class SearchController implements Initializable {

	@FXML
	private VBox searchView;

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
	 * Spell checker for offering suggestions to user.
	 */
	private SpellCheck sc;

	/**
	 * Filters to include in search.
	 */
	private Filter filter;

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

		try {
			search = new Search(index);
			sc = new SpellCheck();
			webEngine = webView.getEngine();
			webEngine.load(getClass().getResource(home).toString());
			results.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> loadResult(newValue));
		} catch (NullPointerException e) {
			System.out.println("It's happened again, ignore it");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Given a file, it loads the web page into the web engine.
	 * 
	 * @param result
	 *            - the file loaded.
	 */
	private void loadResult(File result) {
		String path = result.getPath();
		// Need to deal with difference between indexed files without HTML tags
		// and files with HTML tags.
		path = path.replace("src/main/resources", "");
		path = path.replace("indexed_files", "files");
		System.out.println(path);
		webEngine.load(getClass().getResource(path).toString());
		webEngine.setUserStyleSheetLocation(null);
	}

	/**
	 * Opens the filter window to enable filters.
	 *
	 * TODO: Create filters window.
	 */
	public boolean filters() {
		try {
			String filters = "/java_ebook_search/view/FiltersView.fxml";
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(filters));
			AnchorPane page = loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Filters");
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.setResizable(false);
			dialogStage.initOwner(searchView.getParent().getScene().getWindow());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			FiltersController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			if (null != filter) {
				if (null != filter.getBooks()) {
					controller.setBooks(filter.getBooks());
				}
			}
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			// Set Filters
			this.filter = new Filter();
			this.filter.setBooks(controller.getBooks());

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Filter files
	 * 
	 * @param files
	 * @return
	 */
	private List<MyFile> filterResults(List<MyFile> files) {
		// No filter, do nothing
		if (null == filter) {
			return files;
		}

		// New filtered List to return
		List<MyFile> toReturn = new ArrayList<MyFile>();

		// If passed in a null list of files, do nothing
		if (!CollectionUtils.isEmpty(files)) {
			// Loop through each file
			for (MyFile file : files) {
				// If is in filtered list add to filtered results
				// "toReturn"
				if (filter.getBooks().contains(file.getBook())) {
					toReturn.add(file);
				}
			}
		}
		return toReturn;
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
		files = filterResults(files);
		if(files.size() == 0){
			//re-search the query using one of the suggestions
			List<String> suggestions = sc.getSuggestions(term);
			files = search.search((suggestions.get(0)));
			System.out.println(suggestions);
		}

		// Add results to list, displaying on GUI.
		int i = 1;
		for (MyFile file : files) {
			file.setData(i);
			listItems.add(file);
			i++;
		}
	}
}