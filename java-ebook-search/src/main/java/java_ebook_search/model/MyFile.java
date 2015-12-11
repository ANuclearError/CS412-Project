package java_ebook_search.model;

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

	private String book;
	
	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	private static final long serialVersionUID = -7307250172058813456L;

	public MyFile(String pathname, String book) {
		super(pathname);
		this.book = book;
	}
	
	@Override
	public String toString(){
		
		//Stick a number infront of it, if not 0
		return data == 0 ? this.getName() + "\n" + book :
				(data + ": " + this.getName() + "\n" + book);
	}

}
