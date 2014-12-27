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
 * A class that implements SpaceEntity and represents a GuidedMissile.
 * @author Steven Muschler
 */

public class GuidedMissile implements SpaceEntity 
{
    private static String shipType = "Guided Missile";
    private PolygonPlus polygon;
    private boolean damaged;
    private boolean destroyed;
    private String target;
    private double strength;
    private double maxStrength;
    private double detRange;
    private double damage;
    private int duration;
    private String infoText;
    
    private Identifiable myId;
    private Movable myMover;
    
    /**
     * GuidedMissile's Constructor.
     * @param side The side of the SpaceEntity that fired the GuidedMissile.
     * @param loc The location of the GuidedMissile.
     * @param des The destination of the GuidedMissile.
     * @param tarId The id of the SpaceEntity the GuidedMissile is targeting.
     * @param speed The speed of the GuidedMissile.
     * @param angle The angle of the GuidedMissile.
     * @param maxSt The maximum strength of the GuidedMissile.
     * @param detRange The detonation range of the GuidedMissile's explosion.
     * @param dam The damage the GuidedMissile deals to other SpaceEntities.
     * @param dur The duration of the GuidedMissile before it detonates.
     * @throws InvalidDoubleException if speed or maxSt or detRange or dam < 0.
     * @throws ColorNotFoundException if side does not exist.
     * @throws NullObjectException if side is null.
     * @throws InvalidIntegerException if duration < 0.
     */
    public GuidedMissile(String side, Point3D loc, Point3D des, String tarId, double speed, double angle, double maxSt, double detRange, double dam, int dur) throws InvalidDoubleException, ColorNotFoundException, NullObjectException, InvalidIntegerException
    {
        
        buildShape();
        
        myId = IdentifiableImplFactory.createIdentifiableImpl(side, shipType);
        myMover = MovableImplFactory.createMovable(shipType, loc, des, speed, angle);
        
        target = tarId;
        damaged = false;
        destroyed = false;
        setStrength(maxSt);
        setMaxStrength(maxSt);
        setDetRange(detRange);
        setDamage(dam);
        setDuration(dur);
        
        updateInfoText();    
    }

    /**
     * The movement algorithm of the GuidedMissile.
     * @param timeInterval The amount of time for the GuidedMissile to travel.
     * @throws InvalidIntegerException if timeInterval < 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        if (atDestination())
            destroyed = true;
        
        if (duration <= 0 || strength <= 0)
        {
            detonate();
            destroyed = true;
        }
        else
        {
            setDuration(duration-1);
        }
        
        Point3D des = EntityManager.getInstance().getLocationById(target);
        
        if (des == null)
        {
            detonate();
            destroyed = true;
        }
        else
            myMover.setDestination(des);
        
        myMover.move(timeInterval);
        
        if (destroyed == true)
            EntityManager.getInstance().processDetonation(myId.getId(), detRange, damage);
        
    }

    /**
     * The GuidedMissile's update algorithm.  
     * The GuidedMissile asks EntityManager to call reaquireRadarLock.
     * @see com.spacesimulation.domain.EntityManager
     */
    public void update() 
    {
        target = EntityManager.getInstance().reaquireRadarLock(getLocation(), target, myId.getColor());
        if (target == null)
        {
            detonate();
            destroyed = true;
        }
    }
    
    private void buildShape()
    { 
        ArrayList<Point> sp = new ArrayList<Point>(); 
        sp.add(new Point(0, -10)); 
        sp.add(new Point(2, -6)); 
        sp.add(new Point(2, 3)); 
        sp.add(new Point(4, 9)); 
        sp.add(new Point(2, 9)); 
        sp.add(new Point(2, 7)); 
        sp.add(new Point(0, 8)); 
        sp.add(new Point(-2, 7)); 
        sp.add(new Point(-2, 9)); 
        sp.add(new Point(-4, 9)); 
        sp.add(new Point(-2, 3)); 
        sp.add(new Point(-2, -6)); 
     
        polygon = new PolygonPlus(); 
        for (int i = 0; i < sp.size(); i++) { 
            polygon.addPoint(sp.get(i).x, sp.get(i).y); 
    }
        
    }

