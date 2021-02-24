package student.adventure;

import com.google.gson.Gson;
import student.server.Command;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * DungeonGameEngine maintains most of the logic that interacts and changes the room based on what a player does
 *
 * @author Harry Chu
 */
public class DungeonGameEngine {

    private final String RESTART_GAME_FILE_PATH;
    private final String ESCAPE_MESSAGE = "You have escaped the dungeon! Congratulations!";
    private final String ESCAPE_COMMAND = "Leave the dungeon";

    //Possible commands
    private static final String QUIT_CMD = "quit";
    private static final String EXIT_CMD = "exit";
    private static final String EXAMINE_CMD = "examine";
    private static final String GO_CMD = "go";
    private static final String TAKE_CMD = "take";
    private static final String DROP_CMD = "drop";
    private static final String UNLOCK_CMD = "unlock";
    private static final String INVENTORY_CMD = "inventory";
    private static final String RESTART_CMD = "restart";
    private static final List<String> POSSIBLE_COMMANDS = Arrays.asList(new String[]{QUIT_CMD, EXIT_CMD, EXAMINE_CMD, GO_CMD, TAKE_CMD, DROP_CMD, UNLOCK_CMD, INVENTORY_CMD, RESTART_CMD});

    private Room currentRoom;
    private DungeonGameLayout mapLayout;
    private ArrayList<Item> inventory;
    private boolean hasError = false;
    private String outputMessage;

    public static int INSTANCE_ID = 0;

    public DungeonGameEngine(String path) {

        RESTART_GAME_FILE_PATH = path;
        loadGame();
        outputMessage = currentRoom.toString();
        DungeonGameEngine.INSTANCE_ID++;
    }

    /**
     * Given a valid command, preform the task associated with the command
     *
     * @param targetCommand the command that is supposed to be run
     * @return a string containing result of the desired task
     */
    public String inputCommand(Command targetCommand) {

        if(targetCommand == null) {
            return "CommandError: Command cannot be null";
        }

        switch (targetCommand.getCommandName()) {
            case QUIT_CMD:
            case EXIT_CMD:
                return "Goodbye!";
            case EXAMINE_CMD:
                return examineRoom();
            case GO_CMD:
                return moveRooms(targetCommand.getCommandValue());
            case TAKE_CMD:
                return takeItem(targetCommand.getCommandValue(), currentRoom.getItems());
            case DROP_CMD:
                return dropItem(targetCommand.getCommandValue(), inventory);
            case UNLOCK_CMD:
                return unlockRoom();
            case INVENTORY_CMD:
                return viewInventory();
            case RESTART_CMD:
                return restartGame();
            default:
                return "CommandError: '" + targetCommand + "' is not a valid command";
        }
    }

    // returns the stats of the current room
    private String examineRoom() {

        outputMessage = currentRoom.toString();
        return outputMessage;
    }

    //changes the currentRoom to a new room given the direction of the new room
    private String moveRooms(String directionName) {

        if (currentRoom.isLocked()) {
            outputMessage = "Cannot leave the room. The room is locked.";
            return outputMessage;
        }

        String roomName = currentRoom.findRoomName(directionName);
        if (roomName == null) {
            outputMessage = "Cannot go in the direction '" + directionName + "'";
            return outputMessage;
        }

        if (directionName.equals(ESCAPE_COMMAND) && currentRoom.equals(mapLayout.getEndingRoom())) {
            currentRoom = mapLayout.searchForTargetRoom(roomName);
            outputMessage = ESCAPE_MESSAGE + "\n" + getObtainedAchievements();
            return outputMessage;
        }

        currentRoom = mapLayout.searchForTargetRoom(roomName);
        outputMessage = currentRoom.toString();
        return outputMessage;
    }

    //takes an item from the current room and adds it to the inventory
    private String takeItem(String itemName, ArrayList<Item> itemLocation) {

        Item toTake = Item.findItem(itemName, itemLocation);
        if (toTake == null) {
            outputMessage = "Room does not contain '" + itemName + "'";
            return outputMessage;
        }

        currentRoom.takeItem(toTake);
        inventory.add(toTake);

        outputMessage = toTake.getItemDescription();
        return outputMessage;
    }

    //takes an item from the inventory and adds it to the current room
    private String dropItem(String itemName, ArrayList<Item> itemLocation) {

        Item toDrop = Item.findItem(itemName, itemLocation);
        if (toDrop == null) {
            return "Inventory does not contain '" + itemName + "'";
        }

        inventory.remove(toDrop);
        currentRoom.addItem(toDrop);

        outputMessage = "You have dropped " + toDrop.getItemName();
        return outputMessage;
    }

    //unlocks the current room if its not already unlocked and the player has the necessary items
    private String unlockRoom() {

        if (!currentRoom.isLocked()) {
            outputMessage = "Room is not locked.";
            return outputMessage;
        }

        if (!currentRoom.hasRequiredItems(inventory)) {
            outputMessage = "You do not have the necessary items to *unlock* the doors in this room. "
                    + "You must *restart* the game";
            return outputMessage;
        }

        currentRoom.unlock();

        outputMessage = "Room unlocked! You may now *go* in any valid direction and leave the room.";
        return outputMessage;
    }

    //reloads the game from the JSON to restart the game
    private String restartGame() {

        loadGame();
        outputMessage = currentRoom.toString() + "\n" + "Game restarted!";
        return outputMessage;
    }

    //builds then returns the string containing all of the items in inventory
    private String viewInventory() {

        StringBuilder toReturn = new StringBuilder("Inventory: ");
        for (Item targetItem : inventory) {
            toReturn.append(targetItem.getItemName());
            toReturn.append(",");
            toReturn.append(" ");
        }

        int lastChar = toReturn.lastIndexOf(",");
        if (lastChar < 0) {
            toReturn.append("(nothing)");
            return toReturn.toString();
        }

        outputMessage = toReturn.substring(0, lastChar);
        return outputMessage;
    }

    //given the instance variable, restart the current game from the file
    private void loadGame() {

        Gson gson = new Gson();
        try {
            Reader jsonFile = Files.newBufferedReader(Paths.get(RESTART_GAME_FILE_PATH));

            mapLayout = gson.fromJson(jsonFile, DungeonGameLayout.class);
            DungeonGameLayoutValidator.validateDeserialization(mapLayout);

        } catch (IOException e) {
            hasError = true;
            throw new IllegalArgumentException("Path is invalid");
        } catch (IllegalArgumentException e) {
            hasError = true;
            throw e;
        }

        currentRoom = mapLayout.getStartingRoom();
        inventory = new ArrayList<>();
    }

    //Notifies the player of any achievements they may have gotten from collecting items
    private String getObtainedAchievements() {

        if(mapLayout.getAchievements() == null) {
            outputMessage = "";
            return outputMessage;
        }

        HashMap<String, Integer> numOfItemType = Item.tallyItemTypes(inventory);

        outputMessage = mapLayout.determineEarnedAchievements(numOfItemType);
        return outputMessage;
    }

    public Room getCurrentRoom() {

        return currentRoom;
    }

    public Room getStartingRoom() {

        return mapLayout.getStartingRoom();
    }

    public boolean hasError() {

        return hasError;
    }

    public String getOutputMessage() {

        return outputMessage;
    }

    public ArrayList<Item> getInventory() {

        return inventory;
    }

    public static void resetID() {

        INSTANCE_ID = 0;
    }

    public static int getInstanceId() {

        return INSTANCE_ID;
    }

    public static List<String> getPossibleCommands() {

        return POSSIBLE_COMMANDS;
    }
}
