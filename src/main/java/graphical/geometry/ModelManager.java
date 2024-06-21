package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import graphical.controller.camera.Camera;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelManager {
    private final Map<Vector3, ModelInstance> positionToModelMap;
    private final ModelBatch modelBatch;
    private final Camera camera;

    public ModelManager(Camera camera) {
        this.camera = camera;
        this.modelBatch = new ModelBatch();
        this.positionToModelMap = new LinkedHashMap<>();
    }

    public void updateGraphics() {
        modelBatch.begin(camera);
        modelBatch.render(positionToModelMap.values());
        modelBatch.end();
    }

    public Map<Vector3, ModelInstance> getPositionToModelMap() {
        return positionToModelMap;
    }

    public void addModel(Vector3 position, ModelInstance modelInstance) {
        positionToModelMap.put(position, modelInstance);
    }

    public void removeModelUsingPosition(Vector3 position) {
        positionToModelMap.remove(position);
    }

    public void dispose() {
        modelBatch.dispose();
    }

    public void populateWithTestModels() {
        ModelInstance modelInstance = new CubeBuilder()
                .setColor(Color.BLUE)
                .setPosition(0, -1, 0)
                .setOpacity(0.5f)
                .create();

        positionToModelMap.put(new Vector3(0, -1, 0), modelInstance);
    }

    public void createAndAddTurtleModel(Vector3 position, Direction direction) {
        TurtleModel turtleModel = new TurtleModel(position, direction);
        positionToModelMap.put(position.cpy(), turtleModel.getModelInstance());
    }

    public void updateTurtleModelPosition(Vector3 oldPosition, Vector3 newPosition) {
        ModelInstance modelInstance = positionToModelMap.remove(oldPosition);
        if (modelInstance == null) {
            return;
        }
        modelInstance.transform.setTranslation(newPosition);
        positionToModelMap.put(newPosition, modelInstance);
    }
}
