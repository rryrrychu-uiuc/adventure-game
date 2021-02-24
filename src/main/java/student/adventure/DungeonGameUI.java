package student.adventure;

import student.server.Command;

import java.util.Scanner;

/**
 * DungeonGameUI runs the DungeonGameEngine and separates the command string parsing from the game
 * logic
 *
 * @author Harry Chu
 */
public class DungeonGameUI {

  private final String ESCAPED_DUNGEON = "You have escaped the dungeon! Enjoy your freedom!";
  private final String EXIT_GAME = "Goodbye!";
  private final DungeonGameEngine gameEngine;

  public DungeonGameUI(String path) {
    gameEngine = new DungeonGameEngine(path);
  }

  /** starts and runs the game engine to allow user input */
  public void runGame() {

    Scanner input = new Scanner(System.in);

    String commandOutput = "";
    while (!commandOutput.equals(EXIT_GAME) && !commandOutput.equals(ESCAPED_DUNGEON)) {

      System.out.print("> ");
      String commandLine = input.nextLine();

      Command cleanedCommand = cleanCommand(commandLine);
      commandOutput = gameEngine.inputCommand(cleanedCommand);

      System.out.println("\n" + commandOutput + "\n");
    }
  }

  /**
   * Removes all of the whitespace and casing from a string and turn it into a command object
   *
   * @param input the desired action inputted by a user
   * @return a command associated with the action taken by the user
   */
  public Command cleanCommand(String input) {

    input = input.trim().toLowerCase();

    String[] inputArgs = input.split("\\s+");

    StringBuilder arguments = new StringBuilder();
    for (int i = 1; i < inputArgs.length; i++) {

      arguments.append(inputArgs[i]);
      arguments.append(" ");
    }

    return new Command(inputArgs[0].trim(), arguments.toString().trim());
  }
}
