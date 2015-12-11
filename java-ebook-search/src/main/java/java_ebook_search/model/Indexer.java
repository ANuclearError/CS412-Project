package java_ebook_search.model;

import java.io.IOException;

/**
 * Class: Indexer.java
 *
 * @Author Kevin Paton
 * @Since 26 Nov 2015
 */

public class Indexer {

	public static void main(String args[]) throws IOException{

		Indexer index = new Indexer();
		index.index("src/main/resources/java_ebook_search/indexed_files",
					"src/main/resources/java_ebook_search/index");
	}

	public void index(String documentsSrc, String indexDest) throws IOException {

		// TODO remove
		// -index
		// "/Users/kp_one/Documents/sandbox/CS412-Project/java-ebook-search/src/main/resources/index",
		// -docs "/Users/kp_one/Desktop/java"

		String indexArgs[] = { "-index", indexDest, "-docs", documentsSrc };

		MyIndexFiles.main(indexArgs, Stopwords.getWords());

	}

}