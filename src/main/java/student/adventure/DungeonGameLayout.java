package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DungeonGameLayout {

    @SerializedName("startingRoom")
    private String startingRoomName;
    @SerializedName("endingRoom")
    private String endingRoomName;
    private ArrayList<Room> rooms;

    public String getStartingRoomName() {
        return startingRoomName;
    }

    public String getEndingRoomName() {
        return endingRoomName;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Room getStartingRoom() {
        return searchForTargetRoom(startingRoomName);
    }

    public Room searchForTargetRoom(String name) {
        for (Room targetRoom : rooms) {
            if (targetRoom.getRoomName().equals(name)) {
                return targetRoom;
            }
        }
        return null;
    }
}
