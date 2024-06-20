package computercraft.commands;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import org.java_websocket.WebSocket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class Commands {
    private final WebSocket webSocket;
    private int commandId;
    private final TurtleWebSocketServer server;

    protected Commands(WebSocket webSocket, TurtleWebSocketServer server) {
        this.webSocket = webSocket;
        this.server = server;
        commandId = 0;
    }

    JsonNode sendCommand(String command) {
        CompletableFuture<JsonNode> completableFuture = server.sendCommandWithResponse(command, commandId++, webSocket);
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }

    }
}
