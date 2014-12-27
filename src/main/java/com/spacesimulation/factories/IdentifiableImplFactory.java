package com.spacesimulation.factories;

import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.utils.Identifiable;
import com.spacesimulation.utils.IdentifiableImpl;

/**
 * Single point of creation for classes that implement Identifiable.
 * @author Steven Muschler
 */
public class IdentifiableImplFactory 
{
    /**
     * Calls constructor depending on parameters.
     * @param color The color/side of the object that owns the reference to the Impl.
     * @param shipType The type of SpaceEntity that owns the reference to the Impl.
     * @return An IndentifiableImpl.
     * @throws ColorNotFoundException thrown if color does not exist. 
     * @throws NullObjectException thrown if color is null.
     */
    public static Identifiable createIdentifiableImpl(String color, String shipType) throws ColorNotFoundException, NullObjectException
    {
        return new IdentifiableImpl(color, shipType);
    }
}
