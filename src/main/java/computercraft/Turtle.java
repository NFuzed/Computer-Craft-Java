package computercraft;

import com.badlogic.gdx.math.Vector3;
import graphical.geometry.Direction;
import graphical.geometry.TurtleModel;
import org.java_websocket.WebSocket;


public class Turtle {

    private final int id;
    private final Vector3 position;
    private final Direction direction;
    private final WebSocket webSocket;
    private final TurtleModel turtleModel;

    public Turtle(int id, Vector3 position, Direction direction, WebSocket webSocket) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.webSocket = webSocket;

        turtleModel = new TurtleModel(position, direction);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }


    public Integer getId() {
        return id;
    }
}
