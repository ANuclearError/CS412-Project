package java_ebook_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Class: Stopwords.java
 * 
 * @Author Kevin Paton
 * @Since 27 Nov 2015
 */

public class Stopwords {

	private Stopwords() {
		// No instances
	}

	public static CharArraySet getWords() {

		Collection<String> stopWordCollection = new ArrayList<String>(Arrays.asList("a", "an", "and", "are", "as", "at",
				"be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that",
				"the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with"));

		stopWordCollection.remove("and");
		stopWordCollection.remove("or");
		stopWordCollection.remove("not");
		stopWordCollection.remove("if");
		stopWordCollection.remove("for");
		stopWordCollection.remove("this");

		return new CharArraySet(stopWordCollection, true);

	}

}
