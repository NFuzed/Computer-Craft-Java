package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class TurtleModel {

    private final ModelInstance modelInstance;

    public TurtleModel(Vector3 position, Direction direction) {
        modelInstance = new ModelInstance(createModel());
        modelInstance.transform.translate(position);
        modelInstance.transform.rotate(Direction.toQuaternion(direction));
    }

    private ModelInstance createModel() {
        return new CubeBuilder()
                .setColor(Color.YELLOW)
                .setPosition(0, 0, 0)
                .setOpacity(1)
                .create();
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }
}
