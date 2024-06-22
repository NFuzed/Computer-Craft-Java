package computercraft.commands;


import com.fasterxml.jackson.databind.JsonNode;
import computercraft.turtle.Turtle;
import computercraft.turtle.WebsocketInformation;
import graphical.geometry.Direction;
import util.GenericJsonConverter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TurtleCommands extends Commands {
    private final Turtle turtle;

    public TurtleCommands(AtomicInteger commandId, WebsocketInformation websocketInformation, Turtle turtle) {
        super(commandId, websocketInformation);
        this.turtle = turtle;
    }

    public boolean moveForward() {
            JsonNode response = sendCommand("turtle.forward()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                turtle.moveForward();
            }
            return success;
    }

    public boolean moveUp() {
            JsonNode response = sendCommand("turtle.up()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                turtle.moveUp();
            }
            return success;
    }

    public boolean moveDown() {
            JsonNode response = sendCommand("turtle.down()");
            boolean success = GenericJsonConverter.convertToBoolean(response);

            if (success) {
                turtle.moveDown();
            }
            return success;
    }

    public boolean turnLeft() {
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
    }

    public boolean turnRight() {

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
    }

    public Map<String, Object> inspect() {
        JsonNode response = sendCommand("turtle.inspect()");
        return GenericJsonConverter.convertToMap(response);
    }
}


