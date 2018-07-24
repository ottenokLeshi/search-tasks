package Tasks;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class TaskHelper {

    public static void readFile(List<String> docArr, Path path) {
        try (Stream<String> stream = Files.lines( path, StandardCharsets.UTF_8)) {
            stream.forEach(docArr::add);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDocsToIndex (List<String> docArr, IndexWriter indexWriter, String fieldName) throws IOException {
        Document doc = new Document();
        TextField textField = new TextField(fieldName, "",  Field.Store.YES);

        for (String text: docArr) {
            textField.setStringValue(text);
            doc.removeField(fieldName);
            doc.add(textField);
            indexWriter.addDocument(doc);
        }

        indexWriter.close();
    }
}
