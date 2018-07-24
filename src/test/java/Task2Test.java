import Tasks.Task2.Task2;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static Tasks.Task2.Task2.search;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Task2Test {

    @AfterClass
    public static void closeDirectory() throws IOException {
        Task2.close();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "to be not 1", 1 },
                { "to or to 1", 0 },
                { "to 1", 3 },
                { "long story short 0", 1 },
                { "long short 0", 0 },
                { "long short 1", 1 },
                { "short long 1", 0 },
                { "story long 1", 0},
                { "story long 4", 0},
                { "story lo 2", 0},
                { "lo story 2", 1},
                { "s l 2", 2}
        });
    }

    @Parameterized.Parameter
    public String fInput;

    @Parameterized.Parameter(1)
    public int fExpected;

    @Test
    public void smallTest() throws IOException {
        assertEquals(search(fInput).length, fExpected);
    }

}
