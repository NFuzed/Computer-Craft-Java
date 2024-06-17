package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class CubeBuilder {
    private static final ModelBuilder MODEL_BUILDER = new ModelBuilder();
    private Color color;
    private Vector3 position;

    public CubeBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public CubeBuilder setPosition(int x, int y, int z) {
        position = new Vector3(x, y, z);
        return this;
    }

    public ModelInstance create() {
        Material material = new Material(ColorAttribute.createDiffuse(color));
        Model box = MODEL_BUILDER.createBox(1, 1, 1, material, VertexAttributes.Usage.Position);
        return new ModelInstance(box, position);
    }
}
