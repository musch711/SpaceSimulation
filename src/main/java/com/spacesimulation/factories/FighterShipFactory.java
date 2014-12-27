package com.spacesimulation.factories;

import com.spacesimulation.domain.FighterShip;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;

/**
 * Single point of FighterShip creation.
 * @author Steven Muschler
 */
public class FighterShipFactory 
{
    /**
     * Calls the FighterShip Constructor.
     * @param color The color/side of the FighterShip.
     * @param ang The angle of the FighterShip.
     * @param sp The speed of the FighterShip.
     * @param mStrength The maximum strength of the FighterShip.
     * @param missiles The number of GuidedMissiles the FighterShip is initialized with.
     * @return The new FighterShip object.
     * @throws InvalidDoubleException thrown if sp or mStrength < 0.
     * @throws InvalidIntegerException thrown if missiles < 0.
     * @throws ColorNotFoundException thrown if color does not exist.
     * @throws NullObjectException thrown if color is null.
     */
    public static FighterShip build(String color, double ang, double sp, double mStrength, int missiles) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException
    {
        return new FighterShip(color, ang, sp, mStrength, missiles);
    }
}
