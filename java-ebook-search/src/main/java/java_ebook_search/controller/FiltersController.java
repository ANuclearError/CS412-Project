package java_ebook_search.controller;

import java.util.HashSet;
import java.util.Set;

import java_ebook_search.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Controller class that handles the user actions on the Filter window.
 */
public class FiltersController {

	@FXML
	private CheckBox javanut, langref, awt, fclass, exp;

	@FXML
	private RadioButton both, content, title;

	/**
	 * Window of filters.
	 */
	private Stage dialogStage;

	/**
	 * User has confirmed changes to filters.
	 */
	private boolean okClicked = false;

	/**
	 * Set of books
	 */
	private Set<String> books;

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
		Set<String> books = new HashSet<String>();
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

		if(both.isSelected() || content.isSelected()) {
			System.out.println("Searching content");
		}
		if(both.isSelected() || title.isSelected()) {
			System.out.println("Searching title");
		}

		okClicked = true;
		this.books = books;
		dialogStage.close();
	}

	/**
	 * The user does not wish to confirm changes.
	 */
	public void cancel() {
		dialogStage.close();
	}

	/**
	 * Returns the books the user wishes to filter.
	 * @return books
	 */
	public Set<String> getBooks() {
		return books;
	}

	/**
	 * Sets the books to those currently filtered
	 * @param books - current filter.
	 */
	public void setBooks(Set<String> books) {
		this.books = books;
		updateCheckBoxes();
	}

	/**
	 * Set Selected on all checkboxes
	 *
	 * @param b - setting for all
	 */
	private void checkAll(boolean b) {
		javanut.setSelected(b);
		langref.setSelected(b);
		exp.setSelected(b);
		fclass.setSelected(b);
		awt.setSelected(b);
	}

	/**
	 * Update checkboxes
	 */
	private void updateCheckBoxes() {
		checkAll(false);
		for (String book : books) {
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
	}
}
