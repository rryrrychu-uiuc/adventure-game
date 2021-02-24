package student.adventure;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

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

    /** Obtains the room object of the name of the starting room given in the layout. */
    public Room getStartingRoom() {

        return searchForTargetRoom(startingRoomName);
    }

    /**Obtains the room object of the name of the ending room given in the layout.*/
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

    /**
     * Determine what achievements a player obtained given a map of collected itemTypes
     *
     * @param numOfItemType a map containing the amount of each type of item a player got
     * @return a string congratulating the player for every obtained achievement
     */
    public String determineEarnedAchievements(Map<String, Integer> numOfItemType) {

        StringBuilder toReturn = new StringBuilder("Obtained Achievements:");
        for(Achievement targetAchievement: achievements) {

            int obtainedItems = numOfItemType.get(targetAchievement.getItemType());
            if(obtainedItems >= targetAchievement.getMinRequiredItems()) {
                toReturn.append("\n");
                toReturn.append(targetAchievement.getAchievementMessage());
            }
        }

        return toReturn.toString();
    }
}
