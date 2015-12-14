package java_ebook_search.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* Class: CommonSearchTerms.java
* @Author Kevin Paton
* @Since 14 Dec 2015
*/

public class CommonSearchTerms {

	public static Set<String> TERMS = new HashSet<String>(); 
	
	public static void createDefault() throws IOException{
		
		String string = readFile("src/main/resources/java_ebook_search/Common-Java-Search-Terms.txt");
		String strings[] = string.split("\\r?\\n");
		
		for(String s: strings){
			TERMS.add(s);
		}
	}
	
	
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
