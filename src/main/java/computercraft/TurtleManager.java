package computercraft;

import com.badlogic.gdx.math.Vector3;
import graphical.geometry.Direction;
import graphical.geometry.ModelManager;
import org.java_websocket.WebSocket;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TurtleManager {
    private final LinkedHashMap<Integer, Turtle> turtleMap;
    private final ModelManager modelManager;
    private final ConcurrentLinkedQueue<TurtleCreationRequest> creationQueue;

    public TurtleManager(ModelManager modelManager) {
        turtleMap = new LinkedHashMap<>();
        this.modelManager = modelManager;
        creationQueue = new ConcurrentLinkedQueue<>();
    }

    public void addTurtle(ConnectionInformation msg, WebSocket conn) {
        TurtleCreationRequest request = new TurtleCreationRequest(msg, conn);
        creationQueue.add(request);
    }

    public void update() {
        while (!creationQueue.isEmpty()) {
            TurtleCreationRequest request = creationQueue.poll();
            Vector3 position = new Vector3(request.getMsg().getxPosition(), request.getMsg().getyPosition(), request.getMsg().getzPosition());
            Direction direction = Direction.toValue(request.getMsg().getDirection());

            Turtle turtle = new Turtle(request.getMsg().getId(), position, direction, request.getConn());
            modelManager.createTurtleModel(position, direction);
            turtleMap.put(turtle.getId(), turtle);
        }
    }
}


