package student.adventure;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DungeonGameUI {

    public DungeonGameEngine gameEngine;
    public final String ESCAPED_DUNGEON = "Escaped!";
    public final String EXIT_GAME = "Goodbye";
    public final List<String> VALID_DIRECTIONS = Arrays.asList("north", "east", "south", "west");

    public DungeonGameUI(String path) {
        gameEngine = new DungeonGameEngine(path);
    }

    public void runGame() {

        Scanner input = new Scanner(System.in);

        String commandOutput = "";
        while(!commandOutput.equals(EXIT_GAME) || !commandOutput.equals(ESCAPED_DUNGEON)) {

            System.out.print("> ");
            String inputCommand = input.nextLine();

            String[] splitCommand = cleanCommandAndArguments(inputCommand.toLowerCase());

            System.out.println(commandOutput);
        }
    }

    private String[] cleanCommandAndArguments(String input) {

        input = input.trim();
        String[] inputArgs = input.split("\\s+");

        StringBuilder arguments = new StringBuilder();
        for(int i = 1; i < inputArgs.length; i++) {

            arguments.append(inputArgs[i]);
            arguments.append(" ");
        }

        return new String[] {inputArgs[0], arguments.toString().trim()};
    }
}
