package com.spacesimulation.domain;

import com.spacesimulation.display.ConsoleItem;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.factories.ConsoleItemFactory;
import com.spacesimulation.factories.DebrisCloudFactory;
import com.spacesimulation.factories.IdentifiableImplFactory;
import com.spacesimulation.factories.MovableImplFactory;
import com.spacesimulation.factories.RailGunBoltFactory;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import com.spacesimulation.utils.Identifiable;
import com.spacesimulation.utils.Movable;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;

/**
 * A class that implements SpaceEntity and represents a SpacePort.
 * @author Steven Muschler
 */

public class SpacePort implements SpaceEntity
{
    private static String shipType = "Space Port";
    private PolygonPlus polygon;
    private boolean destroyed;
    private boolean damaged;
    private double strength;
    private double maxStrength;
    private String infoText;
    private int numBolts;
    
    /**
     * dockedShips stores any SpaceEntities that are currently at the SpacePort for repairs.
     */
    private ArrayList<SpaceEntity> dockedShips = new ArrayList<SpaceEntity>();
    
    private Movable myMover;
    private Identifiable myId;
    
    /**
     * SpacePort's Constructor
     * @param color The side that the SpacePort is on.
     * @param ang The angle of the SpacePort's travel.
     * @param sp The SpacePort's speed.
     * @param des If the SpacePort is destroyed.
     * @param dam If the SpacePort is damaged.
     * @param mStrength The maximum strength of the SpacePort.
     * @throws InvalidDoubleException if sp or mStrength < 0.
     * @throws ColorNotFoundException if color does not exist.
     * @throws NullObjectException if color is null.
     */
    public SpacePort(String color, double ang, double sp, boolean des, boolean dam, double mStrength) throws InvalidDoubleException, ColorNotFoundException, NullObjectException
    {
        buildShape();
        destroyed = des;
        damaged = dam;
        numBolts = 100;
        setStrength(mStrength);
        setMaxStrength(mStrength);
        
        myMover = MovableImplFactory.createMovable(shipType, sp, ang);
        myId = IdentifiableImplFactory.createIdentifiableImpl(color, shipType);
    }
    
    /**
     * SpacePort's Movement Algorithm.  Delegated to a movableImpl.
     * @param timeInterval The amount of time the SpacePort travels.
     * @throws InvalidIntegerException if timeInterval < 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        myMover.move(timeInterval);
    }

    /**
     * Checks if the SpacePort has any ships docked at it for repairs.
     */
    public void update() 
    {
        dockedShips = EntityManager.getInstance().findDockedShips(myMover.getLocation(), myId.getColor());
    }

    private void buildShape() 
    {
        ArrayList<Point> sp = new ArrayList<Point>();
        sp.add(new Point(0, -20));
        sp.add(new Point(6, -6));
        sp.add(new Point(20, 0));
        sp.add(new Point(6, 6));
        sp.add(new Point(0, 20));
        sp.add(new Point(-6, 6));
        sp.add(new Point(-20, 0));
        sp.add(new Point(-6, -6));
        
        polygon = new PolygonPlus();
        for (int i = 0; i < sp.size(); i++) 
        {
            polygon.addPoint(sp.get(i).x, sp.get(i).y);
        }
    }

    /**
     * @return ConsoleItem built from the SpacePort.
     */
    public ConsoleItem makeConsoleItem() 
    {
        return ConsoleItemFactory.createConsoleItem(getId(), getLocation(),
                                                    getColor(), getAngle(), 
                                                    getPolygon(), getInfoText(), 
                                                    isDestroyed(), isDamaged());
    }

    /**
     * @return 0.0. 
     */
    public double getAngle() {
        return 0.0;
    }

    /**
     * @return The color/side the SpacePort is on. 
     */
    public Color getColor() {
        return myId.getColor();
    }

    /**
     * @return The String id of the SpacePort.
     */
    public String getId() {
        return myId.getId();
    }

    /**
     * @return The location of the SpacePort.
     */
    public Point3D getLocation() 
    {
        return myMover.getLocation();
    }

    /**
     * @return The Polygon of the SpacePort. 
     */
    public PolygonPlus getPolygon() 
    {
        PolygonPlus pp = new PolygonPlus(polygon);
        return pp;
    }

    /**
     * Sets the SpacePort's destination to a new random point.
     */
    public void setDestination() 
    {
        myMover.setDestination();
    }

    /**
     * @return The SpacePort's infoText.
     */
    public String getInfoText() 
    {
        return infoText;
    }

    /**
     * Updates the SpacePort's infoText as it may have changed.
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
                   "Max Strength: " + maxStrength;
    }

    /**
     * @return True if the SpacePort has reached its destination, otherwise false.
     */
    public boolean atDestination() 
    {
        return myMover.atDestination();
    }

    /**
     * @return True if the SpacePort is destroyed, otherwise false.
     */
    public boolean isDestroyed() 
    {
        return destroyed;
    }

    /**
     * @return True if the SpacePort's strength < maxStrength, otherwise false.
     */
    public boolean isDamaged() 
    {
        return damaged;
    }
    
    private void setStrength(double st) throws InvalidDoubleException
    {
        if (st < 0.0)
            throw new InvalidDoubleException("Negative Strength Encountered: " + st);
        
        strength = st;
    }
    
    private void setMaxStrength(double st) throws InvalidDoubleException
    {
        if (st < 0.0)
            throw new InvalidDoubleException("Negative Max Strength Encountered: " + st);
        
        maxStrength = st;
    }
    
    /**
     * @return The SpacePort's EntityType.  Should always be "Space Port".
     */
    public String getEntityType()
    {
        return shipType;
    }
    
    /**
     * @return The String representation of the SpacePort's color/side. 
     */
    public String getColorSt() 
    {
        return myId.getColorSt();
    }

    /**
     * SpacePort fires ten (if it has ten remaining) RailGunBolts at the source of the radar lock.
     * @param p The location of the object that targeted SpacePort.
     */
    public void reactToRadarLock(Point3D p) 
    {
        int i;
        if (numBolts <= 0)
            i = 10;
        else if (numBolts < 10)
            i = 10 - numBolts;
        else
            i = 0;
        
        for (; i < 10; i++)
        {
            double x = p.getX()*(0.95+(Math.random()*0.1));
            double y = p.getY()*(0.95+(Math.random()*0.1));
            double z = p.getZ()*(0.95+(Math.random()*0.1));
            Point3D des = new Point3D(x,y,z);
            try {
                SpaceEntity sp = RailGunBoltFactory.build(getColorSt(), 30.0, 0.0, 1.0, 10.0, 200.0, getLocation(), des);
                EntityManager.getInstance().addEntity(sp);
            } catch (InvalidDoubleException ex) {
                System.out.println(ex);
            } catch (ColorNotFoundException ex) {
                System.out.println(ex);
            } catch (NullObjectException ex) {
                System.out.println(ex);
            }
            numBolts--;
        }
    }
    
    /**
     * @return true.
     */
    public boolean isPort()
    {
        return true;
    }

    /**
     * Applies damage to the SpacePort's strength.
     * @param dam The amount of damage dealt to the SpacePort.
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
                DebrisCloud dc = DebrisCloudFactory.build(2, 200, "YELLOW", getLocation(), true);
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
            
            Iterator<SpaceEntity> itr = dockedShips.iterator();
            while(itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                sp.applyDamage(Math.random()*1500.0);
            }
        }
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
