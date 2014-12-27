package com.spacesimulation.factories;

import java.awt.Color;
import com.spacesimulation.exceptions.ColorNotFoundException;
/**
 * Single point of creation for Color objects.
 * @see java.awt.Color
 * @author Steven Muschler
 */

public class ColorFactory 
{
    /**
     * Builds Color objects
     * @param color The string representation of the Color object to be built.
     * @return The new Color object.
     * @throws ColorNotFoundException thrown if the String color does not match one found in the ColorFactory. 
     */
    public static Color build(String color) throws ColorNotFoundException
    {
        if (color.equalsIgnoreCase("BLUE"))
            return Color.BLUE;
        else if (color.equalsIgnoreCase("RED"))
            return Color.RED;
        else if (color.equalsIgnoreCase("GRAY"))
            return Color.GRAY;
        else if (color.equalsIgnoreCase("ORANGE"))
            return Color.ORANGE;
        else if (color.equalsIgnoreCase("YELLOW"))
            return Color.YELLOW;
        else if (color.equalsIgnoreCase("CYAN"))
            return Color.CYAN;
        else if (color.equalsIgnoreCase("MAGENTA"))
            return Color.MAGENTA;
        else if (color.equalsIgnoreCase("GREEN"))
            return Color.GREEN;
        else
            throw new ColorNotFoundException("This color is not supported: " + color);
    }
}
