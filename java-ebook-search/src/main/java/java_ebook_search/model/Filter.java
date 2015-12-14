package java_ebook_search.model;

import java.util.Set;

/**
 * Class: Filter.java
 * 
 * @Author Kevin Paton
 * @Since 14 Dec 2015
 */

public class Filter {

	/**
	 * Books to filter
	 */
	private Set<String> books;

	/**
	 * Get the filtered book set.
	 * 
	 * @return
	 */
	public Set<String> getBooks() {
		return books;
	}

	/**
	 * Set the filter book set.
	 * 
	 * @param books
	 */
	public void setBooks(Set<String> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Filter [books=" + books + "]";
	}

}
