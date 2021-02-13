package student.adventure;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DungeonGameEngine {

    private Room currentRoom;
    private DungeonGameLayout mapLayout;

    public DungeonGameEngine(String path) {
        Gson gson = new Gson();
        try {
            Reader jsonFile = Files.newBufferedReader(Paths.get(path));
            mapLayout = gson.fromJson(jsonFile, DungeonGameLayout.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid path");
        }

        currentRoom = mapLayout.getStartingRoom();
    }
}
