package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.server.Message;
import computercraft.server.TurtleWebSocketServer;
import graphical.geometry.Direction;
import graphical.geometry.ModelManager;
import org.java_websocket.WebSocket;
import util.GenericJsonConverter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TurtleManager {
    private final LinkedHashMap<Integer, Turtle> turtleMap;
    private final ModelManager modelManager;
    private final ConcurrentLinkedQueue<TurtleCreationRequest> creationQueue;
    private final ConcurrentLinkedQueue<TurtleUpdateInformation> updateQueue;

    public TurtleManager(ModelManager modelManager) {
        turtleMap = new LinkedHashMap<>();
        this.modelManager = modelManager;
        creationQueue = new ConcurrentLinkedQueue<>();
        updateQueue = new ConcurrentLinkedQueue<>();
    }

    public void addTurtle(Message msg, WebSocket conn, TurtleWebSocketServer turtleWebSocketServer) {
        TurtleCreationRequest request = new TurtleCreationRequest(msg, conn, turtleWebSocketServer);
        creationQueue.add(request);
    }

    public void update() {
        while (!creationQueue.isEmpty()) {
            TurtleCreationRequest request = creationQueue.poll();
            Map<String, Object> content = GenericJsonConverter.convertToMap(request.getMsg().getContent());

            int xPosition = (int) content.get("xPosition");
            int yPosition = (int) content.get("yPosition");
            int zPosition = (int) content.get("zPosition");

            Vector3 position = new Vector3(xPosition, yPosition, zPosition);
            Direction direction = Direction.toValue((String) content.get("direction"));

            Turtle turtle = new Turtle(request.getMsg().getTurtleId(), position, direction, request.getConn(), request.getTurtleWebSocketServer(), updateQueue);
            modelManager.createAndAddTurtleModel(position, direction);
            turtleMap.put(turtle.getId(), turtle);
        }
        while (!updateQueue.isEmpty()) {
            TurtleUpdateInformation updateInformation = updateQueue.poll();
            modelManager.updateTurtleModelPosition(updateInformation.oldPosition(), updateInformation.newPosition());
        }
    }
}


