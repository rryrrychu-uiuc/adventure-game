package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * DungeonGameLayout is the primary object that gets deserialized for the game
 *
 * @author Harry Chu
 */
public class DungeonGameLayout {

    @SerializedName("startingRoom")
    private String startingRoomName;
    @SerializedName("endingRoom")
    private String endingRoomName;
    private String gameType;
    private ArrayList<Room> rooms;
    private ArrayList<Achievement> achievements;

    public String getStartingRoomName() {
        return startingRoomName;
    }

    public String getEndingRoomName() {
        return endingRoomName;
    }

    public String getGameType() {
        return gameType;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    /**
     * Obtains the room object of the name of the starting room given in the layout.
     *
     * @return the room whose name matches the instance variable startingRoomName
     */
    public Room getStartingRoom() {
        return searchForTargetRoom(startingRoomName);
    }

    /**
     * Obtains the room object of the name of the ending room given in the layout.
     *
     * @return the room whose name matches the instance variable endingRoomName
     */
    public Room getEndingRoom() {
        return searchForTargetRoom(endingRoomName);
    }

    /**
     * Finds a room given a name.
     *
     * @param roomName the room of the name wanted to be found
     * @return the room object with the associated name
     */
    public Room searchForTargetRoom(String roomName) {
        for (Room targetRoom : rooms) {
            if (targetRoom.getRoomName().equals(roomName)) {
                return targetRoom;
            }
        }

        return null;
    }
}
