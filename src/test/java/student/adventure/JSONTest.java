package student.adventure;

import org.junit.Before;
import org.junit.Test;

public class JSONTest {

    private DungeonGameEngine testGame;

    @Before
    public void setup() {
        testGame = null;
    }

    //Testing json file path

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidJSONPath() {
        testGame = new DungeonGameEngine("waterfile");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyJSONFile() {
        testGame = new DungeonGameEngine("src/test/resources/emptyJSON.json");
    }

    //Testing Layout gameType deserialization

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGameType() {
        testGame = new DungeonGameEngine("src/test/resources/GameTypeTestJSONs/invalidGameTypeJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullGameType() {
        testGame = new DungeonGameEngine("src/test/resources/GameTypeTestJSONs/nullGameTypeJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyGameType() {
        testGame = new DungeonGameEngine("src/test/resources/GameTypeTestJSONs/emptyGameTypeJSON.json");
    }

    //Testing array of rooms deserialization

    @Test(expected = IllegalArgumentException.class)
    public void testRoomsNull() {
        testGame = new DungeonGameEngine("src/test/resources/RoomTestJSONs/roomsArrayNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoomsEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/RoomTestJSONs/roomsArrayEmptyJSON.json");
    }

    //Testing startingRoomName deserialization

    @Test(expected = IllegalArgumentException.class)
    public void testStartingRoomNameNull() {
        testGame = new DungeonGameEngine("src/test/resources/StartingRoomNameTestJSONs/startingRoomNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartingRoomNameEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/StartingRoomNameTestJSONs/startingRoomEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartingRoomNameDNE() {
        testGame = new DungeonGameEngine("src/test/resources/StartingRoomNameTestJSONs/startingRoomDNEJSON.json");
    }

    //Testing endingRoomName deserialization

    @Test(expected = IllegalArgumentException.class)
    public void testEndingRoomNameNull() {
        testGame = new DungeonGameEngine("src/test/resources/EndingRoomNameTestJSONs/endingRoomNullJSON");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndingRoomNameEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/EndingRoomNameTestJSONs/endingRoomEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndingRoomNameDNE() {
        testGame = new DungeonGameEngine("src/test/resources/EndingRoomNameTestJSONs/endingRoomDNEJSON.json");
    }

    @Test
    public void testEndingRoomIsStartingRoom() {
        testGame = new DungeonGameEngine("src/test/resources/EndingRoomNameTestJSONs/endingIsStartingRoomJSON.json");
    }

    //Testing room deserialization

    @Test(expected = IllegalArgumentException.class)
    public void testRoomNameNull() {
        testGame = new DungeonGameEngine("src/test/resources/RoomTestJSONs/roomNameNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoomNameEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/RoomTestJSONs/roomNameEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoomDescriptionNull() {
        testGame = new DungeonGameEngine("src/test/resources/RoomTestJSONs/roomDescriptionNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRoomDescriptionEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/RoomTestJSONs/roomDescriptionEmptyJSON.json");
    }

    //Testing direction deserialization within room

    @Test
    public void testDirectionsArrayNull() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionsArrayNullJSON.json");
    }

    @Test
    public void testDirectionsArrayEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionsArrayEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectionNameNull() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionNameNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectionNameEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionNameEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectionRoomNull() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionRoomNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectionRoomEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionRoomEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectionRoomDNE() {
        testGame = new DungeonGameEngine("src/test/resources/DirectionTestJSONs/directionRoomDNEJSON.json");
    }

    //Testing item deserialization within room

    @Test
    public void testItemsArrayNull() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemsArrayNullJSON.json");
    }

    @Test
    public void testItemsArrayEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemsArrayEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemNameNull() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemNameNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemNameEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemNameEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemDescriptionNull() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemDescriptionNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemDescriptionEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemDescriptionEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemTypeNull() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemTypeNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemTypeEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/ItemTestJSONs/itemTypeEmptyJSON.json");
    }

    //Test required items deserialization in room
    @Test
    public void testRequiredItemsArrayNull() {
        testGame = new DungeonGameEngine("src/test/resources/RequiredItemTestJSONs/requiredItemsArrayNullJSON.json");
    }

    @Test
    public void testRequiredItemsArrayEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/RequiredItemTestJSONs/requiredItemsArrayEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequiredItemDNE() {
        testGame = new DungeonGameEngine("src/test/resources/RequiredItemTestJSONs/requiredItemsDNEJSON.json");
    }

    //Test achievements deserialization

    @Test
    public void testAchievementArrayNull() {
        testGame = new DungeonGameEngine("src/test/resources/AchievementTestJSONs/achievementArrayNullJSON.json");
    }

    @Test
    public void testAchievementArrayEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/AchievementTestJSONs/achievementArrayEmptyJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAchievementTypeDNE() {
        testGame = new DungeonGameEngine("src/test/resources/AchievementTestJSONs/achievementTypeDNEJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAchievementItemNumNegative() {
        testGame = new DungeonGameEngine("src/test/resources/AchievementTestJSONs/achievementItemNumNegativeJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAchievementMessageNull() {
        testGame = new DungeonGameEngine("src/test/resources/AchievementTestJSONs/achievementMessageNullJSON.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAchievementMessageEmpty() {
        testGame = new DungeonGameEngine("src/test/resources/AchievementTestJSONs/achievementMessageEmptyJSON.json");
    }
}
