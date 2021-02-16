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

    public DungeonGameEngine(String path) {

        Gson gson = new Gson();
        try {

            Reader jsonFile = Files.newBufferedReader(Paths.get(path));
            mapLayout = gson.fromJson(jsonFile, DungeonGameLayout.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid path");
        }

        currentRoom = mapLayout.getStartingRoom();
        inventory = new ArrayList<>();

        System.out.println(currentRoom.toString());
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
}
