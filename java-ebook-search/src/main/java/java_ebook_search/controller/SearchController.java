package java_ebook_search.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java_ebook_search.model.CommonSearchTerms;
import java_ebook_search.model.Filter;
import java_ebook_search.model.Result;
import java_ebook_search.model.Search;
import java_ebook_search.model.SpellCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

	private AutoCompletionBinding<String> autoCompletionBinding;

	@FXML
	private Button searchButton;

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
	 * Adds string to our common search terms
	 * @param newWord
	 */
	private void autoCompletionLearnWord(String newWord) {
		CommonSearchTerms.TERMS.add(newWord);

		// we dispose the old binding and recreate a new binding
		if (autoCompletionBinding != null) {
			autoCompletionBinding.dispose();
		}

		autoCompletionBinding = TextFields.bindAutoCompletion(query, CommonSearchTerms.TERMS);

	}

	/**
	 * Changes The query text field to an autocomplete one. Bit hacky.
	 * 
	 * Adds query to our common search terms once a search occurs.
	 * 
	 */
	private void createAutoCompleteTextField() {

		autoCompletionBinding = TextFields.bindAutoCompletion(query, CommonSearchTerms.TERMS);

		// TODO hardcoded
		autoCompletionBinding.setVisibleRowCount(5);

		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				autoCompletionLearnWord(query.getText().trim());
				try {
					search();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

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
			createAutoCompleteTextField();
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

		if (null != result) {

			String path = result.getPath();
			// Need to deal with difference between indexed files without HTML
			// tags
			// and files with HTML tags.
			path = path.replace("src/main/resources", "");
			path = path.replace("indexed_files", "files");
			System.out.println(path);
			webEngine.load(getClass().getResource(path).toString());
			webEngine.setUserStyleSheetLocation(null);
		}
	}

	/**
	 * Opens the filter window to enable filters.
	 * <p>
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
				controller.setFilter(filter);

			}
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			// Set Filters
			this.filter = controller.getFilter();

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
	private List<Result> filterResults(List<Result> files) {
		// No filter, do nothing
		if (null == filter) {
			return files;
		}

		// New filtered List to return
		List<Result> toReturn = new ArrayList<Result>();

		// If passed in a null list of files, do nothing
		if (!CollectionUtils.isEmpty(files)) {
			// Loop through each file
			for (Result file : files) {
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
		List<Result> files = search.search(term);
		files = filterResults(files);
		if (files.size() == 0) {
			// re-search the query using one of the suggestions
			List<String> suggestions = sc.getSuggestions(term);
			if (suggestions.size() != 0) {
				files = search.search((suggestions.get(0)));
				System.out.println(suggestions);
			}
		}

		// Add results to list, displaying on GUI.
		int i = 1;
		for (Result file : files) {
			file.setData(i);
			listItems.add(file);
			i++;
		}
	}
}