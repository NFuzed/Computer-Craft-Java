package computercraft.commands;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import org.java_websocket.WebSocket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Commands {
    private final WebSocket webSocket;
    private AtomicInteger commandId;
    private final TurtleWebSocketServer server;

    protected Commands(AtomicInteger commandId, WebSocket webSocket, TurtleWebSocketServer server) {
        this.commandId = commandId;
        this.webSocket = webSocket;
        this.server = server;
    }

    JsonNode sendCommand(String command) {
        CompletableFuture<JsonNode> completableFuture = server.sendCommandWithResponse(command, commandId.getAndAdd(1), webSocket);
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }

    }
}
