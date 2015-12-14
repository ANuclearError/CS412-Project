package java_ebook_search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
* Class: HelloWorld.java
* @Author Kevin Paton
* @Since 14 Dec 2015
*/

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {

	private AutoCompletionBinding<String> autoCompletionBinding;
	private String[] _possibleSuggestions = { "Hey", "Hello", "Hello World", "Apple", "Cool", "Costa", "Cola",
			"Coca Cola" };
	private Set<String> possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestions));

	private TextField learningTextField;

	@Override
	public void start(Stage primaryStage) {

		StackPane root = new StackPane();

		Scene scene = new Scene(root, 300, 250);

		//
		// TextField with learning auto-complete functionality
		// Learn the word when user presses ENTER
		//
		learningTextField = new TextField();
		autoCompletionBinding = TextFields.bindAutoCompletion(learningTextField, possibleSuggestions);
		learningTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				switch (ke.getCode()) {
				case ENTER:
					autoCompletionLearnWord(learningTextField.getText().trim());
					break;
				default:
					break;
				}
			}
		});

		root.getChildren().add(learningTextField);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void autoCompletionLearnWord(String newWord) {
		possibleSuggestions.add(newWord);

		// we dispose the old binding and recreate a new binding
		if (autoCompletionBinding != null) {
			autoCompletionBinding.dispose();
		}
		autoCompletionBinding = TextFields.bindAutoCompletion(learningTextField, possibleSuggestions);
	}

	public static void main(String[] args) {
		launch(args);
	}
}