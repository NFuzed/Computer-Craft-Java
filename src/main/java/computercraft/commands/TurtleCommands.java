package computercraft.commands;


import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import computercraft.turtle.Turtle;
import graphical.geometry.Direction;
import org.java_websocket.WebSocket;
import util.GenericJsonConverter;

import java.util.ArrayList;
import java.util.Map;

public class TurtleCommands extends Commands {
    private final ArrayList<Peripherals> peripherals;
    private final Turtle turtle;

    public TurtleCommands(WebSocket webSocket, TurtleWebSocketServer server, Turtle turtle) {
        super(webSocket, server);
        this.turtle = turtle;

        peripherals = new ArrayList<>();
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

    public boolean checkForPeripheral(Peripherals peripheral) {
        JsonNode response = sendCommand(String.format("peripheral.find(\"%s\")", peripheral.getId()));
        boolean peripheralExists = GenericJsonConverter.convertToBoolean(response);
        if (peripheralExists) {
            peripherals.add(peripheral);
        }

        return peripheralExists;
    }
}


