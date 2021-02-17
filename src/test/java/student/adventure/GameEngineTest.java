package student.adventure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

    private DungeonGameEngine testEngine;

    @Before
    public void setUp() {
        testEngine = new DungeonGameEngine("src/test/resources/validTestJSON.json");
    }

    //Test proper GSON error handling

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidJSONPath() {
        DungeonGameEngine testGame = new DungeonGameEngine("waterfile");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyJSONFile() {
        DungeonGameEngine testGame = new DungeonGameEngine("src/test/resources/emptyfile.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidJSONSchema() {
        DungeonGameEngine testGame = new DungeonGameEngine("src/main/resources/siebel.json");
    }

    //Testing quit command

    @Test
    public void testValidQuitCommand() {
        assertEquals("Goodbye!", testEngine.inputCommand("quit", ""));
    }

    //Testing exit command

    @Test
    public void testValidExitCommand() {
        assertEquals("Goodbye!", testEngine.inputCommand("exit", ""));
    }

    //Testing examine command

    @Test
    public void testValidExamineCommand() {
        String expectedResult = "===Cathouse===\n" +
                "Test Room\n" +
                "\n" +
                "From here, you can go: forward\n" +
                "Items Visible: waterbottle, pentel energel black pen";
        String actualResult = testEngine.inputCommand("examine", "");

        assertEquals(expectedResult, actualResult);
    }

    //Testing go command

    @Test
    public void testValidGoCommand() {
        testEngine.inputCommand("go", "forward");

        assert (testEngine.getCurrentRoom().getRoomName().equals("Doghouse"));
    }

    @Test
    public void testInvalidDirectionGoCommand() {
        String testargument = "to the moon";

        String expectedResult = "Cannot go in the direction '" + testargument + "'";
        String actualResult = testEngine.inputCommand("go", "to the moon");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testLockedRoomGoCommand() {
        testEngine.inputCommand("go", "forward");

        String expectedResult = "Cannot leave the room. The room is locked.";
        String actualResult = testEngine.inputCommand("go", "backwards");

        assertEquals(expectedResult, actualResult);
    }

    //Testing inventory command

    @Test
    public void testValidInventoryCommand() {
        testEngine.inputCommand("take", "waterbottle");
        testEngine.inputCommand("take", "pentel energel black pen");

        String expectedResult = "Inventory: waterbottle, pentel energel black pen";
        String actualResult = testEngine.inputCommand("inventory", "");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmptyInventoryCommand() {
        String actualResult = testEngine.inputCommand("inventory", "");

        assertEquals("Inventory: (nothing)", actualResult);
    }

    //Testing take command

    @Test
    public void testValidTakeCommand() {
        String itemName = "waterbottle";
        String expectedResult = "Its a water bottle.";
        String actualResult = testEngine.inputCommand("take", itemName);

        //tests the correct output, if the inventory contains has the item, and the room does not contain the item
        assertEquals(expectedResult, actualResult);
        assert (!testEngine.getCurrentRoom().toString().contains(itemName));
        assert (testEngine.inputCommand("inventory", "").contains(itemName));
    }

    @Test
    public void testInValidTakeCommand() {
        String expectedResult = "Room does not contain ''";
        String actualResult = testEngine.inputCommand("take", "");

        assertEquals(expectedResult, actualResult);
    }

    //Testing drop command

    @Test
    public void testValidDropCommand() {
        String testItem = "pentel energel black pen";

        testEngine.inputCommand("take", "pentel energel black pen");
        testEngine.inputCommand("take", "waterbottle");
        testEngine.inputCommand("go", "forward");
        testEngine.inputCommand("drop", "pentel energel black pen");

        //properly checks if the item is in the room because examine command prints all of the items in the room out
        //so if the item is properly dropped, it shows up in examine
        assert (testEngine.inputCommand("examine", "").contains(testItem));
        //also makes sure that the item doesn't show up in the inventory
        assert (!testEngine.inputCommand("inventory", "").contains(testItem));
    }

    @Test
    public void testInvalidDropCommand() {
        String testItem = "pentel energel black pen";

        testEngine.inputCommand("take", "waterbottle");

        String expectResult = "Inventory does not contain '" + testItem + "'";
        String actualResult = testEngine.inputCommand("drop", "pentel energel black pen");

        assertEquals(expectResult, actualResult);
    }

    //Testing unlock command

    @Test
    public void testValidUnlockCommand() {
        testEngine.inputCommand("take", "waterbottle");
        testEngine.inputCommand("take", "pentel energel black pen");
        testEngine.inputCommand("go", "forward");

        String expectedResult = "Room unlocked! You may now *go* in any valid direction and leave the room.";
        String actualResult = testEngine.inputCommand("unlock", "");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testInvalidItemsUnlockCommand() {
        String expectedResult = "Room is not locked.";
        String actualResult = testEngine.inputCommand("unlock", "");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testInvalidRoomUnlockCommand() {
        testEngine.inputCommand("take", "waterbottle");
        testEngine.inputCommand("go", "forward");

        String expectedResult = "You do not have the necessary items to *unlock* the doors in this room. "
                + "You must *restart* the game";
        String actualResult = testEngine.inputCommand("unlock", "");

        assertEquals(expectedResult, actualResult);
    }

    //Testing restart command

    @Test
    public void testValidRestartCommand() {
        testEngine.inputCommand("restart", "");

        assertEquals(testEngine.getStartingRoom(), testEngine.getCurrentRoom());
    }

    //Testing invalid command

    @Test
    public void testNonexistentCommand() {
        String invalidCommand = "fly";
        String invalidArgument = "the airplane";

        String expectedResult = "CommandError: '" + invalidCommand + invalidArgument + "' is not a valid command";
        String actualResult = testEngine.inputCommand(invalidCommand, invalidArgument);

        assertEquals(expectedResult, actualResult);
    }
}