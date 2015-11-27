package java_ebook_search;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Class: Stopwords.java
 * 
 * @Author Kevin Paton
 * @Since 27 Nov 2015
 */

public class Stopwords {

	public static CharArraySet getWords() {
		CharArraySet stopWords =
				new CharArraySet(StopAnalyzer.ENGLISH_STOP_WORDS_SET, true);

        // Remove words that are associated with Java.
        String[] javaWords = {"and", "or", "not", "if", "for", "this"};
        for(String word : javaWords)
		    stopWords.remove(word);
		return stopWords;
	}

}
