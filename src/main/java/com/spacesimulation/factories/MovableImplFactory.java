package com.spacesimulation.factories;

import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.utils.Movable;
import com.spacesimulation.utils.MovableSpaceCraftImpl;
import com.spacesimulation.utils.MovableWeaponImpl;
import com.spacesimulation.utils.NullMovableImpl;
import com.spacesimulation.utils.Point3D;

/**
 * Single point of creation for classes that implement the Movable interface.
 * @author Steven Muschler
 */
public class MovableImplFactory 
{
    
    /**
     * Calls constructors of classes that implement Movable and do not need a set starting location or destination.
     * @param shipType The type of SpaceEntity that owns the reference to the Impl.
     * @param spd The speed that the Impl stores.
     * @param angle The angle that the Impl stores.
     * @return A new MovableImpl.
     * @throws InvalidDoubleException thrown if spd < 0. 
     */
    public static Movable createMovable(String shipType, double spd, double angle) throws InvalidDoubleException
    {
        return new MovableSpaceCraftImpl(spd, angle);
    }
    
    /**
     * Calls constructors of classes that implement Movable and need a set starting location or destination.
     * @param shipType The type of SpaceEntity that owns the reference to the Impl.
     * @param loc The location that the Impl stores.
     * @param dest The destination that the Impl stores.
     * @param spd The speed that the Impl stores.
     * @param angle The angle that the Impl stores.
     * @return A new MovableImpl.
     * @throws InvalidDoubleException InvalidDoubleException thrown if spd < 0. 
     */
    public static Movable createMovable(String shipType, Point3D loc, Point3D dest, double spd, double angle) throws InvalidDoubleException
    {
        if (shipType.equalsIgnoreCase("Debris Cloud"))
            return new NullMovableImpl(spd, angle, loc);
        else
            return new MovableWeaponImpl(shipType, loc, dest, spd, angle);
    }
}
