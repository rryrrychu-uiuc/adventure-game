import student.adventure.DungeonGameEngine;
import student.adventure.DungeonGameUI;

public class Main {
    public static void main(String[] args) {
        DungeonGameUI dge = new DungeonGameUI("src/main/resources/dungeongameroomformat.json");

        dge.runGame();
    }
}
