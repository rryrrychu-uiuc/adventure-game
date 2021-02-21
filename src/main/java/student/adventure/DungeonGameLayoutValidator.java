package student.adventure;

import java.util.ArrayList;
import java.util.Collections;

public class DungeonGameLayoutValidator {

    public static void validateDeserialization(DungeonGameLayout targetLayout) {

        checkValidGameType(targetLayout);

        checkRoomsExist(targetLayout);

        checkValidStartingRoomName(targetLayout);

        checkValidEndingRoomName(targetLayout);

        checkValidRoomStats(targetLayout);

        checkValidAchievements(targetLayout);
    }

    //Checks to make sure the game is of the right schema
    private static void checkValidGameType(DungeonGameLayout targetLayout) {

        String mapType = targetLayout.getGameType();
        if (mapType == null || !mapType.equals("dungeonGame")) {
            throw new IllegalArgumentException("JSON file does not fit the schema of this game");
        }
    }

    //Checks to make sure there are rooms in the game
    private static void checkRoomsExist(DungeonGameLayout targetLayout) {

        if(targetLayout.getRooms() == null || targetLayout.getRooms().size() == 0) {
            throw new IllegalArgumentException("Invalid JSON. No rooms in the game");
        }
    }

    //Checks to make sure a starting room exists
    private static void checkValidStartingRoomName(DungeonGameLayout targetLayout) {

        String startingRoomName = targetLayout.getStartingRoomName();
        if(startingRoomName == null || startingRoomName.length() == 0) {
            throw new IllegalArgumentException("Invalid JSON. No defined starting room.");
        } else if(targetLayout.getStartingRoom() == null) {
            throw new IllegalArgumentException("Invalid JSON. Starting room does not exist in the list of rooms.");
        }
    }

    //Checks to make sure a ending room exists
    private static void checkValidEndingRoomName(DungeonGameLayout targetLayout) {

        String endingRoomName = targetLayout.getStartingRoomName();
        if(endingRoomName == null || endingRoomName.length() == 0) {
            throw new IllegalArgumentException("Invalid JSON. No defined ending room.");
        } else if(targetLayout.getEndingRoom() == null) {
            throw new IllegalArgumentException("Invalid JSON. Ending room does not exist in the list of rooms.");
        }
    }

    //Checks to make sure all of the stats for a room are correct
    private static void checkValidRoomStats(DungeonGameLayout targetLayout) {

        for(Room targetRoom: targetLayout.getRooms()) {

            String targetRoomName = targetRoom.getRoomName();
            if (targetRoomName == null || targetRoomName.length() == 0) {
                throw new IllegalArgumentException("A room does not have a name.");
            }

            String targetRoomDescription = targetRoom.getRoomDescription();
            if(targetRoomDescription == null || targetRoomDescription.length() == 0) {
                throw new IllegalArgumentException("A room does not have a description.");
            }

            checkValidDirection(targetLayout, targetRoom);

            checkValidItems(targetRoom.getItems());

            checkValidItems(targetRoom.getRequiredItems());

            checkRequiredItemsExist(targetLayout, targetRoom);
        }
    }

    //Checks to make sure a room has valid directions
    private static void checkValidDirection(DungeonGameLayout targetLayout, Room targetRoom) {

        if(targetRoom.getDirections() == null) {
            return;
        }

        for(Direction targetDirection: targetRoom.getDirections()) {

            String targetDirectionName = targetDirection.getDirectionName();
            if(targetDirectionName == null || targetDirectionName.length() == 0) {
                throw new IllegalArgumentException("A direction does not have a name.");
            }

            String targetDirectionRoom = targetDirection.getRoomName();
            if(targetDirectionRoom == null || targetDirectionRoom.length() == 0) {
                throw new IllegalArgumentException("A direction does not have an associated room.");
            } else if(!targetLayout.getRooms().contains(targetRoom)) {
                throw new IllegalArgumentException("A direction's room does not exist.");
            }
        }
    }

    //Checks to make sure a room has valid items
    private static void checkValidItems(ArrayList<Item> itemLocation) {

        if(itemLocation == null) {
            return;
        }

        for(Item targetItem: itemLocation) {

            String targetItemName = targetItem.getItemName();
            if(targetItemName == null || targetItemName.length() == 0) {
                throw new IllegalArgumentException("An item does not have a name.");
            }

            String targetItemDescription = targetItem.getItemDescription();
            if(targetItemDescription == null || targetItemDescription.length() == 0) {
                throw new IllegalArgumentException("An item does not have a description.");
            }

            String targetItemType = targetItem.getItemType();
            if(targetItemType == null || targetItemType.length() == 0) {
                throw new IllegalArgumentException("An item does not have a type.");
            }
        }
    }

    //Checks to make sure that all the required items exist in the game
    private static void checkRequiredItemsExist(DungeonGameLayout targetLayout, Room targetRoom) {

        if(!Item.containsAllItems(getAllItems(targetLayout), targetRoom.getRequiredItems())) {
            throw new IllegalArgumentException("Game does not contain the required items");
        }
    }

    //Checks to make sure that all of the achievements are valid
    private static void checkValidAchievements(DungeonGameLayout targetLayout) {

        if(targetLayout.getAchievements() == null) {
            return;
        }

        for(Achievement targetAchievement: targetLayout.getAchievements()) {

            if(targetAchievement.getMinRequiredItems() < 0) {
                throw new IllegalArgumentException("Achievement can never be achieved.");
            }

            String targetAchievementMessage = targetAchievement.getAchievementMessage();
            if(targetAchievementMessage == null || targetAchievementMessage.length() == 0) {
                throw new IllegalArgumentException("Invalid achievement message");
            }

            if(!getAllItemTypes(getAllItems(targetLayout)).contains(targetAchievement.getItemType())) {
                throw new IllegalArgumentException("Achievement can never be achieved.");
            }
        }
    }

    //returns a list containing all of the items in the game
    private static ArrayList<Item> getAllItems(DungeonGameLayout targetLayout) {

        ArrayList<Item> itemsToReturn = new ArrayList<>();
        for(Room targetRoom: targetLayout.getRooms()) {

            if(targetRoom.getItems() == null) {
                continue;
            }

            itemsToReturn.addAll(targetRoom.getItems());
        }

        return itemsToReturn;
    }

    //returns a list containing all types of items in the game
    private static ArrayList<String> getAllItemTypes(ArrayList<Item> allItems) {

        ArrayList<String> itemTypesToReturn = new ArrayList<>();

        for(Item targetItem: allItems) {
            String targetItemType = targetItem.getItemType();
            if(!itemTypesToReturn.contains(targetItemType)) {
                itemTypesToReturn.add(targetItemType);
            }
        }

        return itemTypesToReturn;
    }
}
