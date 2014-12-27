package com.spacesimulation.factories;

import com.spacesimulation.domain.DebrisCloud;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.utils.Point3D;

/**
 * Single point of DebrisCloud creation.
 * @author Steven Muschler
 */
public class DebrisCloudFactory 
{
    /**
     * Calls the DebrisCloud Constructor.
     * @param sFactor The size factor of the DebrisCloud.
     * @param dTime The duration time of the DebrisCloud.
     * @param color The String representation of the color of the DebrisCloud.
     * @param loc The location of the DebrisCloud.
     * @param tar Whether the DebrisCloud is targetable or not.
     * @return The new DebrisCloud object.
     * @throws InvalidDoubleException thrown if sFactor < 0.
     * @throws InvalidIntegerException thrown if dTime < 0.
     * @throws ColorNotFoundException thrown if color does not exist.
     * @throws NullObjectException thrown if color is null.
     */
    public static DebrisCloud build(double sFactor, int dTime, String color, Point3D loc, boolean tar) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException
    {
        return new DebrisCloud(sFactor, dTime, color, loc, tar);
    } 
}
