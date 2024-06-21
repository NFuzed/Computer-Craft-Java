package computercraft.commands;

import computercraft.turtle.Turtle;
import computercraft.turtle.WebsocketInformation;

import java.util.concurrent.atomic.AtomicInteger;

public class CommandController {
    private final TurtleCommands turtleCommands;
    private final ScannerCommands scannerCommands;

    public CommandController(WebsocketInformation websocketInformation, Turtle turtle) {
        CommandQueue commandQueue = new CommandQueue();
        AtomicInteger commandId = new AtomicInteger(0);
        turtleCommands = new TurtleCommands(commandQueue, commandId, websocketInformation, turtle);
        scannerCommands = new ScannerCommands(commandQueue, commandId, websocketInformation);
    }

    public TurtleCommands getTurtleCommands() {
        return turtleCommands;
    }

    public ScannerCommands getScannerCommands() {
        return scannerCommands;
    }
}
