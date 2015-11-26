package java_ebook_search.model;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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
     * The query parser that handles the queries.
     */
    private QueryParser queryParser;

    /**
     * The field that is to be searched.
     */
    String field = "contents";

    public Search(String index) throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(index));
        IndexReader reader = DirectoryReader.open(indexDir);
        indexSearcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();
        queryParser = new QueryParser(field, analyzer);
    }

    /**
     * Returns a list of results given a search query.
     * @param term - the search the user entered
     * @return list of results based on search.
     * @throws ParseException - there was a problem parsing.
     * @throws IOException - there was a problem reading results.
     */
    public List<String> search(String term) throws ParseException, IOException {
        Query query = queryParser.parse(term);
        TopDocs results = indexSearcher.search(query, 50);

        System.out.println("Searching for: " + query.toString(field));
        System.out.println("Results: " + results.totalHits);

        return null;
    }
}
