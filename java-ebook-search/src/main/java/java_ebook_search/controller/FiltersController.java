package java_ebook_search.controller;

import java.util.HashSet;
import java.util.Set;

import java_ebook_search.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * Controller class that handles the user actions on the Filter window.
 */
public class FiltersController {

	@FXML
	private CheckBox javanut, langref, awt, fclass, exp;

	private Stage dialogStage;
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

	public boolean isOkClicked() {
		return okClicked;
	}

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
		okClicked = true;
		dialogStage.close();

		this.books = books;
	}

	public void cancel() {
		dialogStage.close();
	}

	public Set<String> getBooks() {
		return books;
	}

	public void setBooks(Set<String> books) {
		this.books = books;
		updateCheckBoxes();

	}

	/**
	 * Set Selected on all checkboxes
	 *
	 * @param b
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
