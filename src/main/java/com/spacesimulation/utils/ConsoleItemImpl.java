package com.spacesimulation.utils;

import com.spacesimulation.display.ConsoleItem;
import java.awt.Color;

public class ConsoleItemImpl implements ConsoleItem {

    private String id;
    private Point3D location;
    private Color color;
    private double angle;
    private PolygonPlus polygon;
    private String infoText;
    private boolean destroyed;
    private boolean damaged = false;

    public ConsoleItemImpl(String idIn, Point3D loc, Color c, double ang, PolygonPlus poly, String txt, boolean des, boolean dam) {

        // We *should* probably be doing some error checking here but since it's a console utility class, we'll accept what we receive.
        id = idIn;
        location = loc;
        color = c;
        angle = ang;
        polygon = poly;
        infoText = txt;
        destroyed = des;
        damaged = dam;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public double getAngle() {
        return angle;
    }

    public String getId() {
        return id;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D p) {
        location = p;
    }

    public Color getColor() {
        return color;
    }

    public PolygonPlus getPolygon(double zSize) {
        PolygonPlus pp = new PolygonPlus(polygon);
        pp.scale(location.getZ() / zSize + 0.2);
        pp.translate((int) location.getX(), (int) location.getY());
        pp.rotate(angle);
        return pp;
    }

    public String getInfoText() {
        return infoText;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getInfoText() + "\n");

        return sb.toString();

    }

    public boolean isDamaged() {
        return damaged;
    }
}
