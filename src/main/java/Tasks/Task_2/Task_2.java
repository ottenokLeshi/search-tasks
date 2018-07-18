package Tasks.Task_2;

import Tasks.Task;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Task_2 extends Task {

    @Override
    public ScoreDoc[] search(String query) throws IOException {
        List<String> docArr = new ArrayList<>();
        Path pathToFile = Paths.get("docs/task_2.txt");
        readFile(docArr, pathToFile);

        int lastSpaceIndex = query.lastIndexOf(" ");
        int distance = Integer.parseInt(query.substring(lastSpaceIndex + 1, query.length()));
        query = query.substring(0, lastSpaceIndex);

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(SeparateFilterFactory.class)
                .build();
        FSDirectory directory = FSDirectory.open(Paths.get("/Users/alivanov/index"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        Document doc = new Document();
        TextField textField = new TextField("text", "", Field.Store.YES);

        for (String text: docArr) {
            textField.setStringValue(text);
            doc.removeField("text");
            doc.add(textField);
            indexWriter.addDocument(doc);
        }

        indexWriter.close();

        DirectoryReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Query resultQuery = null;

        if (query.split(" ").length == 1) {
            resultQuery = new TermQuery(new Term("text", query));
        } else {
            List<SpanTermQuery> spanQueries = Arrays.stream(query.split(" ")).map(str ->
                    new SpanTermQuery(new Term("text",str))).collect(Collectors.toList());

            resultQuery = new SpanNearQuery(spanQueries.toArray(
                    new SpanQuery[spanQueries.size()]),
                    distance,
                    true);
        }

        ScoreDoc[] hits = indexSearcher.search(resultQuery, 1000).scoreDocs;

        writeResults(query + " " + distance, hits, indexSearcher, "text");

        indexReader.close();
        directory.close();

        return hits;
    }

    public static void main(String[] args) throws IOException {
        Task task = new Task_2();
        task.search(task.readQuery());
    }

}
