package com.spacesimulation.display;

import java.awt.Color;
import com.spacesimulation.utils.Point3D;
import com.spacesimulation.utils.PolygonPlus;

public interface ConsoleItem {

    double getAngle();

    Color getColor();

    String getId();

    Point3D getLocation();

    PolygonPlus getPolygon(double zSize);

    void setLocation(Point3D p);

    String getInfoText();

    boolean isDestroyed();

    boolean isDamaged();
}
