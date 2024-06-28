package computercraft.block;

import com.badlogic.gdx.math.Vector3;
import graphical.geometry.Direction;

import java.util.Map;

public class Block {


    private int x;
    private int y;
    private int z;
    private final String displayName;

    public Block(Map<String, Object> unconvertedValues, Vector3 translation, Direction direction) {
        this.y = (int) translation.y + (int) unconvertedValues.get("y");

        switch (direction) {
            case NORTH:
                this.x = (int) translation.x + (int) unconvertedValues.get("z");
                this.z = (int) translation.z - (int) unconvertedValues.get("x");
                break;
            case EAST:
                this.x = (int) translation.x + (int) unconvertedValues.get("x");
                this.z = (int) translation.z + (int) unconvertedValues.get("z");
                break;
            case SOUTH:
                this.x = (int) translation.x - (int) unconvertedValues.get("z");
                this.z = (int) translation.z + (int) unconvertedValues.get("x");
                break;
            case WEST:
                this.x = (int) translation.x - (int) unconvertedValues.get("x");
                this.z = (int) translation.z - (int) unconvertedValues.get("z");
                break;
        }

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
