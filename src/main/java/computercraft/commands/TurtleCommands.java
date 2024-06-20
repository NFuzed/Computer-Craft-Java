package computercraft.commands;


import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import org.java_websocket.WebSocket;
import util.GenericJsonConverter;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TurtleCommands {
    private final WebSocket webSocket;
    private int commandId;
    private final TurtleWebSocketServer server;

    public TurtleCommands(WebSocket webSocket, TurtleWebSocketServer server) {
        this.webSocket = webSocket;
        this.server = server;
        commandId = 0;
    }

    public boolean moveForward() {
        JsonNode response = sendCommand("turtle.forward()");
        return GenericJsonConverter.convertToBoolean(response);
    }

    public boolean turnLeft() {
        JsonNode response = sendCommand("turtle.turnLeft()");
        return GenericJsonConverter.convertToBoolean(response);
    }

    public boolean turnRight() {
        JsonNode response = sendCommand("turtle.turnRight()");
        return GenericJsonConverter.convertToBoolean(response);
    }

    public Map<String, Object> inspect() {
        JsonNode response = sendCommand("turtle.inspect()");
        return GenericJsonConverter.convertToMap(response);
    }


    private JsonNode sendCommand(String command) {
        CompletableFuture<JsonNode> completableFuture = server.sendCommandWithResponse(command, commandId++, webSocket);
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }

    }
}


