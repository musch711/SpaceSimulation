package com.spacesimulation.domain;

import com.spacesimulation.display.ConsoleItem;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import java.awt.Color;
import com.spacesimulation.utils.Identifiable;
import com.spacesimulation.utils.Movable;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;
import com.spacesimulation.factories.MovableImplFactory;
import com.spacesimulation.factories.IdentifiableImplFactory;
import com.spacesimulation.factories.ConsoleItemFactory;
import com.spacesimulation.factories.DebrisCloudFactory;
import java.awt.Point;
import java.util.ArrayList;

/**
 * A class that implements SpaceEntity and represents a CargoShip.
 * @author Steven Muschler
 */

public class CargoShip implements SpaceEntity 
{
    /**
     * CargoShip's data members.
     */
    
    private static String shipType = "Cargo Ship";
    private PolygonPlus polygon;
    private boolean destroyed;
    private boolean damaged;
    private boolean atPort;
    private double strength;
    private double maxStrength;
    private int debrisClouds;
    private String infoText;
    
    /**
     * CargoShip's references to myMover and myId impls which handle
     * the ships movement and identity respectively.
     * @see com.spacesimulation.domain.SpaceEntity
     */
    private Movable myMover;
    private Identifiable myId;

    /**
     * Constructor for CargoShip.
     * @param color The string representation of the color that CargoShip is.
     * @param ang The CargoShip's angle of travel.
     * @param sp The CargoShip's speed.
     * @param mStrength The CargoShip's maximum strength.
     * @param clouds The number of DebrisClouds that CargoShip is initiated with.
     * @throws InvalidDoubleException Thrown if sp and mStrength are less than 0.
     * @throws InvalidIntegerException Thrown if clouds is less than 0.
     * @throws ColorNotFoundException Thrown if color does not exist.
     * @throws NullObjectException Thrown if color is null.
     */
    public CargoShip(String color, double ang, double sp, double mStrength, int clouds) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException 
    {
        buildShape();
        destroyed = false;
        damaged = false;
        atPort = false;
        setStrength(mStrength);
        setMaxStrength(mStrength);
        setDebrisClouds(clouds);
        
        myMover = MovableImplFactory.createMovable(shipType, sp, ang);

        myId = IdentifiableImplFactory.createIdentifiableImpl(color, shipType);
        
        updateInfoText();
    }
    
    /**
     * Movement algorithm for CargoShip.  Delegated to myMover impl.
     * @param timeInterval The amount of time for the object to travel.
     * @throws InvalidIntegerException Thrown if timeInterval is less than 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        myMover.move(timeInterval);
    }
    
    /**
     * CargoShip's update algorithm.
     * If a CargoShip is damaged, it goes to the nearest SpacePort of the same
     * side for repairs.  When strength is back to maxStrength the CargoShip
     * leaves for a new random destination.
     */
    public void update() 
    {
        if (getStrength() < getMaxStrength())
        {
            String port = EntityManager.getInstance().getNearestSpacePort(myMover.getLocation());
            if (port == null)
                return;
            
            if (port.contains(getColorSt()))
            {
                Point3D p = EntityManager.getInstance().getLocationById(port);
                if (!atSpacePort())
                {
                    if (p != null)
                    {
                        if (myMover.getLocation().distance(p) <= 10.0)
                        {
                            atPort=true;
                            myMover.setLocation(p);
                            myMover.setDestination(p);
                        }
                        else
                            myMover.setDestination(EntityManager.getInstance().getLocationById(port));
                    }
                }
                else
                {
                    strength++;
                    if (p != null)
                    {
                        myMover.setLocation(p);
                    }
                }
                
            }
        }
        else
        {
            if (atSpacePort())
            {
                myMover.setDestination();
                atPort = false;
            }
        }
    }
    
    /**
     * Builds the CargoShip polygon.
     */
    private void buildShape() 
    {
        ArrayList<Point> sp = new ArrayList<Point>();
        sp.add(new Point(+0, -20));
        sp.add(new Point(+12, +16));
        sp.add(new Point(+0, +30));
        sp.add(new Point(-12, +16));
        
        polygon = new PolygonPlus();
        for (int i = 0; i < sp.size(); i++)
            polygon.addPoint(sp.get(i).x, sp.get(i).y);
    }
    
    /**
     * @return A ConsoleItem that is used to display the object to the screen.
     */
    public ConsoleItem makeConsoleItem()
    {
        return ConsoleItemFactory.createConsoleItem(getId(), getLocation(), 
                                                    getColor(), getAngle(), 
                                                    getPolygon(), getInfoText(), 
                                                    isDestroyed(), isDamaged());
    }
    
    /**
     * @return The speed of the CargoShip.
     */
    public double getSpeed() 
    {
        return myMover.getSpeed();
    }

    /**
     * @return The angle of the CargoShip.
     */
    public double getAngle() 
    {
        return myMover.getAngle();
    }
    
    /**
     * @return The Color of the CargoShip
     */
    public Color getColor() 
    {
        return myId.getColor();
    }

     /**
     * @return The Id of the CargoShip
     */
    public String getId() 
    {
        return myId.getId();
    }

     /**
     * @return The Location of the CargoShip
     */
    public Point3D getLocation() 
    {
        return myMover.getLocation();
    }
    
     /**
     * @return The Polygon of the CargoShip
     */
    public PolygonPlus getPolygon() 
    {
        PolygonPlus pp = new PolygonPlus(polygon);
        return pp;
    }

