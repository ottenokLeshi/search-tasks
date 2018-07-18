package Tasks.Task_1;

import Tasks.Task;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Task_1 extends Task {

    @Override
    public ScoreDoc[] search(String query) throws IOException {
        Path pathToFile = Paths.get("docs/task_1.txt");
        List<String> docArr = new ArrayList<>();
        readFile(docArr, pathToFile);

        StringBuilder wildQuery = new StringBuilder().append("*");

        for (int i = 0; i < query.length(); i++) {
            wildQuery.append(query.charAt(i));
            wildQuery.append("*");
        }

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(KeywordTokenizerFactory.class)
                .addTokenFilter("lowercase")
                .build();
        FSDirectory directory = FSDirectory.open(Paths.get("/Users/alivanov/index"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);

        Document doc = new Document();
        TextField textField = new TextField("path", "",  Field.Store.YES);

        for (String text: docArr) {
            textField.setStringValue(text);
            doc.removeField("path");
            doc.add(textField);
            iwriter.addDocument(doc);
        }
        iwriter.close();

        DirectoryReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Term term = new Term("path", wildQuery.toString().toLowerCase());
        Query searchQuery = new WildcardQuery(term);
        ScoreDoc[] hits = indexSearcher.search(searchQuery, 1000).scoreDocs;

        writeResults(query, hits, indexSearcher, "path");

        indexReader.close();
        directory.close();

        return hits;
    }

    public static void main(String[] args) throws IOException {
        Task task = new Task_1();
        task.search(task.readQuery());
    }
}
