package computercraft.commands.peripherals.unlimitedperipherals;

import com.badlogic.gdx.math.Vector3;
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
        String command =
                String.format("local scanner = peripheral.find('%s')%n", PeripheralType.UNIVERSAL_SCANNER.getId()) +
                        String.format("local result = scanner.scan('block', %s)%n", range) +
                        "local filtered_result = {}\n" +
                        "for _, v in pairs(result) do\n" +
                        "    table.insert(filtered_result, {x = v.x, y = v.y, z = v.z, displayName = v.displayName})\n" +
                        "end\n" +
                        "return filtered_result";

        JsonNode response = sendCommand(command);
        List<Object> unconvertedMaps = GenericJsonConverter.convertToArrayList(response);

        List<Block> blocks = unconvertedMaps.stream()
                .map(obj -> (List<Map<String, Object>>) obj)
                .flatMap(Collection::stream)
                .map(map -> new Block(map, turtle.getPosition(), turtle.getDirection()))
                .filter(block -> !new Vector3(block.getX(), block.getY(), block.getZ()).equals(turtle.getPosition()))
                .toList();

        turtle.getBlocksToAdd().addAll(blocks);
        return blocks;
    }
}
