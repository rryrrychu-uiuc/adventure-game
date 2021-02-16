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
            return "Room is locked.";
        }

        if(directionName.equals("Leave the dungeon")) {
            return "Escaped!";
        }

        String roomName = findRoomName(directionName);
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

        return "";
    }

    /**
     * Takes an item and adds it to the player inventory.
     *
     * @param itemName name of the item the player wants to obtain from the room
     * @return the description of the item
     */
    public String takeItem(String itemName) {

        return takeItemHelper(itemName, currentRoom.getItems());
    }

    //helper to takeItem since the UI can't access currentRoom's items
    private String takeItemHelper(String itemName, ArrayList<Item> itemLocation) {

        Item toTake = findItem(itemName, itemLocation);

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
    public String dropItem(String itemName) {

        return dropItemHelper(itemName, inventory);
    }

    //helper to dropItem since the UI can't access inventory
    private String dropItemHelper(String itemName, ArrayList<Item> itemLocation) {

        Item toDrop = findItem(itemName, itemLocation);

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
    public void restartGame() {

        loadGame();
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

        return toReturn.substring(0, toReturn.length() - 1);
    }
}
