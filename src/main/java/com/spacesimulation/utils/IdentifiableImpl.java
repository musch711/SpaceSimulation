package com.spacesimulation.utils;

import com.spacesimulation.factories.ColorFactory;
import java.awt.Color;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.NullObjectException;

/**
 * Impl that implements the Identifiable interface.
 * @author Steven Muschler
 */

public class IdentifiableImpl implements Identifiable
{
    private String id;
    private Color color;
    private String colSt;
    private static int idGenerator = 1;
    
    /**
     * IdentifiableImpl's Constructor
     * @param c The color/side the Impl is on.
     * @param shipType The ship type of the object that owns the reference to this Impl.
     * @throws ColorNotFoundException thrown if c is not in the Color factory.
     * @throws NullObjectException thrown if c is null
     */
    public IdentifiableImpl(String c, String shipType) throws ColorNotFoundException, NullObjectException
    {
        setColorSt(c);
        color = ColorFactory.build(c);    
        id = c + " " + shipType + " " + idGenerator++;
    }
    
    /**
     * @return The Impl's String id.
     */
    public String getId() 
    {
        return id;
    }
    
    /**
     * @return The Impl's Color.
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * @return String representation of the Impl's color.
     */
    public String getColorSt()
    {
        return colSt;
    }
    
    private void setColorSt(String s) throws NullObjectException
    {
        if (s == null)
            throw new NullObjectException("Recieved null color string.");
        colSt = s;
    }
}
