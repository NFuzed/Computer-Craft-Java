package computercraft.block;

import com.badlogic.gdx.math.Vector3;

import java.util.Map;

public class Block {


    private final int x;
    private final int y;
    private final int z;
    private final String displayName;

    public Block(Map<String, Object> unconvertedValues, Vector3 translation) {
        this.x = (int) translation.x + (int) unconvertedValues.get("x");
        this.y = (int) translation.y + (int) unconvertedValues.get("y");
        this.z = (int) translation.z + (int) unconvertedValues.get("z");

        displayName = ((String) unconvertedValues.get("displayName"));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getDisplayName() {
        return displayName;
    }
}
