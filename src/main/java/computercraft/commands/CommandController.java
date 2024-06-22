package computercraft.commands;

import computercraft.commands.peripherals.unlimitedperipherals.ScannerCommands;
import computercraft.turtle.Turtle;
import computercraft.turtle.WebsocketInformation;

import java.util.concurrent.atomic.AtomicInteger;

public class CommandController {
    private final TurtleCommands turtleCommands;
    private final ScannerCommands scannerCommands;

    public CommandController(WebsocketInformation websocketInformation, Turtle turtle) {
        AtomicInteger commandId = new AtomicInteger(0);
        turtleCommands = new TurtleCommands(commandId, websocketInformation, turtle);
        scannerCommands = new ScannerCommands(commandId, websocketInformation, turtle);
    }

    public TurtleCommands getTurtleCommands() {
        return turtleCommands;
    }

    public ScannerCommands getScannerCommands() {
        return scannerCommands;
    }
}
