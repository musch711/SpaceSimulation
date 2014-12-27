package com.spacesimulation.domain;

import com.spacesimulation.display.ConsoleItem;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import com.spacesimulation.factories.ConsoleItemFactory;
import com.spacesimulation.factories.DebrisCloudFactory;
import com.spacesimulation.factories.GuidedMissileFactory;
import com.spacesimulation.factories.IdentifiableImplFactory;
import com.spacesimulation.factories.MovableImplFactory;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import com.spacesimulation.utils.Identifiable;
import com.spacesimulation.utils.Movable;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;

/**
 * A class that implements SpaceEntity and represents a FighterShip.
 * @author Steven Muschler
 */

public class FighterShip implements SpaceEntity
{
    /**
     * FighterShip's data members.
     */
    private static String shipType = "Fighter Ship";
    private PolygonPlus polygon;
    private boolean destroyed;
    private boolean damaged;
    private double strength;
    private double maxStrength;
    private int missiles;
    private String infoText;
    
    private Identifiable myId;
    private Movable myMover;
    
    /**
     * targeted stores the String Ids of the SpaceEntities that the FighterShip has 
     * recently fire at.  This is to make sure that FighterShip do not fire dozens of
     * GuidedMissiles at a target in a very short period of time.
     */
    private HashMap<String, Integer> targeted = new HashMap<String, Integer>();
    
    /**
     * FigherShip's Constructor
     * @param color The side that the FighterShip is on.
     * @param ang The angle of the FighterShip's travel.
     * @param sp The FighterShip's speed.
     * @param mStrength The maximum strength of the FighterShip.
     * @param missiles The number of missiles the FighterShip currently has.
     * @throws InvalidDoubleException if sp or mStrength < 0.
     * @throws InvalidIntegerException if missiles < 0.
     * @throws ColorNotFoundException if the color String passed in does not exist.
     * @throws NullObjectException if color is null.
     */
    public FighterShip(String color, double ang, double sp, double mStrength, int missiles) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException
    {
        buildShape();
        destroyed = false;
        damaged = false;
        setStrength(mStrength);
        setMaxStrength(mStrength);
        setMissiles(missiles);
        
        myMover = MovableImplFactory.createMovable(shipType, sp, ang);

        myId = IdentifiableImplFactory.createIdentifiableImpl(color, shipType);
        
        updateInfoText();
        
    }

