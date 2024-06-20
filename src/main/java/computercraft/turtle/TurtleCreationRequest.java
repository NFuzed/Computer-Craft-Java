package computercraft.turtle;

import computercraft.server.Message;
import computercraft.server.TurtleWebSocketServer;
import org.java_websocket.WebSocket;

public class TurtleCreationRequest {
    private final TurtleWebSocketServer turtleWebSocketServer;
    private Message msg;
    private WebSocket conn;

    public TurtleCreationRequest(Message msg, WebSocket conn, TurtleWebSocketServer turtleWebSocketServer) {
        this.msg = msg;
        this.conn = conn;
        this.turtleWebSocketServer = turtleWebSocketServer;
    }

    public TurtleWebSocketServer getTurtleWebSocketServer() {
        return turtleWebSocketServer;
    }

    public Message getMsg() {
        return msg;
    }

    public WebSocket getConn() {
        return conn;
    }
}
