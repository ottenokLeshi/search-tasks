import Tasks.Task1.Task1;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Task1Test {

    @AfterClass
    public static void closeDirectory() throws IOException {
        Task1.close();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "lqdocsplgi", 2 },
                { "lqd///gif", 12 },
                { "minusbottom.gif", 1 }
        });
    }

    @Parameterized.Parameter
    public String fInput;

    @Parameterized.Parameter(1)
    public int fExpected;

    @Test
    public void pathTest() throws IOException {
        assertEquals(Task1.search(fInput).length, fExpected);
    }
}
