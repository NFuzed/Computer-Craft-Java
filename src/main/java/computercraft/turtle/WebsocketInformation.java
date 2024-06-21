package computercraft.turtle;

import computercraft.server.TurtleWebSocketServer;
import org.java_websocket.WebSocket;

public class WebsocketInformation {
    private WebSocket webSocket;
    private TurtleWebSocketServer server;

    public WebsocketInformation(WebSocket webSocket, TurtleWebSocketServer server) {
        this.webSocket = webSocket;
        this.server = server;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public TurtleWebSocketServer getServer() {
        return server;
    }
}
