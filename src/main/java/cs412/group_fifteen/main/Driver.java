package cs412.group_fifteen.main;

import org.apache.lucene.demo.IndexFiles;
import org.apache.lucene.demo.SearchFiles;

/**
 * Main method class.
 *
 * @author Aidan O'Grady
 * @version 1.0
 * @since 1.0
 */
public class Driver {

    public static void main(String[] args) throws Exception
    {
//        String[] indexArgs = {"-index", "index", "-docs", "files"};
//        IndexFiles.main(indexArgs);
        SearchFiles.main(args);
    }


}
