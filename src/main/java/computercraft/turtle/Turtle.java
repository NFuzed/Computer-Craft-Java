package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.commands.TurtleCommands;
import computercraft.server.TurtleWebSocketServer;
import graphical.geometry.Direction;
import graphical.geometry.TurtleModel;
import org.java_websocket.WebSocket;

public class Turtle extends TurtleCommands {
    private final int id;
    private final Vector3 position;
    private final Direction direction;
    private final TurtleModel turtleModel;

    public Turtle(int id, Vector3 position, Direction direction, WebSocket webSocket, TurtleWebSocketServer server) {
        super(webSocket, server);
        this.id = id;
        this.position = position;
        this.direction = direction;

        turtleModel = new TurtleModel(position, direction);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public TurtleModel getTurtleModel() {
        return turtleModel;
    }

    public Integer getId() {
        return id;
    }
}