    /**
     * Sets the CargoShip's destination to a random Point3D.
     */
    public void setDestination()
    {
        myMover.setDestination();
    }
    
    /**
     * @return The InfoText of the CargoShip.
     */
    public String getInfoText() 
    {
        return infoText;
    }
    
    /**
     * Updates the CargoShip's InfoText as it may have changed.
     */
    public void updateInfoText()
    {
        infoText = "ID: " + myId.getId() + "\n" + 
                   "Location: " + EntityManager.getInstance().formatLocation(myMover.getLocation()) + "\n" + 
                   "Destination: " + EntityManager.getInstance().formatLocation(myMover.getDestination()) + "\n" +
                   "Speed: " + myMover.getSpeed() + "\n" +
                   "Angle: " + String.format("%.2f", myMover.getAngle()) + "\n" +
                   "Damaged: " + damaged + "\n" +
                   "Strength: " + String.format("%.2f", strength) + "\n" +
                   "Max Strength: " + maxStrength + "\n" +
                   "Debris Clouds: " + debrisClouds;
    }
    
    /**
     * @return True if CargoShip's Location == CargoShip's Destination, otherwise False.
     */
    public boolean atDestination()
    {
        return myMover.atDestination();
    }
    
    /**
     * @return True if the CargoShip's strength <= 0, otherwise false. 
     */
    public boolean isDestroyed() 
    {
        return destroyed;
    }
    
    /**
     * @return True if the CargoShip's strength != maxStrength, otherwise false.
     */
    public boolean isDamaged() 
    {
        return damaged;
    }
    
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getInfoText() + "\n");

        return sb.toString();
    }
    
    /**
     * Sets strength to the value passed into it.
     * @param st The value that strength will be set to
     * @throws InvalidDoubleException if st < 0.
     */
    private void setStrength(double st) throws InvalidDoubleException
    {
        if (st < 0.0)
            throw new InvalidDoubleException("Negative Strength Encountered: " + st);
        
        strength = st;
    }
    
   /**
     * Sets maxStrength to the value passed into it.
     * @param st The value that maxStrength will be set to
     * @throws InvalidDoubleException if st < 0.
     */
    private void setMaxStrength(double st) throws InvalidDoubleException
    {
        if (st < 0.0)
            throw new InvalidDoubleException("Negative Max Strength Encountered: " + st);
        
        maxStrength = st;
    }
    
    /**
     * Sets the debrisClouds to the value passed into it.
     * @param dc The value that debrisClouds will be set to
     * @throws InvalidIntegerException if dc < 0.
     */
    private void setDebrisClouds(int dc) throws InvalidIntegerException
    {
        if (dc < 0)
            throw new InvalidIntegerException("Negative Number of Debris Clouds Encountered: " +dc);
        
        debrisClouds = dc;
    }
    
    /**
     * @return True if CargoShip is docked at a SpacePort, otherwise False.
     */
    private boolean atSpacePort()
    {
        return atPort;
    }
    
    /**
     * @return The CargoShip's current strength. 
     */
    private double getStrength()
    {
        return strength;
    }
    
    /**
     * @return The CargoShip's maxStrength.
     */
    private double getMaxStrength()
    {
        return maxStrength;
    }

    /**
     * @return The type of Entity CargoShip is.  Should always return "Cargo Ship".
     */
    public String getEntityType() 
    {
        return shipType;
    }

    /**
     * @return The String representation of the CargoShip's color.
     */
    public String getColorSt() 
    {
        return myId.getColorSt();
    }

    /**
     * Creates a DebrisCloud to try and distract a missile that has locked onto CargoShip.
     * @param p The location of the missile that has locked onto CargoShip.
     */
    public void reactToRadarLock(Point3D p) 
    {
        if (debrisClouds > 0)
        {
            try {
                SpaceEntity dc = DebrisCloudFactory.build(1, 200, "GRAY", getLocation(), true);
                EntityManager.getInstance().addEntity(dc);
                setDebrisClouds(debrisClouds-1);
                
            } catch (InvalidDoubleException ex) {
                System.out.println(ex);
            } catch (InvalidIntegerException ex) {
                System.out.println(ex);
            } catch (ColorNotFoundException ex) {
                System.out.println(ex);
            } catch (NullObjectException ex) {
                System.out.println(ex);
            }
        }
    }
    
    /**
     * @return false. 
     */
    public boolean isPort()
    {
        return false;
    }

    /**
     * Applies damage to the CargoShip.  Damage is removed from the CargoShip's strength.
     * @param dam The amount of damage that is to be dealt to the CargoShip.
     */
    public void applyDamage(double dam)
    {
        double health = strength-dam;
        if (health <= 0)
        {
            destroyed = true;
        }
        else
        {
            try 
            {
                setStrength(health);
                damaged = true;
            } 
            catch (InvalidDoubleException ex) 
            {
                System.out.println(ex);
            }
        }
        
        if (destroyed == true)
        {
            try {
                DebrisCloud dc = DebrisCloudFactory.build(1, 200, "GRAY", getLocation(), true);
                EntityManager.getInstance().addEntity(dc);
            } catch (InvalidDoubleException ex) {
                System.out.println(ex);
            } catch (InvalidIntegerException ex) {
                System.out.println(ex);
            } catch (ColorNotFoundException ex) {
                System.out.println(ex);
            } catch (NullObjectException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * @return true.
     */
    public boolean isShip() {
        return true;
    }

    /**
     * @return false.
     */
    public boolean isTargetable() {
        return true;
    }
}
