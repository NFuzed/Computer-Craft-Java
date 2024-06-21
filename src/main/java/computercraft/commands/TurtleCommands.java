package computercraft.commands;


import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import computercraft.turtle.Turtle;
import graphical.geometry.Direction;
import org.java_websocket.WebSocket;
import util.GenericJsonConverter;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class TurtleCommands extends Commands {
    private final CommandQueue commandQueue;
    private final Turtle turtle;

    public TurtleCommands(CommandQueue commandQueue, AtomicInteger commandId, WebSocket webSocket, TurtleWebSocketServer server, Turtle turtle) {
        super(commandId, webSocket, server);
        this.commandQueue = commandQueue;
        this.turtle = turtle;
    }

    public Future<Boolean> moveForward() {
        return commandQueue.enqueueCommand(() -> {
            JsonNode response = sendCommand("turtle.forward()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                turtle.moveForward();
            }
            return success;
        });
    }

    public Future<Boolean> moveUp() {
        return commandQueue.enqueueCommand(() -> {
            JsonNode response = sendCommand("turtle.up()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                turtle.moveUp();
            }
            return success;
        });
    }

    public Future<Boolean> moveDown() {
        return commandQueue.enqueueCommand(() -> {
            JsonNode response = sendCommand("turtle.down()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                turtle.moveDown();
            }
            return success;
        });
    }

    public Future<Boolean> turnLeft() {
        return commandQueue.enqueueCommand(() -> {
            JsonNode response = sendCommand("turtle.turnLeft()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                switch (turtle.getDirection()) {
                    case NORTH -> turtle.setDirection(Direction.WEST);
                    case EAST -> turtle.setDirection(Direction.NORTH);
                    case SOUTH -> turtle.setDirection(Direction.EAST);
                    case WEST -> turtle.setDirection(Direction.SOUTH);
                }
            }

            return success;
        });
    }

    public Future<Boolean> turnRight() {
        return commandQueue.enqueueCommand(() -> {
            JsonNode response = sendCommand("turtle.turnRight()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                switch (turtle.getDirection()) {
                    case NORTH -> turtle.setDirection(Direction.EAST);
                    case EAST -> turtle.setDirection(Direction.SOUTH);
                    case SOUTH -> turtle.setDirection(Direction.WEST);
                    case WEST -> turtle.setDirection(Direction.NORTH);
                }
            }

            return success;
        });
    }

    public Map<String, Object> inspect() {
        JsonNode response = sendCommand("turtle.inspect()");
        return GenericJsonConverter.convertToMap(response);
    }
}


