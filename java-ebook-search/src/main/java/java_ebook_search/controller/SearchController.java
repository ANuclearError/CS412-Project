package java_ebook_search.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import com.google.common.io.Files;

import java_ebook_search.model.Book;
import java_ebook_search.model.Filter;
import java_ebook_search.model.Highlighter;
import java_ebook_search.model.Result;
import java_ebook_search.model.Search;
import java_ebook_search.model.SpellCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

	@FXML
	private StatusBar status;

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
	 * Number of results for the current search.
	 */
	private int noOfResults = 0;

	/**
	 * The frequency of terms in the currently selected document.
	 */
	private int termFrequency = 0;

	/**
	 * Status message for number of results.
	 */
	private final String statusResultsText = "Number of results: ";

	/**
	 * Status message for term frequency.
	 */
	private final String statusFrequencyText = "Term frequency: ";
	/**
	 * List of words for auto-completion.
	 */
	private AutoCompletionBinding<String> commonTerms;

	/**
	 * Sets up the GUI, ensuring that everything is loaded nicely.
	 *
	 * @param location
	 * @param resources
	 */
	public void initialize(URL location, ResourceBundle resources) {
		filter = new Filter();
		Set<String> books = new HashSet<>();
		for (Book book : Book.values()) {
			books.add(book.toString());
		}
		filter.setBooks(books);
		results.setItems(listItems);
		String index = "java_ebook_search/index";
		String home = "/java_ebook_search/view/index.html";

		try {
			search = new Search(index);
			commonTerms = TextFields.bindAutoCompletion(query, search.getAutocomplete());
			commonTerms.setVisibleRowCount(10);
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
	 * Adds string to our common search terms
	 * 
	 * @param newWord
	 */
	private void autoCompletionLearnWord(String newWord) {
		search.addTerm(newWord);

		// we dispose the old binding and recreate a new binding
		if (commonTerms != null) {
			commonTerms.dispose();
		}
		commonTerms = TextFields.bindAutoCompletion(query, search.getAutocomplete());
	}

	/**
	 * Highlights a word in an html file an returns the new file. The word comes
	 * from the GUI's query text field. Also updates the term frequency field.
	 * (Cheeky).
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private File highlight(File file) throws IOException {

		File toLoad = null;
		String searchTerm = query.getText().trim();

		// Don't wanna highlight boolean terms
		// TOOD wildcards? Suppose you wouldn't see that syntax in the file
		// anyway...?
		searchTerm = searchTerm.replace(" AND ", " ");
		searchTerm = searchTerm.replace(" OR ", " ");
		searchTerm = searchTerm.replace(" NOT ", " ");

		// If not an empty search
		if (!StringUtils.isEmpty(searchTerm)) {

			String htmlString = Files.toString(file, Charset.defaultCharset());
			Highlighter hl = new Highlighter(searchTerm, htmlString);
			String newhtmlString = hl.getHighlightedHtml();
			termFrequency = hl.getTermFrequency();

			status.setText(statusResultsText + noOfResults + ", " + statusFrequencyText + termFrequency);
			;
			// Write to a hidden temp file.
			String tempFilePath = file.getParent() + "/" + ".highlighted.htm";

			toLoad = new File(tempFilePath);

			FileUtils.writeStringToFile(toLoad, newhtmlString, Charset.defaultCharset());

		} else {
			System.out.println("[ERROR] Empty Search");
		}

		return toLoad;
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
			// tags and files with HTML tags.
			// path = path.replace("src/main/resources", "");
			path = path.replace("indexed_files", "files");

			// Get the HTML file & not the parsed index file
			File htmlFile = new File(path);

			// Lets highlight it
			try {
				htmlFile = highlight(htmlFile);
			} catch (IOException e) {
				System.out.println("[ERROR] Failed to highlight.");
			}

			path = "file:///" + htmlFile.getAbsolutePath();

			// N.B. MUST USE file:/// and NOT get Resource ***
			// otherwise file is loaded from /target/... and because the
			// filename is still .highlighted.htm...
			// it will NOT be updated.
			webEngine.load(path);
			// webEngine.load(getClass().getResource(path).toString());
			webEngine.setUserStyleSheetLocation(null);
		}
	}

	/**
	 * Opens the filter window to enable filters.
	 *
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

			// Set the Filter into the controller.
			FiltersController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			if (null != filter) {
				controller.setFilter(filter);
			}

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			// Set Filters
			this.filter = controller.getFilter();

			// Trigger search
			search();
			return controller.isOkClicked();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Executes search.
	 */
	public void search() throws IOException, ParseException {
		// clear old list
		listItems.clear();
		String term = query.getText();
		autoCompletionLearnWord(query.getText().trim());

		// Get file paths
		try {
			if (!(query.getText().equals("") || query.getText() == null)) {
				List<Result> files = search.search(term, filter);
				if (files.size() == 0) {
					// re-search the query using one of the suggestions
					List<String> suggestions = sc.getSuggestions(term);
					if (suggestions.size() != 0) {
						String suggestion = suggestions.get(0);
						spellCheckAlert(term, suggestion);
						files = search.search(suggestion, filter);
						System.out.println(suggestions);
					} else {
						noResultError(term);
					}
				}
				noOfResults = search.getResults();
				status.setText(statusResultsText + noOfResults);

				// Add results to list, displaying on GUI.
				int i = 1;
				for (Result file : files) {
					file.setData(i);
					listItems.add(file);
					i++;
				}
			}
		} catch (ParseException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Parse Exception");
			alert.setHeaderText("Cannot parse query.");
			alert.setContentText("Your query seems to be an invalid boolean query.");
			alert.showAndWait();
		}
	}

	/**
	 * Displays alert that the user's temr is being changed to suggestion.
	 *
	 * @param term
	 *            - original query.
	 * @param suggestion
	 *            - suggested query.
	 */
	private void spellCheckAlert(String term, String suggestion) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("No results found");
		alert.setHeaderText("0 Results found for " + term + ".");
		alert.setContentText("Trying " + suggestion + " instead.");
		alert.showAndWait();
	}

	/**
	 * Displays alert when there are no possible results.
	 *
	 * @param term
	 *            - term that had no results.
	 */
	private void noResultError(String term) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("No results found");
		alert.setHeaderText("0 Results found for " + term + ".");
		alert.setContentText("Try a different spelling or something.");
		alert.showAndWait();
	}
}