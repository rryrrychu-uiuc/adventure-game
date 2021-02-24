package student.adventure;

import org.junit.Before;
import org.junit.Test;
import student.server.AdventureException;
import student.server.Command;
import student.server.DungeonGameService;
import student.server.GameStatus;

import java.util.Map;

import static org.junit.Assert.*;

public class DungeonGameServiceTest {

  private final DungeonGameService testGameService = new DungeonGameService();

  @Before
  public void setUp() {

    testGameService.reset();
  }

  // Test newGame()

  @Test
  public void testNewGame() throws AdventureException {

    testGameService.newGame();

    assert (testGameService.getInstancesOfGame().size() == 1
        && DungeonGameEngine.getInstanceId() == 1);
  }

  @Test
  public void testMultipleNewGames() throws AdventureException {

    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();

    assert (testGameService.getInstancesOfGame().size() == 4
        && DungeonGameEngine.getInstanceId() == 4);
  }

  // Test resetGame()

  @Test
  public void testResetService() throws AdventureException {

    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();
    testGameService.reset();

    assert (testGameService.getInstancesOfGame().size() == 0
        && DungeonGameEngine.getInstanceId() == 0);
  }

  @Test
  public void testResetEmptyInstances(){

    testGameService.reset();

    assert (testGameService.getInstancesOfGame().size() == 0
        && DungeonGameEngine.getInstanceId() == 0);
  }

  // Test destroyGame()

  @Test
  public void testDestroySingleInstance() throws AdventureException {

    testGameService.newGame();

    assertTrue(testGameService.destroyGame(1));
    assert (testGameService.getInstancesOfGame().size() == 0
        && DungeonGameEngine.getInstanceId() == 1);
  }

  @Test
  public void testDestroyInstanceDNE(){

    assertFalse(testGameService.destroyGame(1));
    assert (testGameService.getInstancesOfGame().size() == 0
        && DungeonGameEngine.getInstanceId() == 0);
  }

  @Test
  public void testDestroyNegativeID(){

    assertFalse(testGameService.destroyGame(-1));
    assert (testGameService.getInstancesOfGame().size() == 0
        && DungeonGameEngine.getInstanceId() == 0);
  }

  @Test
  public void testDestroyCorrectInstanceGivenMultiple() throws AdventureException {

    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();

    assertTrue(testGameService.destroyGame(2));

    Map<Integer, DungeonGameEngine> currentInstances = testGameService.getInstancesOfGame();

    assertTrue(currentInstances.containsKey(1) && currentInstances.containsKey(3));
    assert (testGameService.getInstancesOfGame().size() == 2
        && DungeonGameEngine.getInstanceId() == 3);
  }

  // Test getGame()

  @Test(expected = IllegalArgumentException.class)
  public void testGetInstanceDNE() {

    testGameService.getGame(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNegativeId() throws AdventureException {

    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();

    GameStatus expectedGame = testGameService.getGame(-1);
  }

  @Test
  public void testValidGetSingleInstance() throws AdventureException {
    testGameService.newGame();

    GameStatus expectedGame = testGameService.getGame(1);

    assertEquals(expectedGame.getId(), 1);
  }

  @Test
  public void testValidGetCorrectInstanceGivenMultiple() throws AdventureException {

    testGameService.newGame();
    testGameService.newGame();
    testGameService.newGame();

    GameStatus actualGame = testGameService.getGame(2);

    assertEquals(2, actualGame.getId());
  }

  // Test executeCommand()
  /**
   * Since tested for correct state changes for all of the various inputs in DungeonGameEngineTest already,
   * it is unnecessary to test if all of the commands properly change the state of DungeonGameEngine for executeCommand.
   * Instead, test if command is executed on the proper instance.
   * And just to be safe, check one state change given a command.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteInstanceDNE() {

    testGameService.executeCommand(1, new Command("", ""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteNegativeID() {

    testGameService.executeCommand(-1, new Command("", ""));
  }

  @Test
  public void testExecuteMultipleInstance() throws AdventureException {

    testGameService.newGame();

    String expectedOutputMessage =
        "===Empty Room===\n"
            + "You walk up a stairway and enter a room with nothing in it.\n"
            + "\n"
            + "From here, you can go: forward, backward, left\n"
            + "Items Visible: (nothing)";

    testGameService.executeCommand(1, new Command("go", "forward"));
    String actualOutputMessage = testGameService.getGame(1).getMessage();

    assertEquals(expectedOutputMessage, actualOutputMessage);
  }

  @Test
  public void testExecuteCorrectInstanceGivenMultiple() throws AdventureException {

    testGameService.newGame();
    testGameService.newGame();

    String expectedExecutedOutputMessage =
        "===Empty Room===\n"
            + "You walk up a stairway and enter a room with nothing in it.\n"
            + "\n"
            + "From here, you can go: forward, backward, left\n"
            + "Items Visible: (nothing)";

    String expectedUnexecutedOutputMessage =
        "===Three-door Room===\n"
            + "You fell down a deep hole and are now stuck in a room with three dungeon doors.\n"
            + "You have no choice but explore and try to find your way out.\n"
            + "\n"
            + "From here, you can go: forward, right, left\n"
            + "Items Visible: (nothing)";

    testGameService.executeCommand(1, new Command("go", "forward"));

    String actualExecutedOutputMessage = testGameService.getGame(1).getMessage();
    String actualUnexecutedOutputMessage2 = testGameService.getGame(2).getMessage();

    assertEquals(expectedExecutedOutputMessage, actualExecutedOutputMessage);
    assertEquals(expectedUnexecutedOutputMessage, actualUnexecutedOutputMessage2);
  }
}
