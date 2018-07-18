import Tasks.Task;
import Tasks.Task_2.Task_2;
import org.apache.lucene.search.ScoreDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2_query {
    private Task task = new Task_2();

    @BeforeEach
    private void initEach() {
        File index = new File("/Users/alivanov/index");

        String[] entries = index.list();
        for (String s : entries) {
            File currentFile = new File(index.getPath(), s);
            currentFile.delete();
        }
        System.out.println();
    }

    @Test
    void test_1() {
        String searchQuery = "to be not 1";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 1);
    }

    @Test
    void test_2() {
        String searchQuery = "to or to 1";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 0);
    }

    @Test
    void test_3() {
        String searchQuery = "to 1";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 3);
    }

    @Test
    void test_4() {
        String searchQuery = "long story short 0";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 1);
    }

    @Test
    void test_5() {
        String searchQuery = "long short 0";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 0);
    }

    @Test
    void test_6() {
        String searchQuery = "long short 1";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 1);
    }

    @Test
    void test_12() {
        String searchQuery = "short long 1";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 0);
    }

    @Test
    void test_7() {
        String searchQuery = "story long 1";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 0);
    }

    @Test
    void test_8() {
        String searchQuery = "story long 4";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 0);
    }

    @Test
    void test_9() {
        String searchQuery = "story lo 2";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 0);
    }

    @Test
    void test_10() {
        String searchQuery = "lo story 2";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 1);
    }

    @Test
    void test_11() {
        String searchQuery = "s l 2";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 2);
    }

}
