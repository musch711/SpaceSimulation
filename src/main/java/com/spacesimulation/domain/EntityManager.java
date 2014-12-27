package com.spacesimulation.domain;

import com.spacesimulation.display.ViewManager;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
import java.util.ArrayList;
import java.util.Iterator;
import com.spacesimulation.factories.CargoShipFactory;
import com.spacesimulation.factories.FighterShipFactory;
import com.spacesimulation.factories.SpacePortFactory;
import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import com.spacesimulation.utils.Point3D;

/**
 * A class that keeps track of the SpaceEntities and contains the game's loop.
 */

public class EntityManager {

    private volatile static EntityManager instance;

    private List<SpaceEntity> spaceItems = new CopyOnWriteArrayList<SpaceEntity>();
   
    private Map<String, Integer> numItems = Collections.synchronizedMap(new HashMap<String, Integer>());
    
    private UpdateLoop updateLoop;

    public static EntityManager getInstance() {
        if (instance == null) {
            synchronized (EntityManager.class) {
                if (instance == null) // Double-Check!
                {
                    instance = new EntityManager();
                }
            }
        }
        return instance;
    }

    private EntityManager() {
    }

    public int getXSize() {
        return ViewManager.getXSize();
    }

    public int getYSize() {
        return ViewManager.getYSize();
    }

    public int getZSize() {
        return ViewManager.getZSize();
    }

    public void start() {
        updateLoop = new UpdateLoop();
        Thread updateThread = new Thread(updateLoop);
        updateThread.start(); // start processing.
    }

    public void stop() {
        updateLoop.setRunning(false);
    }

    public void pause() {
        ViewManager.getInstance().pause();
    }

    public boolean isPaused() {
        return ViewManager.getInstance().isPaused();
    }

    public void resume() {
        ViewManager.getInstance().resume();
    }
    
    public void addEntity(SpaceEntity sp)
    {
        spaceItems.add(sp);
    }
    
    public void removeEntity(SpaceEntity sp)
    {
        spaceItems.remove(sp);
    }

    public String summary() 
    {
        String summary = "";
        Set<String> setOfId = numItems.keySet();
        for (String identifier : setOfId)
        {
            int cur = numItems.get(identifier);
            summary += identifier + ": " + cur + " ";
        }
        return summary;
    }
    
