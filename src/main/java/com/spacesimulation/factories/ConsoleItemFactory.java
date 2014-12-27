package com.spacesimulation.factories;

import com.spacesimulation.display.ConsoleItem;
import java.awt.Color;
import com.spacesimulation.utils.ConsoleItemImpl;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;

/**
 * Single point of ConsoleItem creation.
 * @author Steven Muschler
 */
public class ConsoleItemFactory 
{
    /**
     * Calls the ConsoleItemImpl Constructor.
     * @param idIn The String id of the object.
     * @param loc The location of the object.
     * @param c The Color of the object.
     * @param ang The object's angle of travel.
     * @param poly The object's Polygon.
     * @param txt The object's informational text.
     * @param des Whether the object is destroyed or not.
     * @param dam Whether the object is damaged or not.
     * @return The new ConsoleItem.
     */
    public static ConsoleItem createConsoleItem(String idIn, Point3D loc, Color c, double ang, PolygonPlus poly, String txt, boolean des, boolean dam)
    {
        return new ConsoleItemImpl(idIn, loc, c, ang, poly, txt, des, dam);
    }    
}
