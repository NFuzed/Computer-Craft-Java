package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.commands.CommandController;
import computercraft.commands.TurtleCommands;
import graphical.geometry.Direction;

import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Turtle {
    private final CommandController commandController;
    private final Queue<Turtle> dirtyQueue;
    private final TurtleAttributes turtleAttributes;
    private Direction direction;

    public Turtle(TurtleAttributes turtleAttributes, WebsocketInformation websocketInformation, Queue<Turtle> dirtyQueue) {
        this.turtleAttributes = turtleAttributes;
        this.commandController = new CommandController(websocketInformation, this);
        this.dirtyQueue = dirtyQueue;

        TurtleCommands turtleCommands = commandController.getTurtleCommands();
        turtleCommands.moveForward();
        turtleCommands.moveForward();
        turtleCommands.moveForward();
        turtleCommands.turnLeft();
        turtleCommands.moveForward();
        Future<Boolean> booleanFuture = turtleCommands.moveForward();

        try {
            System.out.println(booleanFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public void moveForward() {
        turtleAttributes.setPosition(turtleAttributes.getPosition().add(Direction.getDirectionAsVector(direction)));
        dirtyQueue.add(this);
    }

    public void moveUp() {
        turtleAttributes.setPosition(turtleAttributes.getPosition().add(new Vector3(0, 1, 0)));
        dirtyQueue.add(this);
    }

    public void moveDown() {
        turtleAttributes.setPosition(turtleAttributes.getPosition().add(new Vector3(0, -1, 0)));
        dirtyQueue.add(this);
    }

    public Integer getId() {
        return turtleAttributes.getId();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Vector3 getPosition() {
        return turtleAttributes.getPosition();
    }

    public Direction getDirection() {
        return direction;
    }

    public CommandController getCommandController() {
        return commandController;
    }
}

