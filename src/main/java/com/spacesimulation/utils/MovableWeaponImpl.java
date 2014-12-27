package com.spacesimulation.utils;

import com.spacesimulation.domain.DebrisCloud;
import com.spacesimulation.domain.EntityManager;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.factories.DebrisCloudFactory;

/**
 * Impl that implements the Movable interface.  The behavior in this class is designed for SpaceEntities like GuidedMissile and RailGunBolt.
 * @author Steven Muschler
 */
public class MovableWeaponImpl implements Movable 
{
    private Point3D location;
    private Point3D destination;
    private double speed;
    private double angle;
    
    private boolean atDestination = false;

    /**
     * MovableWeaponImpl's Constructor
     * @param shipType The String representation of the type that owns this reference.
     * @param loc The location the Impl is storing.
     * @param dest The destination the Impl is storing.
     * @param speed The speed the Impl is storing.
     * @param angle The angle the Impl is storing.
     * @throws InvalidDoubleException thrown if speed < 0.
     */
    public MovableWeaponImpl(String shipType, Point3D loc, Point3D dest, double speed, double angle) throws InvalidDoubleException
    {
        location = loc;
        destination = dest;
        setSpeed(speed);
        setAngle(angle);
    }
    
    /**
     * Movement algorithm for this Impl.  The algorithm is modeled so that when the Impl reaches its destination, then it will perform its detonate behavior.
     * @param timeInterval The amount of time for the Impl to move.
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
                atDestination = true;
                location = new Point3D(destination);
                detonate();
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
     * @return The location the Impl is storing.
     */
    public Point3D getLocation() 
    {
        return location;
    }

    /**
     * @return The destination the Impl is storing. 
     */
    public Point3D getDestination() 
    {
        return destination;
    }

    /**
     * @return The speed the Impl is storing. 
     */
    public double getSpeed() 
    {
        return speed;
    }

    /**
     * @return The angle the Impl is storing. 
     */
    public double getAngle() 
    {
        return angle;
    }

    /**
     * Sets the Impl's destination to a random point.
     */
    public void setDestination() 
    {
        destination = EntityManager.getInstance().createRandomPoint();
    }
    
    /**
     * Sets the Impl's location to the point specified.
     * @param p The Impl's new location.
     */
    public void setLocation(Point3D p)
    {
        location = p;
    }
    
    /**
     * Sets the Impl's destination to the point specified.
     * @param p The Impl's new destination.
     */
    public void setDestination(Point3D p) 
    {
        destination = p;
    }

    /**
     * @return True is destination == location, otherwise false.
     */
    public boolean atDestination() 
    {
        return atDestination;
    }
    
    private void setSpeed(double sp) throws InvalidDoubleException
    {
        if (sp < 0)
            throw new InvalidDoubleException("Negative Speed Encountered: " + sp);
        
        speed = sp;
    }
    
    /**
     * Sets the Impl's angle to the double specified.
     * @param ang The Impl's new angle.
     */
    public void setAngle(double ang)
    {   
        angle = ang;
    }
    
    /**
     * The Impl's detonate behavior.  The Impl creates a small DebrisCloud at the Impl's current location.
     */
    public void detonate()
    {
        DebrisCloud dc;
        try 
        {
            dc = DebrisCloudFactory.build(0.25, 40, "YELLOW", getLocation(), false);
            EntityManager.getInstance().addEntity(dc);
        } 
        catch (InvalidDoubleException ex) 
        {
            System.out.println(ex);
        } 
        catch (InvalidIntegerException ex) 
        {
            System.out.println(ex);
        } 
        catch (ColorNotFoundException ex) 
        {
            System.out.println(ex);
        } 
        catch (NullObjectException ex) 
        {
            System.out.println(ex);
        }
    }
}
