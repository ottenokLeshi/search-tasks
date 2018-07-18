import Tasks.Task;
import Tasks.Task_1.Task_1;
import org.apache.lucene.search.ScoreDoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1_query {
    private Task task = new Task_1();

    @BeforeEach
    private void initEach() {
        File index = new File("/Users/alivanov/index");

        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
        System.out.println();
    }

    @Test
    void test_1() {
        String searchQuery = "lqdocsplgi";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 2);
    }

    @Test
    void test_2() {
        String searchQuery = "lqd///gif";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 12);
    }

    @Test
    void test_3() {
        String searchQuery = "minusbottom.gif";
        ScoreDoc[] hits = null;
        try {
            hits = task.search(searchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(hits.length, 1);
    }
}
