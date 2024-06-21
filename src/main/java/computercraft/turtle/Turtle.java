package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.commands.CommandController;
import computercraft.commands.TurtleCommands;
import computercraft.server.TurtleWebSocketServer;
import graphical.geometry.Direction;
import graphical.geometry.TurtleModel;
import org.java_websocket.WebSocket;

import java.util.Queue;

public class Turtle {
    private final int id;
    private final TurtleModel turtleModel;
    private final CommandController commandController;
    private final Queue<TurtleUpdateInformation> updateQueue;
    private final Vector3 position;
    private Direction direction;

    public Turtle(int id, Vector3 position, Direction direction, WebSocket webSocket, TurtleWebSocketServer server, Queue<TurtleUpdateInformation> updateQueue) {
        this.id = id;
        this.position = position;
        this.direction = direction;

        this.commandController = new CommandController(webSocket, server, this);
        this.turtleModel = new TurtleModel(position, direction);
        this.updateQueue = updateQueue;

        TurtleCommands turtleCommands = commandController.getTurtleCommands();
        turtleCommands.moveForward();
        turtleCommands.moveForward();
        turtleCommands.moveForward();
        turtleCommands.turnLeft();
        turtleCommands.moveForward();
        turtleCommands.moveForward();
    }

    public void moveForward() {
        Vector3 oldPosition = position.cpy();
        Vector3 newPosition = position.add(Direction.getDirectionAsVector(direction)).cpy();
        TurtleUpdateInformation updateInformation = new TurtleUpdateInformation(oldPosition, newPosition);
        updateQueue.add(updateInformation);
    }

    public void moveUp() {
        position.add(new Vector3(0, 1, 0));
    }

    public void moveDown() {
        position.add(new Vector3(0, -1, 0));
    }

    private void updateModel() {
        turtleModel.getModelInstance().transform.setTranslation(position);
    }

    public Integer getId() {
        return id;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public CommandController getCommandController() {
        return commandController;
    }
}

