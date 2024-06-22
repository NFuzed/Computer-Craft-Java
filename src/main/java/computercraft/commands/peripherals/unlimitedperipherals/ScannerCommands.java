package computercraft.commands.peripherals.unlimitedperipherals;

import com.fasterxml.jackson.databind.JsonNode;
import computercraft.block.Block;
import computercraft.commands.Commands;
import computercraft.commands.peripherals.PeripheralType;
import computercraft.turtle.Turtle;
import computercraft.turtle.WebsocketInformation;
import util.GenericJsonConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ScannerCommands extends Commands {

    private final Turtle turtle;

    public ScannerCommands(AtomicInteger commandId, WebsocketInformation websocketInformation, Turtle turtle) {
        super(commandId, websocketInformation);
        this.turtle = turtle;
    }

    public List<Block> scanBlocks(int range) {

//            if (!peripherals.contains(Peripherals.UNIVERSAL_SCANNER)){
//                System.out.println("Cannot execute command - Universal Scanner not detected");
//                return Collections.emptyMap();
//            }

        String command = String.format("peripheral.find(\"%s\").scan(\"%s\", %s)", PeripheralType.UNIVERSAL_SCANNER.getId(), "block", range);
        JsonNode response = sendCommand(command);
        List<Object> unconvertedMaps = GenericJsonConverter.convertToArrayList(response);

        List<Block> blocks = unconvertedMaps.stream()
                .map(obj -> (List<Map<String, Object>>) obj)
                .flatMap(Collection::stream)
                .map(map -> new Block(map, turtle.getPosition()))
                .toList();

        turtle.addBlocksToGraphics(blocks);

        return blocks;
    }
}
