package Tasks.Task_3;

import Tasks.Task;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task_3 extends Task {

    public static void main(String[] args) throws IOException {
        Task task = new Task_3();
        task.search(" + ");
    }

    @Override
    public ScoreDoc[] search(String delimiter) throws IOException {
        List<String> docArr = new ArrayList<>();
        Path pathToFile = Paths.get("docs/task_2.txt");
        readFile(docArr, pathToFile);

        Map<String, String> delMap = new HashMap<>();
        delMap.put("delimiter", delimiter);

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(StopFilterFactory.class)
                .addTokenFilter(ConcatenateFilterFactory.class, delMap)
                .build();

        FSDirectory directory = FSDirectory.open(Paths.get("/Users/alivanov/index"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);

        Document doc = new Document();
        TextField textField = new TextField("field", "", Field.Store.YES);

        for (String text: docArr) {
            textField.setStringValue(text);
            doc.removeField("field");
            doc.add(textField);
            iwriter.addDocument(doc);
        }

        iwriter.close();
        directory.close();

        return null;
    }
}
