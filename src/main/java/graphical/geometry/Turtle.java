package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Turtle {

    private final ModelInstance modelInstance;
    private final Direction direction;

    public Turtle(Vector3 position, Direction direction) {
        modelInstance = new ModelInstance(createModel());
        modelInstance.transform.translate(position);
        modelInstance.transform.rotate(Direction.toQuaternion(direction));

        this.direction = direction;
    }

    private ModelInstance createModel() {
        return new CubeBuilder()
                .setColor(Color.YELLOW)
                .setPosition(0, 0, 0)
                .create();
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void move() {
        Quaternion quaternion = Direction.toQuaternion(direction);
        modelInstance.transform.translate(quaternion.x, quaternion.y, quaternion.z);
    }
}
