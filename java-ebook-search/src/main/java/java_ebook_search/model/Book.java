package java_ebook_search.model;

/**
 * Enum for storing book titles.
 */
public enum Book {
	JAVANUT("Java in a Nutshell"), LANGREF("Java Language Reference"), AWT("Java AWT Reference"), FCLASS(
			"Java Fundamental Classes Reference"), EXP("Exploring Java");

	private String title;

	Book(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
