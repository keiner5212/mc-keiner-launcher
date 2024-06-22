package minecraft.client.util;

import java.awt.Color;

public class ColorUtils {
    public static Color hexStringToColor(String hexColor) {
        hexColor = hexColor.replace("#", "");

        if (hexColor.length() != 3 && hexColor.length() != 6) {
            throw new IllegalArgumentException(
                    "Incorrect color value: " + hexColor + ". Must be 3 or 6 characters long.");
        }

        if (hexColor.length() == 3) {
            hexColor = hexColor.charAt(0) + "" + hexColor.charAt(0) +
                    hexColor.charAt(1) + "" + hexColor.charAt(1) +
                    hexColor.charAt(2) + "" + hexColor.charAt(2);
        }

        try {
            int rgb = Integer.parseInt(hexColor, 16);
            return new Color(rgb);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect color value: " + hexColor);
        }
    }
}