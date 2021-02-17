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

    // returns the stats of the current room
    private String examineRoom() {

        return currentRoom.toString();
    }

    //changes the currentRoom to a new room given the direction of the new room
    private String moveRooms(String directionName) {

        if (currentRoom.isLocked()) {
            return "Cannot leave the room. The room is locked.";
        }

        if(directionName.equals("leave the dungeon")) {
            return "You have escaped!";
        }

        String roomName = currentRoom.findRoomName(directionName);
        if(roomName == null) {
            return "Cannot go in the direction '" + directionName + "'";
        }

        currentRoom = mapLayout.searchForTargetRoom(roomName);
        return currentRoom.toString();
    }

    //takes an item from the current room and adds it to the inventory
    private String takeItem(String itemName, ArrayList<Item> itemLocation) {

        Item toTake = findItem(itemName, itemLocation);
        if(toTake == null) {
            return "Room does not contain '" + itemName + "'";
        }

        currentRoom.takeItem(toTake);
        inventory.add(toTake);

        return toTake.getItemDescription();
    }

    //takes an item from the inventory and adds it to the current room
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

    //unlocks the current room if its not already unlocked and the player has the necessary items
    private String unlockRoom() {

        if(!currentRoom.isLocked()) {
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

    //returns a string containing all of the items in inventory
    private String viewInventory() {

        StringBuilder toReturn = new StringBuilder("Inventory:  ");
        for (Item targetItem : inventory) {
            toReturn.append(targetItem.getItemName());
            toReturn.append(",");
            toReturn.append(" ");
        }

        int lastChar = toReturn.lastIndexOf(",");
        if (lastChar < 0) {
            toReturn.append(" (nothing)");
            return toReturn.toString();
        }

        return toReturn.substring(0, lastChar);
    }

}
