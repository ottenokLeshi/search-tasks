import Tasks.Task3.Task3;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Task3Test {
    private Directory directory;
    private String fieldName = Task3.getFieldName();

    @Before
    public void beforeEach() {
        directory = new RAMDirectory();
    }

    @After
    public void afterEach() throws IOException {
        directory.close();
    }

    @Test
    public void defaultDelimiterTest() throws IOException {

        try {
            Task3.indexing(directory, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectoryReader indexReader = DirectoryReader.open(directory);
        Terms terms = MultiFields.getTerms(indexReader, fieldName);
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();

        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertTrue(termsMap.containsKey("see eye eye"));
        assertFalse(termsMap.containsKey("see"));
        assertFalse(termsMap.containsKey("eye"));
        assertFalse(termsMap.containsKey("to"));
    }

    @Test
    public void customDelimiterTest() throws IOException {
        String delimiter = " + ";

        try {
            Task3.indexing(directory, delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectoryReader indexReader = DirectoryReader.open(directory);
        Terms terms = MultiFields.getTerms(indexReader, fieldName);
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();

        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertTrue(termsMap.containsKey("make + long + story + short"));
        assertFalse(termsMap.containsKey("make"));
        assertFalse(termsMap.containsKey("long"));
        assertFalse(termsMap.containsKey("story"));
        assertFalse(termsMap.containsKey("short"));
    }

    @Test
    public void defaultDelimiterWithStopWordsTest() throws IOException {
        String delimiter = " ";
        try {
            Task3.indexing(directory, delimiter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectoryReader indexReader = DirectoryReader.open(directory);
        Terms terms = MultiFields.getTerms(indexReader, fieldName);
        TermsEnum termsEnum = terms.iterator();
        Map<String, String> termsMap = new HashMap<>();

        while (termsEnum.next() != null) {
            BytesRef term = termsEnum.term();
            termsMap.put(term.utf8ToString(), "term");
        }
        assertTrue(termsMap.containsKey("question"));
        assertFalse(termsMap.containsKey("to"));
        assertFalse(termsMap.containsKey("be"));
        assertFalse(termsMap.containsKey("or"));
    }
}
