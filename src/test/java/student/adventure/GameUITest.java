package student.adventure;

import org.junit.BeforeClass;
import org.junit.Test;
import student.server.Command;

import static org.junit.Assert.assertEquals;

public class GameUITest {

    private static DungeonGameUI testUI;

    @BeforeClass
    public static void setUp() {
        testUI = new DungeonGameUI("src/test/resources/validTestJSON.json");
    }

    @Test
    public void testIfCommandContainsExtraWhiteSpace() {
        Command expectedResult = new Command("cmd", "why is there bad whitespace");

        Command actualResult =
                testUI.cleanCommand("   cmd         why   is   there    bad whitespace       ");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIfCommandIsCaseSensitive() {
        Command expectedResult = new Command("water", "is not a virus or a command");

        Command actualResult =
                testUI.cleanCommand("wAtEr IS nOT a ViRus OR A COMMaND");

        assertEquals(expectedResult, actualResult);
    }
}
