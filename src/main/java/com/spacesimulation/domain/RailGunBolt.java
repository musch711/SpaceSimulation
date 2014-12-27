package com.spacesimulation.domain;

import com.spacesimulation.display.ConsoleItem;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.factories.ConsoleItemFactory;
import com.spacesimulation.factories.IdentifiableImplFactory;
import com.spacesimulation.factories.MovableImplFactory;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import com.spacesimulation.utils.Identifiable;
import com.spacesimulation.utils.Movable;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;

/**
 * A class that implements SpaceEntity and represents a RailGunBolt.
 * @author Steven Muschler
 */

public class RailGunBolt implements SpaceEntity
{
    private static String shipType = "Rail Gun Bolt";
    private PolygonPlus polygon;
    private boolean damaged;
    private double strength;
    private double maxStrength;
    private double detRange;
    private double damage;
    private String infoText;
    
    private Identifiable myId;
    private Movable myMover;
    
    /**
     * RailGunBolt's Constructor
     * @param side The side/color that the RailGunBolt belongs to.
     * @param speed The speed of the RailGunBolt.
     * @param angle The angle of the RailGunBolt.
     * @param mStrength The maximum strength of the RailGunBolt.
     * @param detRange The detonation range of the RailGunBolt.
     * @param dam The damage the RailGunBolt does.
     * @param loc The location of the RailGunBolt.
     * @param des The destination of the RailGunBolt.
     * @throws InvalidDoubleException thrown if speed or mStrength or detRange or dam < 0.
     * @throws ColorNotFoundException thrown if side does not exist.
     * @throws NullObjectException thrown if side is null.
     */
    public RailGunBolt(String side, double speed, double angle, double mStrength, double detRange, double dam, Point3D loc, Point3D des) throws InvalidDoubleException, ColorNotFoundException, NullObjectException
    {
        myId = IdentifiableImplFactory.createIdentifiableImpl(side, shipType);
        myMover = MovableImplFactory.createMovable(shipType, loc, des, speed, angle);
        buildShape();
        damaged = false;
        setStrength(mStrength);
        setMaxStrength(mStrength);
        setDetRange(detRange);
        setDamage(dam);
        updateInfoText();
    }
    
    /**
     * RailGunBolt's movement algorithm.  Delegate to movableImpl.
     * @param timeInterval The amount of time the RailGunBolt is to travel.
     * @throws InvalidIntegerException thrown if timeInterval < 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        myMover.move(timeInterval);
        if (atDestination())
            EntityManager.getInstance().processDetonation(getId(), detRange, damage);
    }

    /**
     * RailGunBolt's update algorithm.  RailGunBolt exhibits no behavior in its update method.
     */
    public void update() 
    {
    }

    private void buildShape() 
    {
        ArrayList<Point> sp = new ArrayList<Point>();
        sp.add(new Point(2, -6));
        sp.add(new Point(2, 3));
        sp.add(new Point(-2, 3));
        sp.add(new Point(-2, -6));
        
        polygon = new PolygonPlus();
        for (int i = 0; i < sp.size(); i++) 
        {
            polygon.addPoint(sp.get(i).x, sp.get(i).y);
        }
    }

    /**
     * @return A ConsoleItem built from the RailGunBolt.
     */
    public ConsoleItem makeConsoleItem() 
    {
        return ConsoleItemFactory.createConsoleItem(getId(), getLocation(), 
                                                    getColor(), getAngle(), 
                                                    getPolygon(), getInfoText(), 
                                                    isDestroyed(), isDamaged());
    }

    /**
     * @return RailGunBolt's angle of travel.
     */
    public double getAngle() 
    {
        return myMover.getAngle();
    }

    /**
     * @return The side/color the RailGunBolt is on.
     */
    public Color getColor() 
    {
        return myId.getColor();
    }

    /**
     * @return The id of the RailGunBolt.
     */
    public String getId() 
    {
        return myId.getId();
    }

