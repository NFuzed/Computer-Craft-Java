package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import graphical.controller.camera.Camera;

import java.util.ArrayList;
import java.util.List;

public class ModelManager {
    private final ArrayList<ModelInstance> models;
    private final ModelBatch modelBatch;
    private final Camera camera;

    public ModelManager(Camera camera) {
        this.camera = camera;
        this.modelBatch = new ModelBatch();
        this.models = new ArrayList<>();
    }

    public void renderAllModels() {
        modelBatch.begin(camera);
        modelBatch.render(models);
        modelBatch.end();
    }

    public List<ModelInstance> getModels() {
        return models;
    }

    public void addModel(ModelInstance modelInstance) {
        models.add(modelInstance);
    }

    public void dispose() {
        modelBatch.dispose();
    }

    public void populateWithTestModels() {
        ModelInstance modelInstance = new CubeBuilder()
                .setColor(Color.GREEN)
                .setPosition(0, 0, 0)
                .create();

        TurtleModel turtleModel = new TurtleModel(new Vector3(0, 0, 2), Direction.NORTH);
        turtleModel.move();
        turtleModel.move();

        models.add(modelInstance);
        models.add(turtleModel.getModelInstance());
    }

    public TurtleModel createTurtleModel(Vector3 position, Direction direction) {
        TurtleModel turtleModel = new TurtleModel(position, direction);
        models.add(turtleModel.getModelInstance());
        return turtleModel;
    }
}
