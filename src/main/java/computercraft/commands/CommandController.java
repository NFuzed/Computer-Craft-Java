package computercraft.commands;

import computercraft.server.TurtleWebSocketServer;
import computercraft.turtle.Turtle;
import org.java_websocket.WebSocket;

import java.util.concurrent.atomic.AtomicInteger;

public class CommandController {

    private final TurtleCommands turtleCommands;
    private final ScannerCommands scannerCommands;

    public CommandController(WebSocket webSocket, TurtleWebSocketServer server, Turtle turtle) {
        AtomicInteger commandId = new AtomicInteger(0);
        turtleCommands = new TurtleCommands(commandId, webSocket, server, turtle);
        scannerCommands = new ScannerCommands(commandId, webSocket, server);
    }

    public TurtleCommands getTurtleCommands() {
        return turtleCommands;
    }

    public ScannerCommands getScannerCommands() {
        return scannerCommands;
    }
}
