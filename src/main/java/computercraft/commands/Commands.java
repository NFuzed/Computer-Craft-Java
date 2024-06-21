package computercraft.commands;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import computercraft.turtle.WebsocketInformation;
import org.java_websocket.WebSocket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Commands {
    private final WebSocket webSocket;
    private final AtomicInteger commandId;
    private final TurtleWebSocketServer server;

    protected Commands(AtomicInteger commandId, WebsocketInformation websocketInformation) {
        this.commandId = commandId;
        this.webSocket = websocketInformation.getWebSocket();
        this.server = websocketInformation.getServer();
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
