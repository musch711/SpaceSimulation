package com.spacesimulation.domain;

import com.spacesimulation.display.ConsoleItem;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import com.spacesimulation.utils.Identifiable;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;
import com.spacesimulation.factories.IdentifiableImplFactory;
import com.spacesimulation.factories.MovableImplFactory;
import com.spacesimulation.factories.ConsoleItemFactory;
import com.spacesimulation.utils.Movable;

/**
 * A class that implements SpaceEntity and represents a DebrisCloud.
 * @author Steven Muschler
 */

public class DebrisCloud implements SpaceEntity 
{
    /**
     * DebrisClouds data members.
     */
    private static String shipType = "Defensive Debris Cloud";
    private PolygonPlus polygon;
    private String infoText;
    private double sizeFactor;
    private int durationTime;
    private boolean targetable;
    
    /**
     * DebrisCloud delegates movement activity to a mover and
     * identity activity to a identifier.
     */
    private Identifiable myId;
    private Movable myMover;
    
    /**
     * Constructor for DebrisCloud.
     * @param sFactor The size factor of the DebrisCloud.
     * @param dTime The duration time of the DebrisCloud.
     * @param color The Color of the DebrisCloud.
     * @param loc The Location of the DebrisCloud.
     * @param tar Whether the DebrisCloud should be targeted by GuidedMissiles.
     * @throws InvalidDoubleException if sFactor < 0.
     * @throws InvalidIntegerException if dTime < 0.
     * @throws ColorNotFoundException if the Color passed in does not exist.
     * @throws NullObjectException if the Color is null.
     */
    public DebrisCloud(double sFactor, int dTime, String color, Point3D loc, boolean tar) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException
    {
        buildShape();
        setSizeFactor(sFactor);
        polygon.scale(sFactor);
        setDurationTime(dTime);
        targetable = tar;
        
        myId = IdentifiableImplFactory.createIdentifiableImpl(color, shipType);

        myMover = MovableImplFactory.createMovable(shipType, loc, loc, 0.0, 0.0);
    }
    
    /**
     * Decreases the durationTime of the DebrisCloud by 1.
     */
    public void update() 
    {
        durationTime--;
    }

    private void buildShape() 
    {
        ArrayList<Point> sp = new ArrayList<Point>(); 
        sp.add(new Point(0, -13)); 
        sp.add(new Point(4, -15)); 
        sp.add(new Point(7, -14)); 
        sp.add(new Point(9, -9)); 
        sp.add(new Point(14, -7)); 
        sp.add(new Point(15, -4)); 
        sp.add(new Point(13, 0)); 
        sp.add(new Point(15, 4)); 
        sp.add(new Point(14, 7)); 
        sp.add(new Point(9, 9)); 
        sp.add(new Point(7, 14)); 
        sp.add(new Point(4, 15)); 
        sp.add(new Point(0, 13)); 
        sp.add(new Point(-4, 15)); 
        sp.add(new Point(-7, 14)); 
        sp.add(new Point(-9, 9)); 
        sp.add(new Point(-14, 7)); 
        sp.add(new Point(-15, 4)); 
        sp.add(new Point(-13, 0)); 
        sp.add(new Point(-15, -4)); 
        sp.add(new Point(-14, -7)); 
        sp.add(new Point(-9, -9)); 
        sp.add(new Point(-7, -14)); 
        sp.add(new Point(-4, -15)); 
         
        polygon = new PolygonPlus(); 
        for (int i = 0; i < sp.size(); i++) { 
            polygon.addPoint(sp.get(i).x, sp.get(i).y); 
        } 
    }

    /**
     * @return A ConsoleItem that is used to display the object to the screen.
     */
    public ConsoleItem makeConsoleItem()
    {
        return ConsoleItemFactory.createConsoleItem(getId(), myMover.getLocation(), 
                                                    getColor(), Math.toRadians(Math.random()*360.0), 
                                                    getPolygon(), getInfoText(), 
                                                    isDestroyed(), false);
    }

    /**
     * @return The Color of the DebrisCloud. 
     */
    public Color getColor() 
    {
        return myId.getColor();
    }

    /**
     * @return The String id of the DebrisCloud.
     */
    public String getId() 
    {
        return myId.getId();
    }

    /**
     * @return The Location of the DebrisCloud.
     */
    public Point3D getLocation() 
    {
        return myMover.getLocation();
    }

    /**
     * @return The Polygon of the DebrisCloud.
     */
    public PolygonPlus getPolygon() 
    {
        PolygonPlus pp = new PolygonPlus(polygon);
        return pp;
    }

    /**
     * @return The infoText of the DebrisCloud.
     */
    public String getInfoText() 
    {
        return infoText;
    }

    /**
     * Updates the infoText if any data has changed.
     */
    public void updateInfoText() 
    {
        infoText = "ID: " + myId.getId() + "\n" + 
            "Location: " + EntityManager.getInstance().formatLocation(myMover.getLocation()) + "\n" + 
            "Duration: " + durationTime;
    }

    /**
     * @return True if durationTime < 0, otherwise false.
     */
    public boolean isDestroyed() 
    {
        if (durationTime > 0)
            return false;
        else
            return true;
    }

    /**
     * The movement algorithm for the DebrisCloud.
     * @param timeInterval The amount of time for the DebrisCloud to move.
     * @throws InvalidIntegerException if timeInterval < 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        if (timeInterval < 0)
        {
            String message = "Invalid Integer Exception: " + timeInterval + " cannot be less than 0";
            throw new InvalidIntegerException(message);
        }
        else
        {
            myMover.move(timeInterval);
        }
    }

    /**
     * @return The angle of the DebrisCloud. 
     */
    public double getAngle() 
    {
        return myMover.getAngle();
    }

    /**
     * Sets the DebrisCloud's destination to a random Point3D.
     */
    public void setDestination() 
    {
        myMover.setDestination();
    }
    
    /**
     * @param p The new Location of the DebrisCloud.
     */
    public void setLocation(Point3D p)
    {
        myMover.setLocation(p);
    }

    /**
     * @return true.
     */
    public boolean atDestination() 
    {
        return true;
    }

    /**
     * @return false.
     */
    public boolean isDamaged() 
    {
        return false;
    }
    
    private void setSizeFactor(double sF) throws InvalidDoubleException
    {
        if (sF < 0.0)
            throw new InvalidDoubleException("Negative Size Factor Encountered: " + sF);
        
        sizeFactor = sF; 
    }
    
    private void setDurationTime(int dT) throws InvalidIntegerException
    {
        if (dT < 0)
            throw new InvalidIntegerException("Negative Duration Time Encountered: " + dT);
        
        durationTime = dT; 
    }
    
    /**
     * @return The type of the entity DebrisCloud is.  Should always return "Debris Cloud". 
     */
    public String getEntityType()
    {
        return shipType;
    }
    
    /**
     * @return The String representation of the DebrisCloud's color.
     */
    public String getColorSt() 
    {
        return myId.getColorSt();
    }

    /**
     * DebrisClouds do nothing when locked onto by a radar.
     * @param p The Location of the object targeting DebrisCloud.
     */
    public void reactToRadarLock(Point3D p) {
    }
    
    /**
     * @return false.
     */
    public boolean isPort()
    {
        return false;
    }

    /**
     * @param dam The amount of damage to be applied to DebrisCloud.
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
     * @return True if the DebrisCloud can be targeted, otherwise false..
     */
    public boolean isTargetable() {
        return targetable;
    }
    
}
