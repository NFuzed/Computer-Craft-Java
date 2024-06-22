package util;

import com.badlogic.gdx.graphics.Color;

public class StringToColor {

    private StringToColor() {
    }

    public static Color hashStringToColor(String str) {
        // Get the hash code of the string and ensure it's positive
        int hash = Math.abs(str.hashCode());

        // Use bitwise operations to extract RGB components
        int r = (hash & 0xFF0000) >> 16; // Extract red component
        int g = (hash & 0x00FF00) >> 8;  // Extract green component
        int b = (hash & 0x0000FF);       // Extract blue component

        // Normalize RGB components to the range [0, 1]
        float rNorm = r / 255f;
        float gNorm = g / 255f;
        float bNorm = b / 255f;

        // Create and return a color from the normalized RGB components
        return new Color(rNorm, gNorm, bNorm, 1f); // Alpha is set to 1 (opaque)
    }
}
