package student.adventure;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DungeonGameEngine {

    private Room currentRoom;
    private DungeonGameLayout mapLayout;
    private ArrayList<Item> inventory;
    private String restartGameFilePath;

    public DungeonGameEngine(String path) {

        restartGameFilePath = path;
        loadGame();
    }

    //Takes in an array of strings as a command and preforms the desired task
    public String inputCommand(String commandName, String arguments) {

        switch(commandName) {
            case "quit":
            case "exit":
                return "Goodbye";
            case "examine":
                return examineRoom();
            case "go":
                return moveRooms(arguments);
            case "take":
                return takeItem(arguments, currentRoom.getItems());
            case "drop":
                return dropItem(arguments, inventory);
            case "unlock":
                return unlockRoom();
            case "inventory":
                return viewInventory();
            case "restart":
                return restartGame();
            default:
                return "CommandError: '" + commandName + arguments + "' is not a valid command";
        }
    }

    /**
     * Provides the stats of currentRoom
     *
     * @return the description, list of directions, and list of items in the present room as a string
     */
    public String examineRoom() {

        return currentRoom.toString();
    }

    /**
     * Changes the currentRoom to a new room.
     *
     * @param directionName the name of the direction the player wants to move in
     * @return The description, direction, and items contained in the new room
     */
    public String moveRooms(String directionName) {

        if (currentRoom.isLocked()) {
            return "Cannot leave the room. The room is locked.";
        }

        if(directionName.equals("leave the dungeon")) {
            return "6You have escaped!";
        }

        String roomName = findRoomName(directionName);
        if(roomName == null) {
            return "Cannot go in the direction '" + directionName + "'";
        }

        currentRoom = mapLayout.searchForTargetRoom(roomName);
        return currentRoom.toString();
    }

    //finds the associated roomName associated to a direction for the current room
    private String findRoomName(String directionName) {

        for (Direction targetDirection : currentRoom.getDirections()) {
            if (targetDirection.getDirectionName().equals(directionName)) {

                return targetDirection.getRoomName();
            }
        }

        return null;
    }

    /**
     * Takes an item and adds it to the player inventory.
     *
     * @param itemName name of the item the player wants to obtain from the room
     * @return the description of the item
     */
    private String takeItem(String itemName, ArrayList<Item> itemLocation) {

        Item toTake = findItem(itemName, itemLocation);
        if(toTake == null) {
            return "Room does not contain '" + itemName + "'";
        }

        currentRoom.takeItem(toTake);
        inventory.add(toTake);

        return toTake.getItemDescription();
    }

    /**
     * Takes an item from the player inventory and adds it to the room.
     *
     * @param itemName name of the item the player wants to drop from inventory
     * @return the name of the item dropped
     */
    private String dropItem(String itemName, ArrayList<Item> itemLocation) {

        Item toDrop = findItem(itemName, itemLocation);
        if(toDrop == null) {
            return "Inventory does not contain '" + itemName + "'";
        }

        inventory.remove(toDrop);
        currentRoom.addItem(toDrop);

        return "You have dropped " + toDrop.getItemName();
    }

    //finds an item given the itemName
    private Item findItem(String itemName, ArrayList<Item> itemLocation) {

        for (Item targetItem : itemLocation) {
            if (targetItem.getItemName().equals(itemName)) {

                return targetItem;
            }
        }

        return null;
    }

    /**
     * Unlock the current room given a player has the correct items
     *
     * @return statement telling the player whether the room was unlocked
     */
    public String unlockRoom() {

        if(!currentRoom.isLocked()) {
            return "Room is not locked.";
        }

        if (!currentRoom.hasRequiredItems(inventory)) {
            return "You do not have the necessary items to unlock the doors in this room. Please type 'restart' to restart the game";
        }

        currentRoom.unlock();
        return "Room unlocked!";
    }

    //reloads the game from the JSON to restart the game
    public String restartGame() {

        loadGame();
        return "Game restarted!";
    }

    //given the instance variable, restart the current game from the file
    private void loadGame() {

        Gson gson = new Gson();
        try {
            Reader jsonFile = Files.newBufferedReader(Paths.get(restartGameFilePath));
            mapLayout = gson.fromJson(jsonFile, DungeonGameLayout.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid path");
        }

        currentRoom = mapLayout.getStartingRoom();
        inventory = new ArrayList<>();

        System.out.println(currentRoom.toString());
    }

    /**
     * Finds all of the items in the players inventory
     *
     * @return a string list of the items in the player's inventory
     */
    public String viewInventory() {

        StringBuilder toReturn = new StringBuilder("Inventory: ");
        for (Item targetItem : inventory) {

            toReturn.append(targetItem.getItemName());
            toReturn.append(",");
            toReturn.append(" ");
        }

        return toReturn.substring(0, toReturn.length() - 2);
    }

}
