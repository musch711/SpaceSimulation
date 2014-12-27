package com.spacesimulation.utils;

import com.spacesimulation.exceptions.InvalidDoubleException;

/**
 * Impl that implements the Movable interface.  This Impl is for SpaceEntities that do not move, such as DebrisClouds.
 * @author Steven Muschler
 */
public class NullMovableImpl implements Movable
{
    private Point3D location;
    private Point3D destination;
    private double speed;
    private double angle;
    
    /**
     * NullMovableImpl's Constructor.
     * @param spd The speed that the Impl stores.
     * @param ang The angle that the Impl stores.
     * @param loc The location that the Impl stores.
     * @throws InvalidDoubleException thrown if spd < 0.
     */
    public NullMovableImpl(double spd, double ang, Point3D loc) throws InvalidDoubleException
    {
        setSpeed(spd);
        setAngle(ang);
        location = loc;
        destination = location;
    }

    /**
     * Impl's movement algorithm.  Does nothing as this Impl does not move.
     * @param timeInterval The amount of time for the Impl to travel.
     */
    public void move(int timeInterval)
    {
    }

    /**
     * @return The location that the Impl is currently storing.
     */
    public Point3D getLocation() 
    {
        return location;
    }

    /**
     * @return The location that the Impl is currently storing as location == destination.
     */
    public Point3D getDestination() 
    {
        return destination;
    }

    /**
     * @return The current speed the Impl is storing.
     */
    public double getSpeed() 
    {
        return speed;
    }

    /**
     * @return The angle that the Impl is currently storing.  Note: this value is random.
     */
    public double getAngle() 
    {
        return Math.toRadians(Math.random()*360.0);
    }

    /**
     * Sets the Impl's location.
     * @param p The Impl's new location.
     */
    public void setLocation(Point3D p) 
    {
        location = p;
    }
    
    /**
     * Set's the Impl's destination.
     * @param p The Impl's new destination.
     */
    public void setDestination(Point3D p) 
    {
        location = p;
    }

    /**
     * This method does nothing.
     */
    public void setDestination() 
    {
    }

    /**
     * @return true, this Impl does not travel, so it is always at its destination. 
     */
    public boolean atDestination() 
    {
        return true;
    }
    
    private void setSpeed(double d) throws InvalidDoubleException
    {
        if (d < 0)
            throw new InvalidDoubleException("Negative Speed Encountered: " + d);
        speed = d;
    }
    
    public void setAngle(double d)
    {
        angle = d;
    }

    /**
     * The Impl's detonate behavior.  This Impl has no detonate behavior, so this method does nothing.
     */
    public void detonate() {
    }
}
