package java_ebook_search.controller;

import java.util.HashSet;
import java.util.Set;

import java_ebook_search.model.Book;
import java_ebook_search.model.Filter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * Controller class that handles the user actions on the Filter window.
 */
public class FiltersController {

	@FXML
	private CheckBox javanut, langref, awt, fclass, exp, content, title;

	/**
	 * Window of filters.
	 */
	private Stage dialogStage;

	/**
	 * User has confirmed changes to filters.
	 */
	private boolean okClicked = false;

	/**
	 * The filter that is to be used for searches.
	 */
	private Filter filter;

	public FiltersController() {
		filter = new Filter();
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Returns whether or not user confirmed changes to filters.
	 * 
	 * @return okClicked
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * The user wishes to update the filters, so we must change the filters to
	 * reflect that.
	 */
	public void confirm() {
		Set<String> books = new HashSet<>();
		if (javanut.isSelected())
			books.add(Book.JAVANUT.toString());
		if (langref.isSelected())
			books.add(Book.LANGREF.toString());
		if (awt.isSelected())
			books.add(Book.AWT.toString());
		if (fclass.isSelected())
			books.add(Book.FCLASS.toString());
		if (exp.isSelected())
			books.add(Book.EXP.toString());

		filter.setSearchContent(content.isSelected());
		filter.setSearchTitle(title.isSelected());

		if (books.isEmpty() || !(content.isSelected() || title.isSelected())) {
			filterError();
		} else {
			okClicked = true;
			filter.setBooks(books);
			dialogStage.close();
		}
	}

	/**
	 * Displays an error alert if the user submits a filter that will not
	 * retrieve any results.
	 */
	private void filterError() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Filter Error");
		alert.setHeaderText("Filter is invalid");
		alert.setContentText("This filter will retrieve 0 results. Please fix!");

		alert.showAndWait();
	}

	/**
	 * The user does not wish to confirm changes.
	 */
	public void cancel() {
		dialogStage.close();
	}

	/**
	 * Return the filter
	 *
	 * @return
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Set the filter and update the checkboxes
	 *
	 * @param filter
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
		updateCheckBoxes();
	}

	/**
	 * Set Selected on all checkboxes
	 *
	 * @param b
	 *            - setting for all
	 */
	private void checkAll(boolean b) {
		javanut.setSelected(b);
		langref.setSelected(b);
		exp.setSelected(b);
		fclass.setSelected(b);
		awt.setSelected(b);
		title.setSelected(b);
		content.setSelected(b);
	}

	/**
	 * Update checkboxes
	 */
	private void updateCheckBoxes() {
		checkAll(false);

		for (String book : filter.getBooks()) {

			if (book.equals(Book.JAVANUT.toString())) {
				javanut.setSelected(true);
			}
			if (book.equals(Book.LANGREF.toString())) {
				langref.setSelected(true);
			}
			if (book.equals(Book.EXP.toString())) {
				exp.setSelected(true);
			}
			if (book.equals(Book.FCLASS.toString())) {
				fclass.setSelected(true);
			}
			if (book.equals(Book.AWT.toString())) {
				awt.setSelected(true);
			}
		}

		content.setSelected(filter.isSearchContent());
		title.setSelected(filter.isSearchTitle());
	}
}
