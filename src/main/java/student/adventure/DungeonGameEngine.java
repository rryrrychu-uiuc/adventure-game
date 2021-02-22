package student.adventure;

import com.google.gson.Gson;
import student.server.Command;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DungeonGameEngine maintains most of the logic that interacts and changes the room based on what a player does
 *
 * @author Harry Chu
 */
public class DungeonGameEngine {

    private final String RESTART_GAME_FILE_PATH;
    private final String ESCAPE_MESSAGE = "You have escaped the dungeon! Congratulations!";
    private Room currentRoom;
    private DungeonGameLayout mapLayout;
    private ArrayList<Item> inventory;

    public static int INSTANCE_ID = 0;

    public DungeonGameEngine(String path) {

        RESTART_GAME_FILE_PATH = path;
        loadGame();
        DungeonGameEngine.INSTANCE_ID++;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Room getStartingRoom() {
        return mapLayout.getStartingRoom();
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
            case "quit":
            case "exit":
                return "Goodbye!";
            case "examine":
                return examineRoom();
            case "go":
                return moveRooms(targetCommand.getCommandValue());
            case "take":
                return takeItem(targetCommand.getCommandValue(), currentRoom.getItems());
            case "drop":
                return dropItem(targetCommand.getCommandValue(), inventory);
            case "unlock":
                return unlockRoom();
            case "inventory":
                return viewInventory();
            case "restart":
                return restartGame();
            default:
                return "CommandError: '" + targetCommand + "' is not a valid command";
        }
    }

    // returns the stats of the current room
    private String examineRoom() {

        return currentRoom.toString();
    }

    //changes the currentRoom to a new room given the direction of the new room
    private String moveRooms(String directionName) {

        if (currentRoom.isLocked()) {
            return "Cannot leave the room. The room is locked.";
        }

        if (directionName.equals("leave the dungeon") && currentRoom.equals(mapLayout.getEndingRoom())) {
            return ESCAPE_MESSAGE + "\n" + getObtainedAchievements();
        }

        String roomName = currentRoom.findRoomName(directionName);
        if (roomName == null) {
            return "Cannot go in the direction '" + directionName + "'";
        }

        currentRoom = mapLayout.searchForTargetRoom(roomName);
        return currentRoom.toString();
    }

    //takes an item from the current room and adds it to the inventory
    private String takeItem(String itemName, ArrayList<Item> itemLocation) {

        Item toTake = findItem(itemName, itemLocation);
        if (toTake == null) {
            return "Room does not contain '" + itemName + "'";
        }

        currentRoom.takeItem(toTake);
        inventory.add(toTake);

        return toTake.getItemDescription();
    }

    //takes an item from the inventory and adds it to the current room
    private String dropItem(String itemName, ArrayList<Item> itemLocation) {

        Item toDrop = findItem(itemName, itemLocation);
        if (toDrop == null) {
            return "Inventory does not contain '" + itemName + "'";
        }

        inventory.remove(toDrop);
        currentRoom.addItem(toDrop);

        return "You have dropped " + toDrop.getItemName();
    }

    //finds an item given the itemName and where to look
    private Item findItem(String itemName, ArrayList<Item> itemLocation) {

        if(itemLocation == null) {
            return null;
        }

        for (Item targetItem : itemLocation) {
            if (targetItem.getItemName().equals(itemName)) {

                return targetItem;
            }
        }

        return null;
    }

    //unlocks the current room if its not already unlocked and the player has the necessary items
    private String unlockRoom() {

        if (!currentRoom.isLocked()) {
            return "Room is not locked.";
        }

        if (!currentRoom.hasRequiredItems(inventory)) {
            return "You do not have the necessary items to *unlock* the doors in this room. "
                    + "You must *restart* the game";
        }

        currentRoom.unlock();
        return "Room unlocked! You may now *go* in any valid direction and leave the room.";
    }

    //reloads the game from the JSON to restart the game
    private String restartGame() {

        loadGame();
        return "Game restarted!";
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

        return toReturn.substring(0, lastChar);
    }

    //given the instance variable, restart the current game from the file
    private void loadGame() {

        Gson gson = new Gson();
        try {
            Reader jsonFile = Files.newBufferedReader(Paths.get(RESTART_GAME_FILE_PATH));

            mapLayout = gson.fromJson(jsonFile, DungeonGameLayout.class);

        } catch (IOException e) {
            throw new IllegalArgumentException("Path is invalid");
        }

        DungeonGameLayoutValidator.validateDeserialization(mapLayout);

        currentRoom = mapLayout.getStartingRoom();
        inventory = new ArrayList<>();

        System.out.println(currentRoom.toString());
    }

    //Notifies the player of any achievements they may have gotten from collecting items
    private String getObtainedAchievements() {

        if(mapLayout.getAchievements() == null) {
            return "";
        }

        StringBuilder toReturn = new StringBuilder("Obtained Achievements:");

        //totals all of the itemTypes a player received
        HashMap<String, Integer> numOfItemType = new HashMap<>();
        for(Item targetItem: inventory) {

            String itemType = targetItem.getItemType();
            if(numOfItemType.containsKey(itemType)) {
                numOfItemType.put(itemType, numOfItemType.get(itemType) + 1);
            } else {
                numOfItemType.put(itemType, 1);
            }
        }

        //checks if the totals meet the required number of the achievement
        for(Achievement targetAchievement: mapLayout.getAchievements()) {

            int obtainedItems = numOfItemType.get(targetAchievement.getItemType());
            if(obtainedItems >= targetAchievement.getMinRequiredItems()) {
                toReturn.append("\n");
                toReturn.append(targetAchievement.getAchievementMessage());
            }
        }

        return toReturn.toString();
    }

    public static void resetID() {
        INSTANCE_ID = 0;
    }

    public static int getInstanceId() {
        return INSTANCE_ID;
    }
}
