package model;

import java.awt.*;
import java.util.ArrayList;

public class Soldier extends Entity {
    protected int speed;
    protected ArrayList<String> terrains;
    protected Entity target;
    protected ArrayList<Point> path;

    public Soldier(Point position, int speed) {
        super(position);
    }

    public int getSpeed() {
        return speed;
    }

    public ArrayList<String> getTerrains() {
        return terrains;
    }

    public ArrayList<Point> getPath() {
        return path;
    }

    protected void selectTarget() {
    }

    public void attack() {
    }

    protected void calculatePath() {
    }

    public void addWaypoint() {
    }
}
