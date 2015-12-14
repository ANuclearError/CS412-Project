package java_ebook_search.model;

import org.apache.lucene.document.Document;

import java.io.File;

/**
* Class: MyFile.java
* @Author Kevin Paton
* @Since 27 Nov 2015
* 
* A class to extend File so I can overwrite the toString
*/
public class MyFile extends File {
	
	private int data;

	/**
	 * The document stored in Lucene's index.
	 */
	private Document doc;

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	private static final long serialVersionUID = -7307250172058813456L;

	public MyFile(String pathname, Document doc) {
		super(pathname);
		this.doc = doc;
	}
	
	@Override
	public String toString(){
		String string = "";
		//Stick a number infront of it, if not 0
		string = data == 0 ? doc.get("title"): (data + ": " + doc.get("title"));
		string  += "\n" + doc.get("book")  + "\n";
		string += doc.get("chapter") + ", " + doc.get("section");
		return string;
	}

}
