package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Soldier Entity type.
 */
public class Soldier extends Entity {
    /**
     * The current speed of the Soldier.
     */
    protected double speed;
    /**
     * An ArrayList which contains which tiles the Solider can cross.
     */
    protected ArrayList<String> terrains;
    /**
     * The Soldier's target Entity.
     */
    protected Entity target;

    protected Pathfinder pf;


    /**
     * An ArrayList containing the path of the Solider to the enemy Castle.
     */
    protected ArrayList<Point> path;

    /**
     * Constructs a new Soldier instance.
     *
     * @param position the soldier's position
     * @param speed    the soldier's current speed
     */
    public Soldier(Point position, double speed) {
        super(position);
        this.type = "Soldier";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.size = new Dimension(1, 1);
        this.value = 2;
        this.speed = speed;
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert"));
        this.pf = new Pathfinder();
    }

    /**
     * Returns the current speed of the Soldier.
     *
     * @return the current speed of the Soldier
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns which tiles the Solider can cross in an ArrayList.
     *
     * @return which tiles the Solider can cross in an ArrayList
     */
    public ArrayList<String> getTerrains() {
        return terrains;
    }

    /**
     * Returns the path of the Solider to the enemy Castle in an ArrayList.
     *
     * @return the path of the Solider to the enemy Castle in an ArrayList
     */
    public ArrayList<Point> getPath() {
        return pf.genPath(this, (side.equals("left") ? "right" : "left"));
    }

    /**
     * Selects a target for the Soldier to attack.
     */
    protected void selectTarget() {
    }

    /**
     * Attacks the unit's target if nearby.
     */
    public void attack() {
    }

    /**
     * Calculates the shortest path to the enemy Castle.
     */
    protected void calculatePath() {
    }

    /**
     * Adds a waypoint to the Soldier's path.
     */
    public void addWaypoint() {
    }
}
