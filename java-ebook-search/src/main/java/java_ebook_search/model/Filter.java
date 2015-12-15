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
	 * Should the content of files be searched?
	 */
	private boolean searchContent = true;

	/**
	 * Should the title of pages be searched?
	 */
	private boolean searchTitle = true;

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

	/**
	 * Returns whether to search content or not.
	 * @return searchContent
	 */
	public boolean isSearchContent() {
		return searchContent;
	}

	/**
	 * Sets whether or not to search content.
	 * @param searchContent - do we?
	 */
	public void setSearchContent(boolean searchContent) {
		this.searchContent = searchContent;
	}

	/**
	 * Returns whether to search titles or not.
	 * @return searchTitles
	 */
	public boolean isSearchTitle() {
		return searchTitle;
	}

	/**
	 * Sets whether or not to search titles.
	 * @param searchTitle - do we?
	 */
	public void setSearchTitle(boolean searchTitle) {
		this.searchTitle = searchTitle;
	}

	@Override
	public String toString() {
		return "Filter [books=" + books + "]\n" +
				"title: " + searchTitle + " content: " + searchContent;
	}

}
