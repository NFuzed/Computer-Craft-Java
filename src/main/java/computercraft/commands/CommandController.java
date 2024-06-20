package computercraft.commands;

import computercraft.server.TurtleWebSocketServer;
import computercraft.turtle.Turtle;
import org.java_websocket.WebSocket;

public class CommandController {

    private final TurtleCommands turtleCommands;
    private final ScannerCommands scannerCommands;

    public CommandController(WebSocket webSocket, TurtleWebSocketServer server, Turtle turtle) {
        turtleCommands = new TurtleCommands(webSocket, server, turtle);
        scannerCommands = new ScannerCommands(webSocket, server);
    }

    public TurtleCommands getTurtleCommands() {
        return turtleCommands;
    }

    public ScannerCommands getScannerCommands() {
        return scannerCommands;
    }
}
