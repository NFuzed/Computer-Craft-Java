package computercraft.commands;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.turtle.WebsocketInformation;
import util.GenericJsonConverter;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ScannerCommands extends Commands {

    private final CommandQueue commandQueue;

    public ScannerCommands(CommandQueue commandQueue, AtomicInteger commandId, WebsocketInformation websocketInformation) {
        super(commandId, websocketInformation);
        this.commandQueue = commandQueue;
    }

    public Future<List<Object>> scanBlocks(int range) {


        return commandQueue.enqueueCommand(() -> {
//            if (!peripherals.contains(Peripherals.UNIVERSAL_SCANNER)){
//                System.out.println("Cannot execute command - Universal Scanner not detected");
//                return Collections.emptyMap();
//            }

            String command = String.format("peripheral.find(\"%s\").scan(\"%s\", %s)", Peripherals.UNIVERSAL_SCANNER.getId(), "block", range);
            JsonNode response = sendCommand(command);
            return GenericJsonConverter.convertToArrayList(response);
        });
    }
}
