package java_ebook_search.controller;

import java_ebook_search.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that handles the user actions on the Filter window.
 */
public class FiltersController {

    @FXML
    private CheckBox javanut, langref, awt, fclass, exp;

    private Stage dialogStage;
    private boolean okClicked = false;

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
        List<String> books = new ArrayList<>();
        if(javanut.isSelected())
            books.add(Book.JAVANUT.toString());
        if(langref.isSelected())
            books.add(Book.LANGREF.toString());
        if(awt.isSelected())
            books.add(Book.AWT.toString());
        if(fclass.isSelected())
            books.add(Book.FCLASS.toString());
        if(exp.isSelected())
            books.add(Book.EXP.toString());
        okClicked = true;
        dialogStage.close();
    }

    public void cancel() {
        dialogStage.close();
    }
}
