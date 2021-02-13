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

        if (toReturn.length() == 0) {
            return "";
        }

        return toReturn.substring(0, toReturn.length() - 1);
    }

    public String toString() {
        String toReturn = "";
        toReturn += (roomName + ": " + roomDescription);
        toReturn += "\n" + "Possible Directions:" + listDirections();
        toReturn += "\n" + "Items Visible:" + listItems();

        return toReturn;
    }
}
