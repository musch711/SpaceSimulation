package com.spacesimulation.factories;

import com.spacesimulation.domain.SpacePort;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.NullObjectException;

/**
 * Single point of SpacePort creation.
 * @author Steven Muschler
 */
public class SpacePortFactory 
{
    /**
     * Calls the SpacePort Constructor.
     * @param c The color/side of the SpacePort.
     * @param ang The angle of the SpacePort.
     * @param sp The speed of the SpacePort.
     * @param des Whether the SpacePort is destroyed or not.
     * @param dam Whether the SpacePort is damaged or not.
     * @param mStrength The maximum strength of the SpacePort.
     * @return The new SpacePort object.
     * @throws InvalidDoubleException thrown if sp or mStrength < 0.
     * @throws ColorNotFoundException thrown if c does not exist.
     * @throws NullObjectException thrown if c is null. 
     */
    public static SpacePort build(String c, double ang, double sp, boolean des, boolean dam, double mStrength) throws InvalidDoubleException, ColorNotFoundException, NullObjectException
    {
        return new SpacePort(c, ang, sp, des, dam, mStrength);
    }
}

