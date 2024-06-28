package computercraft.turtle;

import com.badlogic.gdx.math.Vector3;
import computercraft.server.Message;
import computercraft.server.TurtleWebSocketServer;
import graphical.geometry.Direction;
import graphical.geometry.ModelManager;
import org.java_websocket.WebSocket;
import util.GenericJsonConverter;
import util.observer.Observable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TurtleManager extends Observable<Turtle> {
    private final LinkedHashMap<Integer, Turtle> turtleMap;
    private final ModelManager modelManager;
    private final ConcurrentLinkedQueue<TurtleCreationRequest> creationQueue;

    public TurtleManager(ModelManager modelManager) {
        this.modelManager = modelManager;
        turtleMap = new LinkedHashMap<>();
        creationQueue = new ConcurrentLinkedQueue<>();
    }

    public void addTurtle(Message msg, WebSocket conn, TurtleWebSocketServer turtleWebSocketServer) {
        TurtleCreationRequest request = new TurtleCreationRequest(msg, conn, turtleWebSocketServer);
        creationQueue.add(request);
    }

    public void update() {
        while (!creationQueue.isEmpty()) {
            TurtleCreationRequest request = creationQueue.poll();

            if (turtleMap.get(request.getMsg().getTurtleId()) != null) {
                WebsocketInformation websocketInformation = new WebsocketInformation(request.getConn(), request.getTurtleWebSocketServer());
                turtleMap.get(request.getMsg().getTurtleId()).setWebsocketInformation(websocketInformation);
                continue;
            }

            Map<String, Object> content = GenericJsonConverter.convertToMap(request.getMsg().getContent());

            int xPosition = (int) content.get("xPosition");
            int yPosition = (int) content.get("yPosition");
            int zPosition = (int) content.get("zPosition");

            Vector3 position = new Vector3(xPosition, yPosition, zPosition);
            Direction direction = Direction.toValue((String) content.get("direction"));

            TurtleAttributes turtleAttributes = new TurtleAttributes(request.getMsg().getTurtleId(), position, direction);
            WebsocketInformation websocketInformation = new WebsocketInformation(request.getConn(), request.getTurtleWebSocketServer());
            Turtle turtle = new Turtle(turtleAttributes, websocketInformation, modelManager.getDirtyTurtleQueue(), modelManager.getBlocksToAddQueue());

            modelManager.addTurtle(turtle);
            addTurtleToMap(turtle);
        }
    }

    private void addTurtleToMap(Turtle turtle) {
        turtleMap.put(turtle.getId(), turtle);
        notifyObservers(turtle);
    }

    public LinkedHashMap<Integer, Turtle> getTurtleMap() {
        return turtleMap;
    }
}


