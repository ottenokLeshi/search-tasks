package Tasks.Task1;

import Tasks.TaskHelper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Task1 {

    private static Directory directory;
    private static String fieldName;
    private static Path pathToFile;

    static {
        pathToFile = Paths.get("src/main/resources/task1.txt");
        directory = new RAMDirectory();
        fieldName = "path";

        try {
            indexing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String makeLowerWildCard(String string) {
        StringBuilder wildQuery = new StringBuilder().append("*");

        for (int i = 0; i < string.length(); i++) {
            wildQuery.append(string.charAt(i)).append("*");
        }

        return wildQuery.toString().toLowerCase();
    }

    public static ScoreDoc[] search(String query) throws IOException {
        ScoreDoc[] hits = null;

        query = makeLowerWildCard(query);

        try (DirectoryReader indexReader = DirectoryReader.open(directory)) {
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
                Query searchQuery = new WildcardQuery(new Term(fieldName, query));
                try {
                    hits = indexSearcher.search(searchQuery, 1000).scoreDocs;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return hits;
    }

    private static void indexing() throws IOException {
        List<String> docArr = new ArrayList<>();

        TaskHelper.readFile(docArr, pathToFile);

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(KeywordTokenizerFactory.class)
                .addTokenFilter("lowercase")
                .build();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        TaskHelper.writeDocsToIndex(docArr, indexWriter, fieldName);
    }

    public static void close() throws IOException {
        directory.close();
    }
}
