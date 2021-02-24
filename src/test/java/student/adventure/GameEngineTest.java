package student.adventure;

import org.junit.Before;
import org.junit.Test;
import student.server.Command;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

  private DungeonGameEngine testEngine;

  @Before
  public void setUp() {
    testEngine = new DungeonGameEngine("src/test/resources/validTestJSON.json");
  }

  // Testing invalid command

  @Test
  public void testNonexistentCommand() {
    Command testBadCommand = new Command("fly", "the airplane");

    String expectedResult = "CommandError: '" + testBadCommand + "' is not a valid command";
    String actualResult = testEngine.inputCommand(testBadCommand);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testEmptyCommand() {
    Command testBadCommand = new Command("", "");

    String expectedResult = "CommandError: '" + testBadCommand + "' is not a valid command";
    String actualResult = testEngine.inputCommand(testBadCommand);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testNullCommand() {
    Command testBadCommand = null;

    String expectedResult = "CommandError: Command cannot be null";
    String actualResult = testEngine.inputCommand(testBadCommand);

    assertEquals(expectedResult, actualResult);
  }

  // Testing quit command

  @Test
  public void testValidQuitCommand() {
    assertEquals("Goodbye!", testEngine.inputCommand(new Command("quit", "")));
  }

  @Test
  public void testInvalidPartialQuitCommand() {

    Command invalidQuitCommand = new Command("quite", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testExtraArgumentQuitCommand() {
    assertEquals("Goodbye!", testEngine.inputCommand(new Command("quit", "go")));
  }

  // Testing exit command

  @Test
  public void testValidExitCommand() {
    assertEquals("Goodbye!", testEngine.inputCommand(new Command("exit", "")));
  }

  @Test
  public void testInvalidPartialExitCommand() {

    Command invalidQuitCommand = new Command("exited", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testExtraArgumentExitCommand() {
    assertEquals("Goodbye!", testEngine.inputCommand(new Command("exit", "hats")));
  }

  // Testing examine command

  @Test
  public void testValidExamineCommand() {
    String expectedResult =
        "===Cathouse===\nTest Room\n\nFrom here, you can go: forward\nItems Visible: waterbottle, pentel energel black pen";
    String actualResult = testEngine.inputCommand(new Command("examine", ""));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidPartialExamineCommand() {

    Command invalidQuitCommand = new Command("examined", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testExtraArgumentExamineCommand() {
    String expectedResult =
            "===Cathouse===\nTest Room\n\nFrom here, you can go: forward\nItems Visible: waterbottle, pentel energel black pen";
    String actualResult = testEngine.inputCommand(new Command("examine", "take go "));

    assertEquals(expectedResult, actualResult);
  }

  // Testing go command

  @Test
  public void testValidGoCommand() {
    testEngine.inputCommand(new Command("go", "forward"));

    assert (testEngine.getCurrentRoom().getRoomName().equals("Doghouse"));
  }

  @Test
  public void testInvalidDirectionGoCommand() {
    String testArgument = "to the moon";

    String expectedResult = "Cannot go in the direction '" + testArgument + "'";
    String actualResult = testEngine.inputCommand(new Command("go", "to the moon"));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testLockedRoomGoCommand() {
    testEngine.inputCommand(new Command("go", "forward"));

    String expectedResult = "Cannot leave the room. The room is locked.";
    String actualResult = testEngine.inputCommand(new Command("go", "backwards"));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidPartialGoCommand() {

    Command invalidQuitCommand = new Command("goat", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testEmptyGoArgumentCommand() {
    String testArgument = "";

    String expectedResult = "Cannot go in the direction '" + testArgument + "'";
    String actualResult = testEngine.inputCommand(new Command("go", testArgument));

    assertEquals(expectedResult, actualResult);
  }

  // Testing inventory command

  @Test
  public void testValidInventoryCommand() {
    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("take", "pentel energel black pen"));

    String expectedResult = "Inventory: waterbottle, pentel energel black pen";
    String actualResult = testEngine.inputCommand(new Command("inventory", ""));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidPartialInventoryCommand() {

    Command invalidQuitCommand = new Command("inventorial", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testEmptyInventoryCommand() {
    String actualResult = testEngine.inputCommand(new Command("inventory", ""));

    assertEquals("Inventory: (nothing)", actualResult);
  }

  @Test
  public void testExtraArgumentInventoryCommand() {
    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("take", "pentel energel black pen"));

    String expectedResult = "Inventory: waterbottle, pentel energel black pen";
    String actualResult = testEngine.inputCommand(new Command("inventory", "cows"));

    assertEquals(expectedResult, actualResult);
  }

  // Testing take command

  @Test
  public void testValidTakeCommand() {
    String itemName = "waterbottle";
    String expectedResult = "Its a water bottle.";
    String actualResult = testEngine.inputCommand(new Command("take", itemName));

    // tests the correct output, if the inventory contains has the item, and the room does not
    // contain the item
    assertEquals(expectedResult, actualResult);
    assert (!testEngine.getCurrentRoom().toString().contains(itemName));
    assert (testEngine.inputCommand(new Command("inventory", "")).contains(itemName));
  }

  @Test
  public void testInvalidTakeCommand() {
    String testArgument = "catfish";

    String expectedResult = "Room does not contain '" + testArgument+ "'";
    String actualResult = testEngine.inputCommand(new Command("take", testArgument));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidPartialTakeCommand() {

    Command invalidQuitCommand = new Command("taken", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testEmptyTakeArgumentCommand() {
    String testArgument = "";

    String expectedResult = "Room does not contain '" + testArgument+ "'";
    String actualResult = testEngine.inputCommand(new Command("take", testArgument));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testTakeItemInInventoryCommand() {
    String itemName = "waterbottle";

    testEngine.inputCommand(new Command("take", itemName));
    testEngine.inputCommand(new Command("take", itemName));

    String expectedResult = "Room does not contain '" + itemName+ "'";
    String actualResult = testEngine.inputCommand(new Command("take", itemName));

    assertEquals(expectedResult, actualResult);
  }

  // Testing drop command

  @Test
  public void testValidDropCommand() {
    String testItem = "pentel energel black pen";

    testEngine.inputCommand(new Command("take", "pentel energel black pen"));
    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("go", "forward"));
    testEngine.inputCommand(new Command("drop", "pentel energel black pen"));

    // properly checks if the item is in the room because examine command prints all of the items in
    // the room out
    // so if the item is properly dropped, it shows up in examine
    assert (testEngine.inputCommand(new Command("examine", "")).contains(testItem));
    // also makes sure that the item doesn't show up in the inventory
    assert (!testEngine.inputCommand(new Command("inventory", "")).contains(testItem));
  }

  @Test
  public void testInvalidDropCommand() {
    String testItem = "cowboy";

    testEngine.inputCommand(new Command("take", "waterbottle"));

    String expectResult = "Inventory does not contain '" + testItem + "'";
    String actualResult = testEngine.inputCommand(new Command("drop", testItem));

    assertEquals(expectResult, actualResult);
  }

  @Test
  public void testInvalidPartialDropCommand() {

    Command invalidQuitCommand = new Command("droplet", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testEmptyDropArgumentCommand() {
    String testItem = "";

    testEngine.inputCommand(new Command("take", "waterbottle"));

    String expectResult = "Inventory does not contain '" + testItem + "'";
    String actualResult = testEngine.inputCommand(new Command("drop", testItem));

    assertEquals(expectResult, actualResult);
  }

  @Test
  public void testDropItemInRoom() {
    String testItem = "pentel energel black pen";

    testEngine.inputCommand(new Command("take", "waterbottle"));

    String expectResult = "Inventory does not contain '" + testItem + "'";
    String actualResult = testEngine.inputCommand(new Command("drop", "pentel energel black pen"));

    assertEquals(expectResult, actualResult);
  }

  // Testing unlock command

  @Test
  public void testValidUnlockCommand() {
    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("take", "pentel energel black pen"));
    testEngine.inputCommand(new Command("go", "forward"));

    System.out.println(testEngine.getCurrentRoom());
    String expectedResult =
        "Room unlocked! You may now *go* in any valid direction and leave the room.";
    String actualResult = testEngine.inputCommand(new Command("unlock", ""));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidItemsUnlockCommand() {
    String expectedResult = "Room is not locked.";
    String actualResult = testEngine.inputCommand(new Command("unlock", ""));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidRoomUnlockCommand() {
    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("go", "forward"));

    String expectedResult =
        "You do not have the necessary items to *unlock* the doors in this room. "
            + "You must *restart* the game";
    String actualResult = testEngine.inputCommand(new Command("unlock", ""));

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void testInvalidPartialUnlockCommand() {

    Command invalidQuitCommand = new Command("unlocked", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testExtraArgumentUnlockCommand() {
    String expectedResult = "Room is not locked.";
    String actualResult = testEngine.inputCommand(new Command("unlock", "cats"));

    assertEquals(expectedResult, actualResult);
  }

  // Testing restart command

  @Test
  public void testValidRestartCommand() {

    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("go", "forward"));
    testEngine.inputCommand(new Command("restart", ""));

    assertEquals(testEngine.getStartingRoom(), testEngine.getCurrentRoom());
  }

  @Test
  public void testInvalidPartialRestartCommand() {

    Command invalidQuitCommand = new Command("restarted", "watery");
    String expectedResult = "CommandError: '" + invalidQuitCommand + "' is not a valid command";

    assertEquals(expectedResult, testEngine.inputCommand(invalidQuitCommand));
  }

  @Test
  public void testExtraArgumentRestartCommand() {

    testEngine.inputCommand(new Command("take", "waterbottle"));
    testEngine.inputCommand(new Command("go", "forward"));
    testEngine.inputCommand(new Command("restart", "sf"));

    assertEquals(testEngine.getStartingRoom(), testEngine.getCurrentRoom());
  }

  // Testing achievements

  @Test
  public void testValidAchievement() {
    testEngine.inputCommand(new Command("take", "pentel energel black pen"));
    testEngine.inputCommand(new Command("go", "forward"));
    testEngine.inputCommand(new Command("unlock", ""));

    String expectedResult =
        "You have escaped the dungeon! Congratulations!\nObtained Achievements:\nYou are a Stationary Addict!!! You'll pick up any stationary you find... anywhere.";
    String actualResult = testEngine.inputCommand(new Command("go", "leave the dungeon"));

    assertEquals(expectedResult, actualResult);
  }
}
