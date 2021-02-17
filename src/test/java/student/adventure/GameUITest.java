package student.adventure;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class GameUITest {

    private static DungeonGameUI testUI;

    @BeforeClass
    public static void setUp() {
        testUI = new DungeonGameUI("src/test/resources/validTestJSON.json");
    }

    @Test
    public void testIfCommandContainsExtraWhiteSpace() {
        String[] expectedResult = {"cmd", "why is there bad whitespace"};

        String[] actualResult =
                testUI.cleanCommandAndArguments("   cmd         why   is   there    bad whitespace       ");

        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void testIfCommandIsCaseSensitive() {
        String[] expectedResult = {"water", "is not a virus or a command"};

        String[] actualResult =
                testUI.cleanCommandAndArguments("wAtEr IS nOT a ViRus OR A COMMaND");

        assertArrayEquals(expectedResult, actualResult);
    }
}
