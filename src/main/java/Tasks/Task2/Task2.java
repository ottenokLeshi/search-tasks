package Tasks.Task2;

import Tasks.TaskHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Task2 {

    private static Directory directory;
    private static String fieldName;
    private static Path pathToFile;

    static {
        pathToFile = Paths.get("src/main/resources/task2.txt");
        directory = new RAMDirectory();
        fieldName = "text";

        try {
            indexing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Pair<String, Integer> separateQueryAndDistance(String string) {
        int lastSpaceIndex = string.lastIndexOf(" ");
        int distance = Integer.parseInt(string.substring(lastSpaceIndex + 1, string.length()));
        string = string.substring(0, lastSpaceIndex);

        return new ImmutablePair<>(string, distance);
    }

    private static void indexing() throws IOException {
        List<String> docArr = new ArrayList<>();

        TaskHelper.readFile(docArr, pathToFile);

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(SeparateFilterFactory.class)
                .build();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        TaskHelper.writeDocsToIndex(docArr, indexWriter, fieldName);
    }

    public static ScoreDoc[] search(String query) throws IOException {
        Pair<String, Integer> pair = separateQueryAndDistance(query);
        String pureQuery = pair.getLeft();
        int distance = pair.getRight();
        ScoreDoc[] hits;

        try (DirectoryReader indexReader = DirectoryReader.open(directory)) {
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            Query resultQuery;

            if (pureQuery.split(" ").length == 1) {
                resultQuery = new TermQuery(new Term(fieldName, pureQuery));
            } else {
                resultQuery = new SpanNearQuery(Arrays.stream(pureQuery.split(" ")).map(str ->
                        new SpanTermQuery(new Term(fieldName, str))).toArray(SpanQuery[]::new),
                        distance,
                        true);
            }

            hits = indexSearcher.search(resultQuery, 1000).scoreDocs;
        }

        return hits;
    }

    public static void close() throws IOException {
        directory.close();
    }
}