    public void initialize(String side, int numCargoShips, int numCloudsPerShip, double cargoShipSpeed, 
                                        int numSpacePorts, int numRailGunBolts, double spacePortSpeed,
                                        int numFighterShips, int missiles, double fighterShipSpeed)
    {
        String cs = side + " Ships";
        String sc = side + " Ports";
        if (!numItems.containsKey(cs))
        {
            numItems.put(cs, numCargoShips+numFighterShips);
        }
        else
        {
            int cur = numItems.get(cs);
            numItems.put(cs, cur+numCargoShips+numFighterShips);
        }
        
        if (!numItems.containsKey(sc))
        {
            numItems.put(sc, numSpacePorts);
        }
        else
        {
            int cur = numItems.get(sc);
            numItems.put(sc, cur+numSpacePorts);
        }
        
        for (int i = 0; i < numCargoShips; i++)
        {
            SpaceEntity sp;
            try 
            {
                sp = CargoShipFactory.build(side, 4, cargoShipSpeed, 1000.0, numCloudsPerShip);
                spaceItems.add(sp);
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
         
        
        SpaceEntity fighterShip;
        for (int i = 0; i < numFighterShips; i++)
        {
            try {
                fighterShip = FighterShipFactory.build(side, 0.0, fighterShipSpeed, 1000.0, missiles);
                spaceItems.add(fighterShip);
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
        
        SpaceEntity spacePort;
        for (int i = 0; i < numSpacePorts; i++)
        {
            
            try 
            {
                spacePort = SpacePortFactory.build(side, 0.0, spacePortSpeed, false, false, 2500.0);
                spaceItems.add(spacePort);
            } 
            catch (InvalidDoubleException ex) 
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

    private class UpdateLoop implements Runnable {

        private boolean isRunning = true;
        private long cycleTime;
        private static final int UPDATE_DELAY = 20; // 500 ms

        public void setRunning(boolean value) {
            isRunning = value;
        }

        public void run() {
            cycleTime = System.currentTimeMillis();

            while (isRunning) {

                if (!ViewManager.getInstance().isPaused()) {
                    updateContent();
                }

                synchUpdateRate();

                if (ViewManager.getInstance().isStopped()) {
                    isRunning = false;
                }
            }

        }

        private void updateContent() 
        {
                Iterator<SpaceEntity> itr = spaceItems.iterator();

                if (!spaceItems.isEmpty())
                {
                    while(itr.hasNext())
                    {
                        SpaceEntity sp = itr.next();
                        if (sp.isDestroyed())
                        {
                            if (sp.isShip())
                            {
                                String s = sp.getColorSt() + " Ships";
                                int cur = numItems.get(s);
                                numItems.put(s, cur-1);
                            }
                            else if (sp.isPort())
                            {
                                String s = sp.getColorSt() + " Ports";
                                int cur = numItems.get(s);
                                numItems.put(s, cur-1);
                            }
                        
                            removeEntity(sp);
                            ViewManager.getInstance().updateItem(sp.makeConsoleItem());
                            //Perform destroyed behavior
                        }
                        else
                        {
                            try
                            {
                                sp.move(1);
                                ViewManager.getInstance().updateItem(sp.makeConsoleItem());
                            }
                            catch(InvalidIntegerException e)
                            {
                                System.out.println(e);
                            }
                        
                            if(sp.atDestination())
                            {
                                sp.setDestination();
                            }
                            sp.update();
                            sp.updateInfoText();
                        }
                        //update status info on the view frame
                    }
                }

                ViewManager.getInstance().updateInfo(summary());
        }

        private void synchUpdateRate() {

            cycleTime = cycleTime + UPDATE_DELAY;
            long difference = cycleTime - System.currentTimeMillis();
            try {
                Thread.sleep(Math.max(0, difference));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Point3D getLocationById(String id)
    {
            Iterator<SpaceEntity> itr = spaceItems.listIterator();
            while (itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                if (sp.getId().equalsIgnoreCase(id))
                    return sp.getLocation();
            }
            return null;     
    }
    
    public String getNearestSpacePort(Point3D p)
    {
            String result = null;
            double curSmalDis = Double.MAX_VALUE;
            Iterator<SpaceEntity> itr = spaceItems.listIterator();
            while (itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                if (sp.isPort())
                {
                    if (sp.getLocation().distance(p) < curSmalDis)
                    {
                        curSmalDis = sp.getLocation().distance(p);
                        result = sp.getId();
                    }
                }
            }
        
            return result; 
    }
    
    public Point3D createRandomPoint()
    {
        double x = Math.random()*EntityManager.getInstance().getXSize();
        double y = Math.random()*EntityManager.getInstance().getYSize();
        double z = Math.random()*EntityManager.getInstance().getZSize();
        return new Point3D(x,y,z);
    }
    
    public String formatLocation(Point3D point) 
    {
        String result = "[" + String.format("%.2f", point.getX()) + ", " 
                            + String.format("%.2f", point.getY()) + ", " 
                            + String.format("%.2f", point.getZ()) + "]";
        return result;
    }
    
    public ArrayList<String> performRadarSweep(Point3D loc, Color side)
    {     
            ArrayList<String> targets = new ArrayList<String>();
            Iterator<SpaceEntity> itr = spaceItems.iterator();
            while(itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                if (sp.isTargetable() && !sp.getEntityType().equalsIgnoreCase("Guided Missile") && !sp.getEntityType().equalsIgnoreCase("Defensive Debris Cloud"))
                {
                    double dist = sp.getLocation().distance(loc);
                    if (dist <= 150.0 && dist != 0.0)
                    {
                        double rand = Math.random();               
                        if (rand > 0.99 && sp.getColor().equals(side))
                        {
                            targets.add(sp.getId());
                        }
                        
                        if (sp.getColor().equals(side) == false)
                        {
                            targets.add(sp.getId());
                        }
                    }
                }
            }
            return targets;
    }
    
        public ArrayList<SpaceEntity> findDockedShips(Point3D loc, Color side)
    {     
            ArrayList<SpaceEntity> targets = new ArrayList<SpaceEntity>();
            Iterator<SpaceEntity> itr = spaceItems.iterator();
            while(itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                if (sp.getEntityType().equalsIgnoreCase("Cargo Ship"))
                {
                    double dist = sp.getLocation().distance(loc);
                    if (dist <= 5.0 && sp.getColor().equals(side))
                    {
                        targets.add(sp);
                    }
                }
            }
            return targets;
    }
    
    public  void radarLock(String id, Point3D loc)
    {
            Iterator<SpaceEntity> itr = spaceItems.iterator();
            while(itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                if (sp.getId().equalsIgnoreCase(id))
                    sp.reactToRadarLock(loc);
            }
        
    }
    
    public void processDetonation(String id, double detRange, double damage)
    {
        Point3D loc = getLocationById(id);
        if (loc != null)
        {
            Iterator<SpaceEntity> itr = spaceItems.iterator();
            while(itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                double dis = sp.getLocation().distance(loc);
                if (dis <= detRange)
                {
                    double d = Math.random()*damage;
                    sp.applyDamage(d);
                }
            }
        }
    }
    
    public String reaquireRadarLock(Point3D loc, String id, Color side)
    {
        double rand = Math.random();
        if (rand <= 0.99)
            return id;
        else
        {
            ArrayList<String> targets = performRadarSweep(loc, side);
            Iterator<SpaceEntity> itr = spaceItems.iterator();
            while(itr.hasNext())
            {
                SpaceEntity sp = itr.next();
                if (sp.isTargetable() && !sp.getEntityType().equalsIgnoreCase("Guided Missile"))
                {
                    double dist = sp.getLocation().distance(loc);
                    if (dist <= 150.0 && dist != 0.0)
                    {
                        double random = Math.random();               
                        if (random > 0.99 && sp.getColor().equals(side))
                        {
                            targets.add(sp.getId());
                        }
                        
                        if (sp.getColor().equals(side) == false)
                        {
                            targets.add(sp.getId());
                        }
                    }
                }
            }
            
            if (targets.isEmpty())
                return null;
            else
                return targets.get(0);
        }
    }
}
