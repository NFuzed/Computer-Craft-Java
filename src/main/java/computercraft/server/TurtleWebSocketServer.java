package computercraft.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import computercraft.ConnectionInformation;
import computercraft.TurtleManager;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import util.GenericJsonToMapConverter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class TurtleWebSocketServer extends WebSocketServer {

    private static final String HOST = "localhost";
    private static final int PORT = 8887;
    private final TurtleManager turtleManager;


    public TurtleWebSocketServer(TurtleManager turtleManager) {
        super(new InetSocketAddress(HOST, PORT));
        this.turtleManager = turtleManager;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());

        conn.send("turtle.inspect()");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if ("{}".equals(message)) {
                System.out.println(message);
                return;
            }

            ConnectionInformation msg = mapper.readValue(message, ConnectionInformation.class);
            System.out.println("Action: " + msg.getAction());
            System.out.println("Id: " + msg.getId());

            if ("Connect".equals(msg.getAction())) {
                turtleManager.addTurtle(msg, conn);
            }

        } catch (IOException e) {
            try {
                Map<String, Object> map = GenericJsonToMapConverter.convertToMap(message);
                System.out.println(map);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
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
}
