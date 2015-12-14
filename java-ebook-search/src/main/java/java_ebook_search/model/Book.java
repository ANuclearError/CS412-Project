package java_ebook_search.model;

/**
 * Enum for storing book titles.
 *
 * @author Aidan O'Grady
 * @since 0.4?
 */
public enum Book {
	JAVANUT("Java in a Nutshell"),
	LANGREF("Java Language Reference"),
	AWT("Java AWT Reference"),
	FCLASS("Java Fundamental Classes Reference"),
	EXP("Exploring Java");

	/**
	 * The title of the book, used for enum's toString.
	 */
	private String title;

	/**
	 * Constructor for the enums
	 * @param title - the title of the book.
	 */
	Book(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
