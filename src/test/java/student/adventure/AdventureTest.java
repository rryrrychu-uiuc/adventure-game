package student.adventure;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

/**
 * Your tests should ensure that the application state is correctly updated.
 * For example, if a function mutates an object, you can’t just check that the return value is correct.
 * You must also ensure that the object reflects those changes.
 */

/**
 * Your code should be able to handle the exceptions thrown by Gson in the event that it's passed in a file that doesn't exist or one that is malformed.
 *
 * This isn't testing Gson's functionality, this is testing your code's ability to handle errors gracefully.
 */

public class AdventureTest {
    @Before
    public void setUp() {
        // This is run before every test.
    }

    //Test proper GSON error handling

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidJSONPath() {
        DungeonGameEngine testGame = new DungeonGameEngine("waterfile");

    }

    //TODO: FIX THIS SOMEHOW AHHHHHHHHHHHHHHHHHH
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyJSONFile() {
        DungeonGameEngine testGame = new DungeonGameEngine("src/test/resources/emptyfile.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidJSONSchema() {
        DungeonGameEngine testGame = new DungeonGameEngine("src/main/resources/siebel.json");
    }

}