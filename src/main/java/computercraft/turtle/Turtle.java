package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.block.Block;
import computercraft.commands.CommandController;
import graphical.geometry.Direction;

import java.util.List;
import java.util.Queue;

public class Turtle {
    private final CommandController commandController;
    private final Queue<Turtle> dirtyQueue;
    private final TurtleAttributes turtleAttributes;
    private final Queue<Block> blocksToAdd;

    public Turtle(TurtleAttributes turtleAttributes, WebsocketInformation websocketInformation, Queue<Turtle> dirtyQueue, Queue<Block> blocksToAdd) {
        this.turtleAttributes = turtleAttributes;
        this.commandController = new CommandController(websocketInformation, this);
        this.dirtyQueue = dirtyQueue;
        this.blocksToAdd = blocksToAdd;

        commandController.getScannerCommands().scanBlocks(8);

    }

    public void addBlocksToGraphics(List<Block> unconvertedBlocksMap) {
        blocksToAdd.addAll(unconvertedBlocksMap);
    }

    public void moveForward() {
        turtleAttributes.setPosition(turtleAttributes.getPosition().add(Direction.getDirectionAsVector(turtleAttributes.getDirection())));
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
        turtleAttributes.setDirection(direction);
    }

    public Vector3 getPosition() {
        return turtleAttributes.getPosition();
    }

    public Direction getDirection() {
        return turtleAttributes.getDirection();
    }

    public CommandController getCommandController() {
        return commandController;
    }
}

