package com.spacesimulation.utils;

import com.spacesimulation.exceptions.InvalidIntegerException;

/**
 * A interface that represents a MovableImpl.  SpaceEntities delegate
 * their identity information to the Movable interface.
 * 
 * @author Steven Muschler
 */

public interface Movable 
{
    /**
     * Movement algorithm.
     * @throws InvalidIntegerException If timeInterval if <= 0.
     * @param timeInterval the amount time that the caller should move.
     */
    public void move(int timeInterval) throws InvalidIntegerException;
    
    /**
     * Returns object's current location.
     * @see com.spacesimulation.utils.Point3D
     * @return Point3D that contains the objects current location.
     */
    public Point3D getLocation();
    
    /**
     * Returns object's current destination.
     * @see com.spacesimulation.utils.Point3D
     * @return Point3D that contains the objects destination.
     */
    public Point3D getDestination();
    
    /**
     * Returns objects current speed.
     * @return Double that contains the objects speed.
     */
    public double getSpeed();
    
    /**
     * Returns objects current angle of travel.
     * @return Double that contains current angle of travel.
     */
    public double getAngle();
    
    /*
     * Sets the objects destination.  The new destination is a randomly selected point.
     */
    public void setDestination();
    
    /**
     * Sets the object's angle of travel.
     * @param ang The objects new angle of travel.
     */
    public void setAngle(double ang);
    
    /**
     * Sets the object's destination to the specified point.
     * @param p Object's new destination.
     * @see com.spacesimulation.utils.Point3D
     */
    public void setDestination(Point3D p);
    
    /**
     * Sets the object's location to the specified point.
     * @param p Object's new location.
     * @see com.spacesimulation.utils.Point3D
     */
    public void setLocation(Point3D p);
    
    /**
     * If the object's location == the object's destination, return true, otherwise false.
     * @return Boolean that tells whether an object is at its destination.
     */
    public boolean atDestination();
    
    /**
     * Behavior to be executed if the object is destroyed.
     */
    public void detonate();
}