    /**
     * @return A ConsoleItem made from the GuidedMissile.
     */
    public ConsoleItem makeConsoleItem() 
    {
    return ConsoleItemFactory.createConsoleItem(getId(), getLocation(), 
                                                getColor(), getAngle(), 
                                                getPolygon(), getInfoText(), 
                                                isDestroyed(), isDamaged());
    }

    /**
     * @return The GuidedMissile's angle of travel.
     */
    public double getAngle() 
    {
        return myMover.getAngle();
    }

    /**
     * @return The Color/Side of the GuidedMissile. 
     */
    public Color getColor() 
    {
        return myId.getColor();
    }

    /**
     * @return The String representation of the GuidedMissile's color.
     */
    public String getColorSt() 
    {
        return myId.getColorSt();
    }

    /**
     * @return The GuidedMissile's id.
     */
    public String getId() 
    {
        return myId.getId();
    }

    /**
     * @return The Location of the GuidedMissile.
     */
    public Point3D getLocation() 
    {
        return myMover.getLocation();
    }

    /**
     * @return The Polygon of the GuidedMissile.
     */
    public PolygonPlus getPolygon() 
    {
        PolygonPlus pp = new PolygonPlus(polygon);
        return pp;
    }

    /**
     * Sets the GuidedMissile's destination to a random Point3D.
     */
    public void setDestination() 
    {
        myMover.setDestination();
    }

    /**
     * @return The GuidedMissile's infoText.
     */
    public String getInfoText() 
    {
        return infoText;
    }

    /**
     * Updates the GuidedMissile's infoText as it may have changed.
     */
    public void updateInfoText() 
    {
        infoText = "ID: " + myId.getId() + "\n" + 
                   "Location: " + EntityManager.getInstance().formatLocation(myMover.getLocation()) + "\n" + 
                   "Destination: " + EntityManager.getInstance().formatLocation(myMover.getDestination()) + "\n" +
                   "Speed: " + myMover.getSpeed() + "\n" +
                   "Angle: " + String.format("%.2f", getAngle()) + "\n" +
                   "Damaged: " + damaged + "\n" +
                   "Strength: " + strength + "\n" +
                   "Max Strength: " + maxStrength + "\n" +
                   "Detonation Range: " + detRange + "\n" +
                   "Damage: " + damage + "\n" +
                   "Duration: " + duration;
    }

    /**
     * @return True if GuidedMissile has reached its destination, otherwise false.
     */
    public boolean atDestination() 
    {
        return myMover.atDestination();
    }

    /**
     * @return True if the GuidedMissile is destroyed, otherwise false.
     */
    public boolean isDestroyed() 
    {
        return destroyed;
    }

    /**
     * @return True if the GuidedMissile is damaged, otherwise false.
     */
    public boolean isDamaged() 
    {
        return damaged;
    }

    /**
     * @return false.
     */
    public boolean isPort() 
    {
        return false;
    }

    /**
     * @return The type of SpaceEntity GuidedMissile is.  Should always be "GuidedMissile".
     */
    public String getEntityType() 
    {
        return shipType;
    }

    /**
     * GuidedMissile does not do anything when a radar locks onto it.
     * @param p The location of the object that is targeting GuidedMissile.
     */
    public void reactToRadarLock(Point3D p) {
    }

    /**
     * Reduces the GuidedMissile's strength.
     * @param dam The amount of damage dealt.
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
    
    private void setDuration(int dur) throws InvalidIntegerException
    {
        if (dur < 0)
            throw new InvalidIntegerException("Negative Duration Encountered: " + dur);
        
        duration = dur;
    }
    
    /**
     * GuidedMissile delegates it detonate behavior to its MovableImpl.
     */
    private void detonate()
    {
        myMover.detonate();
    }

    /**
     * @return false.
     */
    public boolean isShip() {
        return false;
    }

    /**
     * @return true.
     */
    public boolean isTargetable() {
        return true;
    }
}