    /**
     * @return The location of the RailGunBolt.
     */
    public Point3D getLocation() 
    {
        return myMover.getLocation();
    }
    
    /**
     * @return The destination of the RailGunBolt. 
     */
    public Point3D getDestination()
    {
        return myMover.getDestination();  
    }

    /**
     * @return The Polygon of the RailGunBolt. 
     */
    public PolygonPlus getPolygon() 
    {
        PolygonPlus pp = new PolygonPlus(polygon);
        return pp;
    }

    /**
     * Sets the RailGunBolt's destination to a random Point3D.
     */
    public void setDestination() 
    {
        myMover.setDestination();
    }

    /**
     * @return RailGunBolt's infoText.
     */
    public String getInfoText() 
    {
        return infoText;
    }

    /**
     * Updates RailGunBolt's infoText as it may have changed.
     */
    public void updateInfoText() 
    {
        infoText = "ID: " + myId.getId() + "\n" + 
                   "Location: " + EntityManager.getInstance().formatLocation(getLocation()) + "\n" + 
                   "Destination: " + EntityManager.getInstance().formatLocation(getDestination()) + "\n" +
                   "Speed: " + myMover.getSpeed() + "\n" +
                   "Angle: " + String.format("%.2f", getAngle()) + "\n" +
                   "Damaged: " + damaged + "\n" +
                   "Strength: " + strength + "\n" +
                   "Max Strength: " + maxStrength + "\n" +
                   "Detonation Range: " + detRange + "\n" +
                   "Damage: " + damage;
    }

    /**
     * @return True if RailGunBolt is at its destination, otherwise false.
     */
    public boolean atDestination() 
    {
        return myMover.atDestination();
    }

    /**
     * @return True if RailGunBolt is at its destination, otherwise false.
     */
    public boolean isDestroyed() 
    {
        if (atDestination())
            return true;
        else
            return false;
    }

    /**
     * @return True if strength < maxStrength, otherwise false.
     */
    public boolean isDamaged() 
    {
        return damaged;
    }
    
    private void setStrength(double st) throws InvalidDoubleException
    {
        if (st < 0)
            throw new InvalidDoubleException("Negative Strength Encountered: " + st);
        
        strength = st;
    }
    
    private void setMaxStrength(double st) throws InvalidDoubleException
    {
        if (st < 0)
            throw new InvalidDoubleException("Negative Max Strength Encountered: " + st);
        
        maxStrength = st;
    }
    
    private void setDetRange(double dr) throws InvalidDoubleException
    {
        if (dr < 0)
            throw new InvalidDoubleException("Negative Detonation Range Encountered: " + dr);
        
        detRange = dr;
    }
        
    private void setDamage(double dam) throws InvalidDoubleException
    {
        if (dam < 0)
            throw new InvalidDoubleException("Negative Damage Encountered: " + dam);
        
        damage = dam;
    }
    
    /**
     * @return The type of SpaceEntity RailGunBolt is.  Should always return "Rail Gun Bolt".
     */
    public String getEntityType()
    {
        return shipType;
    }
    
    /**
     * @return the String representation of the RailGunBolt's color/side.
     */
    public String getColorSt() 
    {
        return myId.getColorSt();
    }

    /**
     * RailGunBolt's do not have any behavior when they are targeted.
     * @param p The location of the object that targeted RailGunBolt.
     */
    public void reactToRadarLock(Point3D p) 
    {
    }
    
    /**
     * @return false.
     */
    public boolean isPort()
    {
        return false;
    }

    /**
     * Applies damage to the RailGunBolt's strength.
     * @param dam The amount of damage the RailGunBolt is dealt.
     */
    public void applyDamage(double dam) 
    {
    }

    /**
     * @return false.
     */
    public boolean isShip() {
        return false;
    }

    /**
     * @return false.
     */
    public boolean isTargetable() {
        return false;
    }
}
