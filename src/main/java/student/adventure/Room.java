package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Room {

    @SerializedName("name")
    private String roomName;
    @SerializedName("description")
    private String roomDescription;

    private ArrayList<Direction> directions;
    private ArrayList<Item> items;

    @SerializedName("willLockWhenEntered")
    private boolean willLock;
    @SerializedName("requiredItemsToUnlockRoom")
    private ArrayList<Item> requiredItems;

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean isLocked() {
        return willLock;
    }

    public ArrayList<Item> getRequiredItems() {
        return requiredItems;
    }

    //removes an item from the room
    public void takeItem(Item toTake) {

        items.remove(toTake);
    }

    //adds the given item to the room
    public void addItem(Item toAdd) {
        items.add(toAdd);
    }

    //changes willLock to false
    public void unlock() {
        willLock = false;
    }

    //checks if the inventory contains all of the requiredItems
    public boolean hasRequiredItems(ArrayList<Item> inventory) {
        return inventory.containsAll(requiredItems);
    }

    //finds the associated roomName associated to a direction for the current room
    public String findRoomName(String directionName) {

        for (Direction targetDirection : directions) {
            if (targetDirection.getDirectionName().equals(directionName)) {

                return targetDirection.getRoomName();
            }
        }

        return null;
    }

    //lists all of the valid directions for the room
    public String listDirections() {

        StringBuilder toReturn = new StringBuilder("From here, you can go:");
        for (Direction targetDirection : directions) {
            toReturn.append(" ");
            toReturn.append(targetDirection.getDirectionName());
            toReturn.append(",");
        }

        int lastChar = toReturn.lastIndexOf(",");
        if (lastChar < 0) {
            return toReturn.toString();
        }

        return toReturn.substring(0, lastChar);
    }

    //list all of the items located in the room
    public String listItems() {

        StringBuilder toReturn = new StringBuilder("Items Visible:");
        for (Item targetItem : items) {
            toReturn.append(" ");
            toReturn.append(targetItem.getItemName());
            toReturn.append(",");
        }

        int lastChar = toReturn.lastIndexOf(",");
        if (lastChar < 0) {
            toReturn.append(" (nothing)");
            return toReturn.toString();
        }

        return toReturn.substring(0, lastChar);
    }

    //prints all of the necessary stats of the room
    public String toString() {

        String toReturn = "===" +getRoomName() + "===\n";
        toReturn += roomDescription + "\n";
        toReturn += "\n" + listDirections();
        toReturn += "\n" + listItems();

        return toReturn;
    }
}
