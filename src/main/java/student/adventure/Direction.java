package student.adventure;

/**
 * Direction is a reference object that connects directions to rooms thorough roomName
 *
 * @author Harry Chu
 */
public class Direction {

    private String directionName;
    private String roomName;

    public String getDirectionName() {

        return directionName;
    }

    public String getRoomName() {

        return roomName;
    }

    @Override
    public boolean equals(Object o) {

        if(o instanceof Direction targetDirection) {
            return directionName.equalsIgnoreCase(targetDirection.directionName) && roomName.equalsIgnoreCase(targetDirection.roomName);
        }

        return false;
    }
}
