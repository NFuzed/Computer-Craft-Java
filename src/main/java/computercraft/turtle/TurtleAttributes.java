package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import graphical.geometry.Direction;

public class TurtleAttributes {
    private final int id;
    private Vector3 position;
    private Direction direction;

    public TurtleAttributes(int id, Vector3 position, Direction direction) {
        this.id = id;
        this.position = position;
        this.direction = direction;
    }

    public Vector3 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }
}
