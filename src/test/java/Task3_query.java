import Tasks.Task;
import Tasks.Task_3.Task_3;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3_query {
    private Task task = new Task_3();

    private FSDirectory directory = FSDirectory.open(Paths.get("/Users/alivanov/index"));
    private DirectoryReader indexReader = DirectoryReader.open(directory);

    public Task3_query() throws IOException {
    }

    @Test
    void test_1() throws IOException {
        String delimiter = " ";
        ScoreDoc[] hits = null;
        try {
            task.search(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DirectoryReader indexReader = DirectoryReader.open(directory);

        Terms terms = MultiFields.getTerms(indexReader, "field");
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();
        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertEquals(true, termsMap.containsKey("see eye eye"));
        assertEquals(false, termsMap.containsKey("see"));
        assertEquals(false, termsMap.containsKey("eye"));
        assertEquals(false, termsMap.containsKey("to"));
    }

    @Test
    void test_2() throws IOException {
        String delimiter = " + ";
        ScoreDoc[] hits = null;
        try {
            task.search(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DirectoryReader indexReader = DirectoryReader.open(directory);

        Terms terms = MultiFields.getTerms(indexReader, "field");
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();
        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertEquals(true, termsMap.containsKey("make + long + story + short"));
        assertEquals(false, termsMap.containsKey("make"));
        assertEquals(false, termsMap.containsKey("long"));
        assertEquals(false, termsMap.containsKey("story"));
        assertEquals(false, termsMap.containsKey("short"));

    }

    @Test
    void test_3() throws IOException {
        String delimiter = " _ ";
        ScoreDoc[] hits = null;
        try {
            task.search(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DirectoryReader indexReader = DirectoryReader.open(directory);

        Terms terms = MultiFields.getTerms(indexReader, "field");
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();
        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertEquals(true, termsMap.containsKey("make _ long _ story _ short"));
        assertEquals(false, termsMap.containsKey("make"));
        assertEquals(false, termsMap.containsKey("long"));
        assertEquals(false, termsMap.containsKey("story"));
        assertEquals(false, termsMap.containsKey("short"));
    }

    @Test
    void test_4() throws IOException {
        String delimiter = " ";
        ScoreDoc[] hits = null;
        try {
            task.search(delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DirectoryReader indexReader = DirectoryReader.open(directory);

        Terms terms = MultiFields.getTerms(indexReader, "field");
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();
        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertEquals(true, termsMap.containsKey("question"));
        assertEquals(false, termsMap.containsKey("to"));
        assertEquals(false, termsMap.containsKey("be"));
        assertEquals(false, termsMap.containsKey("or"));
    }
}
