package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import computercraft.block.Block;
import computercraft.turtle.Turtle;
import graphical.controller.camera.Camera;
import util.StringToColor;

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
    private final ConcurrentLinkedQueue<Block> blocksToAddQueue;

    public ModelManager(Camera camera) {
        this.camera = camera;
        this.modelBatch = new ModelBatch();
        this.blockModelMap = new LinkedHashMap<>();
        this.turtleModelMap = new LinkedHashMap<>();

        this.dirtyTurtleQueue = new ConcurrentLinkedQueue<>();
        this.blocksToAddQueue = new ConcurrentLinkedQueue<>();
    }

    public void updateGraphics() {
        modelBatch.begin(camera);
        modelBatch.render(blockModelMap.values());
        modelBatch.render(turtleModelMap.values());
        flushDirtyModels();
        addNewBlocks();
        modelBatch.end();
    }

    public TurtleModel createAndAddTurtleModel(Vector3 position, Direction direction) {
        return new TurtleModel(position, direction);
    }

    public void addTurtle(Turtle turtle) {

        dirtyTurtleQueue.add(turtle);
    }

    public void flushDirtyModels() {
        while (!dirtyTurtleQueue.isEmpty()) {
            Turtle turtle = dirtyTurtleQueue.poll();
            ModelInstance modelInstance = turtleModelMap.computeIfAbsent(turtle,
                    t -> createAndAddTurtleModel(t.getPosition(), t.getDirection()).getModelInstance());

            modelInstance.transform.setTranslation(turtle.getPosition());
        }
    }

    private void addNewBlocks() {
        while (!blocksToAddQueue.isEmpty()) {
            Block block = blocksToAddQueue.poll();
            Vector3 position = new Vector3(block.getX(), block.getY(), block.getZ());
            ModelInstance blockModel = new CubeBuilder()
                    .setColor(StringToColor.hashStringToColor(block.getDisplayName()))
//                    .setColor(Color.BLACK)
                    .setPosition(position)
                    .setOpacity(0.5f)
                    .create();

            blockModelMap.put(position, blockModel);
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

    public Queue<Block> getBlocksToAddQueue() {
        return blocksToAddQueue;
    }

    public void dispose() {
        modelBatch.dispose();
    }

}
