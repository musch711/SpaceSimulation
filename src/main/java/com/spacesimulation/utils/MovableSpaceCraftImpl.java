package com.spacesimulation.utils;

import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.domain.EntityManager;

/**
 * Impl that implements the Movable interface.  Designed for moving SpaceEntities such as CargoShip and FighterShip.
 * @author Steven Muschler
 */

public class MovableSpaceCraftImpl implements Movable
{
    private Point3D location;
    private Point3D destination;
    private double speed;
    private double angle;
    
    /**
     * Impl's Constructor
     * @param spd The speed of the object that owns the reference to the Impl.
     * @param ang The angle of the object that owns the reference to the Impl.
     * @throws InvalidDoubleException thrown if spd < 0.
     */
    public MovableSpaceCraftImpl(double spd, double ang) throws InvalidDoubleException
    {
        setSpeed(spd);
        setAngle(ang);
        location = EntityManager.getInstance().createRandomPoint();
        destination = EntityManager.getInstance().createRandomPoint();
    }
    
    /**
     * Impl's Movement algorithm.  The algorithm has the object travel to a random location.
     * Once the object reaches the location, the algorithm chooses a new destination for the
     * object to travel to.
     * @param timeInterval The amount of time for the object to travel.
     * @throws InvalidIntegerException thrown if timeInterval < 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        Point3D locationOld = getLocation();
        double distanceTraveled = getSpeed() * timeInterval;
        double distanceToGo = destination.distance(location);
        
        if (distanceToGo > 0)
        {
            if (distanceTraveled >= distanceToGo)
            {
                location = new Point3D(destination);
            }
            else
            {
                double delta = distanceTraveled / distanceToGo;
                double newX = location.getX() + (destination.getX() - location.getX()) * delta;
                double newY = location.getY() + (destination.getY() - location.getY()) * delta;
                double newZ = location.getZ() + (destination.getZ() - location.getZ()) * delta;
                location = new Point3D(newX, newY, newZ);
                
                double nX = location.getX() - locationOld.getX();
                double nY = location.getY() - locationOld.getY();
                double newAngle = Math.atan2(nY, nX) + (Math.PI / 2.0);
                angle = newAngle;
            }
        }
    }
    
    /**
     * @return The stored location.
     */
    public Point3D getLocation() 
    {
        return location;
    }
    
    /**
     * @return The stored destination.
     */
    public Point3D getDestination() 
    {
        return destination;
    }
    
    /**
     * @return The stored speed. 
     */
    public double getSpeed() 
    {
        return speed;
    }
    
    /**
     * @return The stored angle.
     */
    public double getAngle() 
    {
        return angle;
    }
    
    /**
     * Sets the destination to a random point.
     */
    public void setDestination()
    {
        destination = EntityManager.getInstance().createRandomPoint();
    }
    
    /**
     * Sets location to the point specified.
     * @param p The new location.
     */
    public void setLocation(Point3D p)
    {
        location = p;
    }
    
    /**
     * Sets destination to the point specified.
     * @param p The new destination.
     */
    public void setDestination(Point3D p) 
    {
        location = p;
    }
    
    /**
     * @return True if destination == location, otherwise false
     */
    public boolean atDestination()
    {
        if (location.distance(destination) == 0.0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private void setSpeed(double d) throws InvalidDoubleException
    {
        if (d < 0)
            throw new InvalidDoubleException("Negative Speed Encountered: " + d);
        speed = d;
    }
    
    /**
     * Sets the angle.
     * @param d The new angle.
     */
    public void setAngle(double d)
    {
        angle = d;
    }

    /**
     * Behavior for detonation.  This Impl has no detonation behavior.
     */
    public void detonate() {
    }
}
