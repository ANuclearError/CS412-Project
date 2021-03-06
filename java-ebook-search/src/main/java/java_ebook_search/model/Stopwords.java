package java_ebook_search.model;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Class: Stopwords.java
 *
 * The default Lucene stopwords included boolean operators "and", "or" and "not"
 * which are words that seemed necessary for searching for when querying the
 * search engine.
 * 
 * @author Kevin Paton
 * @since 27 Nov 2015
 */
public class Stopwords {
	public static CharArraySet getWords() {
		List<String> stopWords = Arrays.asList("a", "an", "are", "as", "at",
				"be", "but", "by", "in", "into", "is", "it", "no", "of", "on",
				"such", "that", "the", "their", "then", "there", "these",
				"they", "to", "was", "will", "with");
		return new CharArraySet(stopWords, true);
	}
}
