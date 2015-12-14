package java_ebook_search.model;

import java.io.File;

import org.apache.lucene.document.Document;

/**
 * Class: Result.java
 * 
 * @Author Kevin Paton
 * @Since 27 Nov 2015
 * 
 *        A class to extend File so I can overwrite the toString
 */
public class Result extends File {

	private static final long serialVersionUID = -7307250172058813456L;

	/**
	 * Position within results.
	 */
	private int data;

	/**
	 * The document stored in Lucene's index.
	 */
	private Document doc;

	/**
	 * Constructor.
	 * @param pathname - location of file
	 * @param doc - document in index
	 */
	public Result(String pathname, Document doc) {
		super(pathname);
		this.doc = doc;
	}

	/**
	 * Return the book this file belongs to.
	 * 
	 * @return book
	 */
	public String getBook() {
		String toReturn = "";
		if (null != doc) {
			toReturn = doc.get("book");
		}
		return toReturn;
	}

	/**
	 * Returns position in result
	 * @return data
	 */
	public int getData() {
		return data;
	}

	/**
	 * Sets the position result appears in.
	 * @param data - position
	 */
	public void setData(int data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String string = "";
		// Stick a number infront of it, if not 0
		string = data == 0 ? doc.get("title") : (data + ": " + doc.get("title"));
		string += "\n" + doc.get("book") + "\n";
		string += doc.get("chapter") + ", " + doc.get("section");
		return string;
	}
}
