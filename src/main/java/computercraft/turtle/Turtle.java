package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.block.Block;
import computercraft.commands.CommandController;
import graphical.geometry.Direction;
import util.observer.Observable;

import java.util.Queue;

public class Turtle extends Observable<Vector3> {
    private final CommandController commandController;
    private final Queue<Turtle> dirtyQueue;
    private final TurtleAttributes turtleAttributes;
    private final Queue<Block> blocksToAdd;
    private final WebsocketInformation websocketInformation;

    public Turtle(TurtleAttributes turtleAttributes, WebsocketInformation websocketInformation, Queue<Turtle> dirtyQueue, Queue<Block> blocksToAdd) {
        this.turtleAttributes = turtleAttributes;
        this.websocketInformation = websocketInformation;
        this.commandController = new CommandController(websocketInformation, this);
        this.dirtyQueue = dirtyQueue;
        this.blocksToAdd = blocksToAdd;


    }

    public Integer getId() {
        return turtleAttributes.getId();
    }

    public void setDirection(Direction direction) {
        turtleAttributes.setDirection(direction);
    }

    public Vector3 getPosition() {
        return turtleAttributes.getPosition();
    }

    public void movePosition(Vector3 translation) {
        turtleAttributes.setPosition(turtleAttributes.getPosition().add(translation));
        notifyObservers(translation);
    }

    public Direction getDirection() {
        return turtleAttributes.getDirection();
    }

    public Queue<Turtle> getDirtyQueue() {
        return dirtyQueue;
    }

    public Queue<Block> getBlocksToAdd() {
        return blocksToAdd;
    }

    public CommandController getCommandController() {
        return commandController;
    }

    public void setWebsocketInformation(WebsocketInformation websocketInformation) {
        this.websocketInformation.setWebSocket(websocketInformation.getWebSocket());
        this.websocketInformation.setServer(websocketInformation.getServer());
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}

