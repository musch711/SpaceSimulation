package com.spacesimulation.utils;

import java.awt.Color;

/**
 * A interface that represents a IdentifiableImpl.  SpaceEntities delegate
 * their identity information to the Identifiable interface.
 * 
 * @author Steven Muschler
 */

public interface Identifiable 
{
    /**
     * @return String representation of the objects id.
     */
    String getId();
    
    /**
     * @return Color representation of the object.
     * @see java.awt.Color
     */
    Color getColor();
    
    /**
     * @return String representation of the object's color.
     */
    String getColorSt();
}
