package Tasks;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public abstract class Task {

    private String pathToIndex = "/Users/alivanov/index";

    protected void readFile(List docArr, Path path) {
        try (Stream<String> stream = Files.lines( path, StandardCharsets.UTF_8)) {
            stream.forEach(s -> docArr.add(s));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        cleanIndex();
    }

    public String readQuery() throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String query = reader.readLine();
        return query;
    }

    protected void cleanIndex() {
        File index = new File(pathToIndex);

        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
    }

    protected void writeResults(String query, ScoreDoc[] hits, IndexSearcher indexSearcher, String fieldName) throws IOException {
        System.out.println("Search query: " + query);
        System.out.println("Amount of results: " + hits.length);

        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = indexSearcher.doc(hits[i].doc);
            System.out.print("DocId " + hits[i].doc + "; Value: ");
            System.out.println(hitDoc.get(fieldName));
        }
    }

    public abstract ScoreDoc[] search(String query) throws IOException;
}
