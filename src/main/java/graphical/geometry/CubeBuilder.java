package graphical.geometry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class CubeBuilder {
    private static final ModelBuilder MODEL_BUILDER = new ModelBuilder();
    private Color color;
    private Vector3 position;
    private float opacity;

    public CubeBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public CubeBuilder setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    public CubeBuilder setPosition(int x, int y, int z) {
        position = new Vector3(x, y, z);
        return this;
    }

    public ModelInstance create() {
        Material material = new Material();
        material.set(ColorAttribute.createDiffuse(color));
        material.set(new BlendingAttribute(true, opacity));

        Model box = MODEL_BUILDER.createBox(1, 1, 1, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        return new ModelInstance(box, position);
    }
}
