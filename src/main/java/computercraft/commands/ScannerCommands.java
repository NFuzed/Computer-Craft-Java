package computercraft.commands;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.server.TurtleWebSocketServer;
import org.java_websocket.WebSocket;
import util.GenericJsonConverter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ScannerCommands extends Commands {

    public ScannerCommands(AtomicInteger commandId, WebSocket webSocket, TurtleWebSocketServer server) {
        super(commandId, webSocket, server);
    }

    public List<Object> scanBlocks(int range) {
//        if (!peripherals.contains(Peripherals.UNIVERSAL_SCANNER)){
//            System.out.println("Cannot execute command - Universal Scanner not detected");
//            return Collections.emptyMap();
//        }

        String command = String.format("peripheral.find(\"%s\").scan(\"%s\", %s)", Peripherals.UNIVERSAL_SCANNER.getId(), "block", range);
        JsonNode response = sendCommand(command);
        return GenericJsonConverter.convertToArrayList(response);
    }
}