    /**
     * The FighterShip's movement algorithm.
     * @param timeInterval The amount of time for the FighterShip to travel.
     * @throws InvalidIntegerException if timeInterval < 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException 
    {
        myMover.move(timeInterval);
    }

    /**
     * Finds a target that is not in the targeted HashMap
     * and fires a GuidedMissile at them.  Then adds that
     * target to the targeted HashMap.
     */
    public void update() 
    {
        if (strength <= 0)
            destroyed = true;
        
        synchronized(targeted)
        {
        //Update HashMap that stores who has been fired at
        Set ids = targeted.keySet();
        Iterator<String> idItr = ids.iterator();
        synchronized(ids)
        {
            while (idItr.hasNext())
            {
                String temp = idItr.next();
                if (targeted.get(temp) == 0)
                {
                    idItr.remove();
                }
                else
                {
                    int time = targeted.get(temp) - 1;
                    targeted.put(temp, time);
                }
            }
        }
        
        //Fire missiles at targets that are close and have not be targeted recently
        if (missiles > 0)
        {
            ArrayList<String> targets = EntityManager.getInstance().performRadarSweep(getLocation(), getColor());
            Iterator<String> targetItr = targets.iterator();
            while(targetItr.hasNext())
            {
                String id = targetItr.next();
            
                if (!targeted.containsKey(id))
                {
                    EntityManager.getInstance().radarLock(id, getLocation());
                        try 
                        {
                            GuidedMissile gM = GuidedMissileFactory.build(myId.getColorSt(), myMover.getLocation(), EntityManager.getInstance().getLocationById(id), id, 5.5, getAngle(), 1.0, 25.0, 1200.0, 250);                 
                            EntityManager.getInstance().addEntity(gM);
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
                    missiles = missiles-1;
                    targeted.put(id, 250);
                }
            }
        }
        }
    }

    /**
     * Builds the Polygon of the FighterShip.
     */
    public void buildShape() 
    { 
        ArrayList<Point> sp = new ArrayList<Point>(); 
        sp.add(new Point(+0, -16)); 
        sp.add(new Point(+4, -8)); 
        sp.add(new Point(+10, -12)); 
        sp.add(new Point(+16, +8)); 
        sp.add(new Point(+8, +0)); 
        sp.add(new Point(+6, +2)); 
        sp.add(new Point(+0, +20)); 
        sp.add(new Point(-6, +2)); 
        sp.add(new Point(-8, +0)); 
        sp.add(new Point(-16, +8)); 
        sp.add(new Point(-10, -12)); 
        sp.add(new Point(-4, -8)); 
 
        polygon = new PolygonPlus(); 
        for (int i = 0; i < sp.size(); i++) { 
            polygon.addPoint(sp.get(i).x, sp.get(i).y); 
        } 
    } 

    /**
     * @return A ConsoleItem that was created from the FighterShip.
     */
    public ConsoleItem makeConsoleItem() 
    {
        return ConsoleItemFactory.createConsoleItem(getId(), getLocation(), 
                                                    getColor(), getAngle(), 
                                                    getPolygon(), getInfoText(), 
                                                    isDestroyed(), isDamaged());
    }

    /**
     * @return The FighterShip's angle.
     */
    public double getAngle() 
    {
        return myMover.getAngle();
    }

    /**
     * @return The FighterShip's Color. 
     */
    public Color getColor() 
    {
        return myId.getColor();
    }

    /**
     * @return The String representation of the FighterShip's color.
     */
    public String getColorSt() 
    {
        return myId.getColorSt();
    }

    /**
     * @return The Id of the FighterShip.
     */
    public String getId() 
    {
        return myId.getId();
    }

    /**
     * @return The Location of the FighterShip.
     */
    public Point3D getLocation() 
    {
        return myMover.getLocation();
    }

    /**
     * @return The Polygon of the FighterShip.
     */
    public PolygonPlus getPolygon() 
    {
        PolygonPlus pp = new PolygonPlus(polygon);
        return pp;
    }

    /**
     * Sets the FighterShip's destination to a random point.
     */
    public void setDestination() 
    {
        myMover.setDestination();
    }

    /**
     * @return The FighterShip's infoText.
     */
    public String getInfoText() 
    {
        return infoText;
    }

    /**
     * Updates the FighterShip's infoText as it may have changed.
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
                   "Missiles: " + missiles;
    }

    /**
     * @return True if FighterShip's Location == FighterShip's Destination, otherwise false.
     */
    public boolean atDestination() 
    {
        return myMover.atDestination();
    }

    /**
     * @return True if the FighterShip's strength <= 0, otherwise false.
     */
    public boolean isDestroyed() 
    {
        return destroyed;
    }

    /**
     * @return True if the FighterShip's strength < maxStrength, otherwise false. 
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
     * @return String with the FighterShip's type.  Should always be "Fighter Ship".
     */
    public String getEntityType() 
    {
        return shipType;
    }

    /**
     * FighterShips do not reactToRadarLock.
     * @param p The Location of the object that targeted FighterShip.
     */
    public void reactToRadarLock(Point3D p) 
    {
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
    
    private void setMissiles(int m) throws InvalidIntegerException
    {
        if (m < 0)
            throw new InvalidIntegerException("Negative Number of Debris Clouds Encountered: " +m);
        
        missiles = m;
    }

    /**
     * Removes damage from the FighterShip's strength.
     * @param dam The amount of damage to be removed.
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
     * @return True.
     */
    public boolean isShip() {
        return true;
    }

    /**
     * @return False
     */
    public boolean isTargetable() {
        return true;
    }

    
}
