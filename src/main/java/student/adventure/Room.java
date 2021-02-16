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

    private boolean willLockWhenEntered;
    @SerializedName("requiredItemsToUnlockRoom")
    private ArrayList<Item> itemsRequiredToUnlockRoom;

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
        return willLockWhenEntered;
    }

    public ArrayList<Item> getItemsRequiredToUnlockRoom() {
        return itemsRequiredToUnlockRoom;
    }

    public void takeItem(Item toTake) {

        items.remove(toTake);
    }

    public void addItem(Item toAdd) {
        items.add(toAdd);
    }

    public void unlock() {
        willLockWhenEntered = false;
    }

    public boolean hasRequiredItems(ArrayList<Item> inventory) {

        int numOfItems = 0;
        for(Item toCheck: itemsRequiredToUnlockRoom) {
            String itemName = toCheck.getItemName();
            for(Item toCompare: inventory) {
                if(toCompare.getItemName().equals(itemName)) {
                    numOfItems++;
                }
            }
        }

        return numOfItems == itemsRequiredToUnlockRoom.size();
    }

    public String listDirections() {

        StringBuilder toReturn = new StringBuilder();
        for (Direction targetDirection : directions) {
            toReturn.append(" ");
            toReturn.append(targetDirection.getDirectionName());
            toReturn.append(",");
        }

        return toReturn.substring(0, toReturn.length() - 1);
    }

    public String listItems() {

        StringBuilder toReturn = new StringBuilder();
        for (Item targetItem : items) {
            toReturn.append(" ");
            toReturn.append(targetItem.getItemName());
            toReturn.append(",");
        }

        if(toReturn.toString().length() == 0) {
            return "";
        }

        return toReturn.substring(0, toReturn.length() - 1);
    }

    public String toString() {

        String toReturn = "===" +getRoomName() + "===\n";
        toReturn += roomDescription + "\n";
        toReturn += "\nFrom here, you can go:" + listDirections();
        toReturn += "\nItems Visible:" + listItems();

        return toReturn;
    }
}
