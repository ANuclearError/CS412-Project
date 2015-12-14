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

	private boolean searchContent;

	private boolean searchTitle;

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

	public boolean isSearchContent() {
		return searchContent;
	}

	public void setSearchContent(boolean searchContent) {
		this.searchContent = searchContent;
	}

	public boolean isSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(boolean searchTitle) {
		this.searchTitle = searchTitle;
	}

	@Override
	public String toString() {
		return "Filter [books=" + books + "]";
	}

}
