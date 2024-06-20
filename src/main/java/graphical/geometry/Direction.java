package graphical.geometry;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

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

    public static Vector3 getDirectionAsVector(Direction direction) {
        return switch (direction) {
            case NORTH -> new Vector3(0, 0, -1);
            case EAST -> new Vector3(1, 0, 0);
            case SOUTH -> new Vector3(0, 0, 1);
            case WEST -> new Vector3(-1, 0, 0);
            default -> throw new AssertionError("Unknown direction: " + direction);
        };
    }

    public static Direction toValue(String direction) {
        return Enum.valueOf(Direction.class, direction);
    }
}
