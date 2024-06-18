package graphical.geometry;

import com.badlogic.gdx.math.Quaternion;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Quaternion toQuaternion(Direction direction) {
        return switch (direction) {
            case NORTH -> new Quaternion(0, 0, 0, 1);
            case EAST -> new Quaternion(0, 0, 1, 0);
            case SOUTH -> new Quaternion(0, 0, 0, -1);
            case WEST -> new Quaternion(0, 0, -1, 0);
            default -> throw new AssertionError("Unknown direction: " + direction);
        };
    }

    public static Direction toValue(String direction) {
        return Enum.valueOf(Direction.class, direction);
    }
}
