package java_ebook_search.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Controller class that handles the user actions on the Filter window.
 */
public class FiltersController {

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
        okClicked = true;
        dialogStage.close();
    }

    public void cancel() {
        dialogStage.close();
    }
}
