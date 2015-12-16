package java_ebook_search.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * The Search class is responsible for searching through the index, and
 * providing the results so that they can be displayed in the view.
 *
 * @author Aidan O'Grady
 * @since 0.2
 */
public class Search {

	/**
	 * The Index Searcher that looks through the index.
	 */
	private IndexSearcher indexSearcher;

	/**
	 * Index reader that reads through index.
	 */
	private IndexReader reader;

	/**
	 * List of frequent words taken from Lucene, plus terms searched by user.
	 */
	private Set<String> commonTerms;

	public Search(String index) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(index).getFile());
		Directory dir = FSDirectory.open(Paths.get(file.getAbsolutePath()));
		reader = DirectoryReader.open(dir);
		indexSearcher = new IndexSearcher(reader);
		generateCommonTerms();
	}

	private void generateCommonTerms() {
		commonTerms = new HashSet<>();
		try {
			TermStats[] stats = HighFreqTerms.getHighFreqTerms(
					reader,
					1000,
					"contents",
					new HighFreqTerms.TotalTermFreqComparator());

			for(TermStats stat : stats) {
				commonTerms.add(stat.termtext.utf8ToString());
			}
		} catch (Exception e) {
			commonTerms = null;
		}
	}

	/**
	 * Returns a list of results given a search query.
	 * 
	 * @param term
	 *            - the search the user entered
	 * @return list of results based on search.
	 * @throws ParseException
	 *             - there was a problem parsing.
	 * @throws IOException
	 *             - there was a problem reading results.
	 */
	public List<Result> search(String term, Filter filter)
			throws ParseException, IOException {

		List<Result> toReturn = new ArrayList<Result>();

		Analyzer analyzer = new StandardAnalyzer(Stopwords.getWords());
		QueryParser content = new QueryParser("contents", analyzer);
		QueryParser title = new QueryParser("title", analyzer);

		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		if(filter.isSearchContent())
			builder.add(content.parse(term), BooleanClause.Occur.SHOULD);
		if(filter.isSearchTitle())
			builder.add(title.parse(term), BooleanClause.Occur.SHOULD);
		Query query = builder.build();

		TopDocs results = indexSearcher.search(query, 50);

		// System.out.println("Searching for: " + term);
		// System.out.println("Results: " + results.totalHits);

		int numTotalHits = results.totalHits;
		int hitsPerPage = 25;
		ScoreDoc[] hits = results.scoreDocs;

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		while (true) {

			if (end > hits.length) {
				hits = indexSearcher.search(query, numTotalHits).scoreDocs;
			}
			end = Math.min(hits.length, start + hitsPerPage);
			for (int i = start; i < end; i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				String path = doc.get("path");
				if (path != null) {
					// System.out.println((i + 1) + ". " + path);
					toReturn.add(new Result(path, doc));
				} else {
					// System.out.println((i + 1) + ". " + "No path for this
					// document");
				}
			}
			return toReturn;
		}
	}

	public Set<String> getAutocomplete() {
		return commonTerms;
	}

	public boolean addTerm(String term) {
		return commonTerms.add(term);
	}
}
