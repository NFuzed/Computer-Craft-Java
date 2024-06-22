package computercraft.server;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.turtle.TurtleManager;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class TurtleWebSocketServer extends WebSocketServer {

    private static final String HOST = "localhost";
    private static final int PORT = 8887;
    private final TurtleManager turtleManager;
    private final Map<Integer, CompletableFuture<JsonNode>> commandResponseFutures = new ConcurrentHashMap<>();


    public TurtleWebSocketServer(TurtleManager turtleManager) {
        super(new InetSocketAddress(HOST, PORT));
        this.turtleManager = turtleManager;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String value) {
        Optional<Message> optionalMessage = MessageWrapper.unwrapMessage(value);
        if (optionalMessage.isEmpty()) {
            return;
        }

        Message message = optionalMessage.get();

        switch (message.getAction()) {
            case ("Connect"):
                turtleManager.addTurtle(message, conn, this);
                break;
            case ("Command Response"):
                handleCommandResponse(message);
                break;
            default:
                System.out.println("Invalid Action: " + message.getAction());
        }
    }

    private void handleCommandResponse(Message message) {
        CompletableFuture<JsonNode> future = commandResponseFutures.remove(message.getCommandId());
        if (future != null) {
            // Assuming the message has a field "success" that indicates the command result
            future.complete(message.getContent());
        }
    }

    public CompletableFuture<JsonNode> sendCommandWithResponse(String command, int commandId, WebSocket webSocket) {
        CompletableFuture<JsonNode> future = new CompletableFuture<>();
        commandResponseFutures.put(commandId, future);

        sendCommand(command, commandId, webSocket);

        return future;
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("An error occurred on connection " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

    public void runOnNewThread() {
        new Thread(this).start();
    }

    public void sendCommand(String command, int commandId, WebSocket webSocket) {
        Map<String, Object> mappedMessage = new HashMap<>();
        mappedMessage.put("command", command);
        mappedMessage.put("commandId", commandId);

        Optional<String> optionalMessage = MessageWrapper.wrapMessage(mappedMessage);
        optionalMessage.ifPresent(webSocket::send);
    }
}