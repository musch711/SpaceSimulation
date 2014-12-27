package com.spacesimulation.domain;

import com.spacesimulation.exceptions.InvalidIntegerException;
import java.awt.Color;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.display.ConsoleItem;
import com.spacesimulation.utils.PolygonPlus;

/**
 * A interface that represents a SpaceEntitiy
 * @author Steven Muschler
 */

public interface SpaceEntity {
    /**
     * Movement algorithm for SpaceEntities
     * @param timeInterval the amount time that the entity should travel when called.
     * @throws InvalidIntegerException if timeInterval given is <= 0.
     */
    public void move(int timeInterval) throws InvalidIntegerException;
    
    /**
     * SpaceEntities perform their specific update behavior in this method.
     */
    void update();
    
    /**
     * Creates a ConsoleItem based off of the given SpaceEntity.
     * @return The ConsoleItem that was created.
     * @see com.spacesimulation.display.ConsoleItem
     */
    ConsoleItem makeConsoleItem();
    
    /**
     * @return The angle that the SpaceEntity was traveling at.
     */
    double getAngle();

    /**
     * @return The Color of the SpaceEntity.
     */
    Color getColor();
    
    /**
     * @return The String representation of the SpaceEntity's color.
     * @see java.awt.Color
     */
    String getColorSt();

    /**
     * @return The SpaceEntity's unique String identifier.
     */
    String getId();

    /**
     * @return A Point3D that contains the SpaceEntity's current location.
     * @see com.spacesimulation.utils.Point3D
     */
    Point3D getLocation();

    /**
     * @return A copy of the Polygon used to create the SpaceEntity's icon.
     * @see com.spacesimulation.utils.PolygonPlus
     */
    PolygonPlus getPolygon();
    
    /**
     * Sets the SpaceEntity's destination.  The destination chosen is random.
     */
    void setDestination();

    /**
     * @return String summary of the SpaceEntity's information.
     */
    String getInfoText();
    
    /**
     * Updates the infoText of the SpaceEntity as the information may have changed.
     */
    void updateInfoText();
    
    /**
     * @return True if the SpaceEntity has reached its destination.  False otherwise.
     */
    boolean atDestination();

    /**
     * @return True if the SpaceEntity is destroyed. False otherwise.  The Entity will be removed from the screen if True.
     */
    boolean isDestroyed();

    /**
     * @return True if the SpaceEntity is not at full strength.  False otherwise.  The Entity will be dimmed on the screen if True.
     */
    boolean isDamaged();
    
    /**
     * @return True if the SpaceEntity is a port (SpacePort...).  False otherwise.
     */
    boolean isPort();
    
     /**
     * @return True if the SpaceEntity is a ship (Fighter Ship, Cargo Ship,...).  False otherwise.
     */
    boolean isShip();
    
     /**
     * @return True if the SpaceEntity is targetable by weapons.  False otherwise.
     */
    boolean isTargetable();
    
    /**
     * @return String that contains the SpaceEntity's type.
     */
    String getEntityType();
    
    /**
     * Behavior performed when the SpaceEntity has been targeted on radar.
     * @param p Location of the SpaceEntity that has targeted this SpaceEntity.
     * @see com.spacesimulation.utils.Point3D
     */
    void reactToRadarLock(Point3D p);
    
    /**
     * @param dam Amount of damage to be applied.
     * Reduces SpaceEntity's strength.
     */
    void applyDamage(double dam);
}
