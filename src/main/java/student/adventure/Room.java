package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Room is a storage object that maintains different stats about a certain room
 *
 * @author Harry Chu
 */
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

        return Item.containsAllItems(inventory, requiredItems);
    }

    //finds the associated roomName associated to a direction for the current room
    public String findRoomName(String directionName) {

        if(directions == null) {
            return null;
        }

        for (Direction targetDirection : directions) {
            if (targetDirection.getDirectionName().equals(directionName)) {

                return targetDirection.getRoomName();
            }
        }

        return null;
    }

    //lists all of the valid directions for the room
    private String listDirections() {

        StringBuilder toReturn = new StringBuilder("From here, you can go:");
        if(directions != null) {
            for (Direction targetDirection : directions) {
                toReturn.append(" ");
                toReturn.append(targetDirection.getDirectionName());
                toReturn.append(",");
            }
        }

        int lastChar = toReturn.lastIndexOf(",");
        if (lastChar < 0) {
            return toReturn.toString();
        }

        return toReturn.substring(0, lastChar);
    }

    //list all of the items located in the room
    private String listItems() {

        StringBuilder toReturn = new StringBuilder("Items Visible:");
        if(items != null) {
            for (Item targetItem : items) {
                toReturn.append(" ");
                toReturn.append(targetItem.getItemName());
                toReturn.append(",");
            }
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

        String toReturn = "===" + getRoomName() + "===\n";
        toReturn += roomDescription + "\n";
        toReturn += "\n" + listDirections();
        toReturn += "\n" + listItems();

        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Room targetRoom) {
            boolean nameIsEqual = roomName.equals(targetRoom.roomName);
            boolean descriptionIsEqual = roomDescription.equals(targetRoom.roomDescription);
            boolean directionsAreEqual = directions.equals(targetRoom.directions);
            boolean itemsAreEqual = items.equals(targetRoom.items);
            boolean willLockIsEqual = willLock == targetRoom.willLock;
            boolean requiredItemsAreEqual = requiredItems.equals(targetRoom.requiredItems);
            return nameIsEqual && descriptionIsEqual && directionsAreEqual && itemsAreEqual && willLockIsEqual && requiredItemsAreEqual;
        }

        return false;
    }
}
