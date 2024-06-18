package computercraft;

import org.java_websocket.WebSocket;

public class TurtleCreationRequest {
    private ConnectionInformation msg;
    private WebSocket conn;

    public TurtleCreationRequest(ConnectionInformation msg, WebSocket conn) {
        this.msg = msg;
        this.conn = conn;
    }

    public ConnectionInformation getMsg() {
        return msg;
    }

    public WebSocket getConn() {
        return conn;
    }
}
