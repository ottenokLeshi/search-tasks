package Tasks.Task3;

import Tasks.TaskHelper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3 {

    private static String fieldName;
    private static Path pathToFile;

    static {
        pathToFile = Paths.get("src/main/resources/task2.txt");
        fieldName = "field";
    }

    public static void indexing(Directory directory, String delimiter) throws IOException {
        if (delimiter == null) {
            delimiter = " ";
        }

        List<String> docArr = new ArrayList<>();

        TaskHelper.readFile(docArr, pathToFile);

        Map<String, String> delMap = new HashMap<>();
        delMap.put("delimiter", delimiter);

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(StopFilterFactory.class)
                .addTokenFilter(ConcatenateFilterFactory.class, delMap)
                .build();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        TaskHelper.writeDocsToIndex(docArr, indexWriter, fieldName);
    }

    public static String getFieldName() {
        return fieldName;
    }
}
