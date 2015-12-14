package java_ebook_search.model;

import java.io.IOException;

/**
 * Class: Indexer.java
 *
 * @author Kevin Paton
 * @since 26 Nov 2015
 *
 * Class for handling the indexing of the files.
 */
public class Indexer {

	/**
	 * Main method for the indexing of files.
	 * @param args program arguments
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException{
		Indexer index = new Indexer();
		index.index("src/main/resources/java_ebook_search/indexed_files",
					"src/main/resources/java_ebook_search/index");
	}

	/**
	 * Indexes the given documents to the given directory.
	 *
	 * @param documentsSrc - the files to be indexed.
	 * @param indexDest - the location the index is to be stored in.
	 * @throws IOException
	 */
	public void index(String documentsSrc, String indexDest) throws IOException {
		String indexArgs[] = { "-index", indexDest, "-docs", documentsSrc };
		MyIndexFiles.main(indexArgs, Stopwords.getWords());
	}
}