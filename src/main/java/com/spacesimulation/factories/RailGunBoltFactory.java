package com.spacesimulation.factories;

import com.spacesimulation.domain.RailGunBolt;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.utils.Point3D;

/**
 * Single point of RailGunBolt creation.
 * @author Steven Muschler
 */
public class RailGunBoltFactory 
{
    /**
     * Calls the RailGunBolt Constructor.
     * @param side The color/side of the RailGunBolt.
     * @param speed The speed of the RailGunBolt.
     * @param angle The angle of the RailGunBolt.
     * @param mStrength The maximum strength of the RailGunBolt.
     * @param detRange The detonation range of the RailGunBolt.
     * @param dam The damage the RailGunBolt deals.
     * @param loc The location of the RailGunBolt.
     * @param des The destination of the RailGunBolt.
     * @return The new RailGunBolt object.
     * @throws InvalidDoubleException thrown if speed, mStrength, detRange, or damage < 0.
     * @throws ColorNotFoundException thrown if side does not exist.
     * @throws NullObjectException thrown if side is null.
     */
    public static RailGunBolt build(String side, double speed, double angle, double mStrength, double detRange, double dam, Point3D loc, Point3D des) throws InvalidDoubleException, ColorNotFoundException, NullObjectException
    {
        return new RailGunBolt(side, speed, angle, mStrength, detRange, dam, loc, des);
    }
}
