package com.spacesimulation.factories;

import com.spacesimulation.domain.GuidedMissile;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.utils.Point3D;

/**
 * Single point of GuidedMissile creation.
 * @author Steven Muschler
 */
public class GuidedMissileFactory 
{
    /**
     * Calls the GuidedMissile Constructor.
     * @param side The side/color that the GuidedMissile is on.
     * @param loc The location of the GuidedMissile.
     * @param des The destination of the GuidedMissile.
     * @param tarId The String id of the object the GuidedMissile is targeting.
     * @param speed The speed of the GuidedMissile.
     * @param angle The angle of the GuidedMissile.
     * @param maxSt The maximum strength of the GuidedMissile.
     * @param detRange The detonation range of the GuidedMissile.
     * @param dam The damage the GuidedMissile deals.
     * @param dur The length of time the GuidedMissile lasts before detonation.
     * @return The new GuidedMissile object.
     * @throws InvalidDoubleException thrown if speed, maxSt, detRange, or dam < 0.
     * @throws InvalidIntegerException thrown if dur < 0.
     * @throws ColorNotFoundException thrown if side does not exist.
     * @throws NullObjectException thrown if side is null.
     */
    public static GuidedMissile build(String side, Point3D loc, Point3D des, String tarId, double speed, double angle, double maxSt, double detRange, double dam, int dur) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException
    {
        return new GuidedMissile(side, loc, des, tarId, speed, angle, maxSt, detRange, dam, dur);
    }
}
