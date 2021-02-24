package student.server;

import student.adventure.Direction;
import student.adventure.DungeonGameEngine;
import student.adventure.Item;
import student.adventure.Room;

import java.util.*;

public class DungeonGameService implements AdventureService {

  private final HashMap<Integer, DungeonGameEngine> instancesOfGame = new HashMap<>();

  @Override
  public void reset() {

    DungeonGameEngine.resetID();
    instancesOfGame.clear();
  }

  @Override
  public int newGame() throws AdventureException {

    DungeonGameEngine newGameInstance =
        new DungeonGameEngine("src/main/resources/dungeongameroomformat.json");

    if (newGameInstance.hasError()) {
      throw new AdventureException("There was an error parsing the JSON file");
    }

    instancesOfGame.put(DungeonGameEngine.getInstanceId(), newGameInstance);
    return DungeonGameEngine.getInstanceId();
  }

  @Override
  public GameStatus getGame(int id) {

    DungeonGameEngine targetGame = instancesOfGame.get(id);

    if(targetGame == null) {
      throw new IllegalArgumentException("No game associated with ID");
    }

    return new GameStatus(
        targetGame.hasError(),
        id,
        targetGame.getOutputMessage(),
        null,
        null,
        new AdventureState(),
        getValidCommands(targetGame));
  }

  @Override
  public boolean destroyGame(int id) {

    if(instancesOfGame.containsKey(id)) {
      instancesOfGame.remove(id);
      return true;
    }

    return false;
  }

  @Override
  public void executeCommand(int id, Command command) {

    DungeonGameEngine targetGame = instancesOfGame.get(id);
    if(targetGame == null) {
      throw new IllegalArgumentException("No game associated with ID");
    }

    instancesOfGame.get(id).inputCommand(command);
  }

  @Override
  public SortedMap<String, Integer> fetchLeaderboard() {
    return null;
  }

  // given an instance of a game, find all of the valid commands associated to the game
  private Map<String, List<String>> getValidCommands(DungeonGameEngine targetInstance) {

    Room instanceCurrentRoom = targetInstance.getCurrentRoom();

    HashMap<String, List<String>> possibleCommands = new HashMap<>();
    for (String commandName : DungeonGameEngine.getPossibleCommands()) {

      possibleCommands.put(commandName, new ArrayList<>());
    }

    // adds empty string so the single argument commands show up in the API
    possibleCommands.get("examine").add("");
    possibleCommands.get("restart").add("");

    //only add unlock if the room is locked
    if (instanceCurrentRoom.isLocked()) {
      possibleCommands.get("unlock").add("");
    }

    //add all of the possible options for multiple argument commands
    for (Direction targetDirection : instanceCurrentRoom.getDirections()) {

      possibleCommands.get("go").add(targetDirection.getDirectionName());
    }

    for (Item targetItem : instanceCurrentRoom.getItems()) {

      possibleCommands.get("take").add(targetItem.getItemName());
    }

    for (Item targetItem : targetInstance.getInventory()) {

      possibleCommands.get("drop").add(targetItem.getItemName());
    }

    return possibleCommands;
  }

  public HashMap<Integer, DungeonGameEngine> getInstancesOfGame() {
    return instancesOfGame;
  }
}
