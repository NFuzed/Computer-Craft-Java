package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import computercraft.turtle.Turtle;
import graphical.controller.camera.Camera;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ModelManager {
    private final Map<Vector3, ModelInstance> blockModelMap;
    private final ModelBatch modelBatch;
    private final Camera camera;
    private final ConcurrentLinkedQueue<Turtle> dirtyTurtleQueue;
    private final LinkedHashMap<Turtle, ModelInstance> turtleModelMap;

    public ModelManager(Camera camera) {
        this.camera = camera;
        this.modelBatch = new ModelBatch();
        this.blockModelMap = new LinkedHashMap<>();
        this.turtleModelMap = new LinkedHashMap<>();

        this.dirtyTurtleQueue = new ConcurrentLinkedQueue<>();
    }

    public void updateGraphics() {
        modelBatch.begin(camera);
        modelBatch.render(blockModelMap.values());
        flushDirtyModels();
        modelBatch.end();
    }

    public TurtleModel createAndAddTurtleModel(Vector3 position, Direction direction) {
        return new TurtleModel(position, direction);
    }

    public void updateTurtleModelPosition(Vector3 oldPosition, Vector3 newPosition) {
        ModelInstance modelInstance = blockModelMap.remove(oldPosition);
        if (modelInstance == null) {
            return;
        }
        modelInstance.transform.setTranslation(newPosition);
        blockModelMap.put(newPosition, modelInstance);
    }

    public void addTurtle(Turtle turtle) {
        turtleModelMap.put(turtle, createAndAddTurtleModel(turtle.getPosition(), turtle.getDirection()).getModelInstance());
        dirtyTurtleQueue.add(turtle);
    }

    public void flushDirtyModels() {
        while (!dirtyTurtleQueue.isEmpty()) {
            Turtle turtle = dirtyTurtleQueue.poll();
            ModelInstance modelInstance = turtleModelMap.get(turtle);

            if (modelInstance == null) {
                System.out.println("Model not found for turtle: " + turtle.getId());
                continue;
            }

            modelInstance.transform.setTranslation(turtle.getPosition());
        }
    }

    public void populateWithTestModels() {
        ModelInstance modelInstance = new CubeBuilder()
                .setColor(Color.BLUE)
                .setPosition(0, -1, 0)
                .setOpacity(0.5f)
                .create();

        blockModelMap.put(new Vector3(0, -1, 0), modelInstance);
    }

    public Queue<Turtle> getDirtyTurtleQueue() {
        return dirtyTurtleQueue;
    }

    public void dispose() {
        modelBatch.dispose();
    }

}
