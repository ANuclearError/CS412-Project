package java_ebook_search.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: CommonSearchTerms.java
 * 
 * @Author Kevin Paton
 * @Since 14 Dec 2015
 */

public class CommonSearchTerms {

	public static List<String> TERMS = new ArrayList<String>();

	/**
	 * Load in the default words associated with java. N.b. all these words
	 * return a hit in our dataset.
	 * 
	 * @throws IOException
	 */
	public static void createDefault() throws IOException {

		String string = readFile("src/main/resources/java_ebook_search/Common-Java-Search-Terms.txt");
		String strings[] = string.split("\\r?\\n");

		for (String s : strings) {
			TERMS.add(s);
		}
	}

	/**
	 * Read in file
	 * 
	 * @param fileName
	 *            (Path)
	 * @return
	 * @throws IOException
	 */
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
