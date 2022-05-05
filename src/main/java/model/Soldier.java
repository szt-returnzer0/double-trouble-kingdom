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

    /**
     * The soldier's pathfinder
     */
    protected Pathfinder pf;


    /**
     * An ArrayList containing the path of the Solider to the enemy Castle.
     */
    protected ArrayList<Point> path;

    /**
     * The soldier's waypoints.
     */
    protected ArrayList<Point> wayPoints;

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
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 2;
        this.speed = speed;
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert"));
        this.pf = new Pathfinder();
        this.damage = 10;
        this.wayPoints = new ArrayList<>();
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
        calculatePath();
        return this.path;
    }

    @Override
    public String getImage() {
        String color = (side.equals("left") ? "Blue" : "Red");
        if (path == null || path.isEmpty()) {
            return color + "Front0.png";
        }
        String[][] Directions = new String[][]{(new String[]{"Front", "Back", "Front"}), (new String[]{"Left", "Front", "Right"}), (new String[]{"Front", "Front", "Front"})};
        return color + Directions[1 + path.get(0).y][1 + path.get(0).x] + animObj.getFrame() + ".png";
    }

    public int visStartPoint = 0;

    /**
     * Selects a target for the Soldier to attack.
     */
    public void selectTarget(Entity target) {
        this.target = target;
    }

    /**
     * Attacks the unit's target if nearby.
     */
    public void attack() {
        if (isAlive() && target != null && target.isAlive()) {
            ArrayList<Point> targetPoints = new ArrayList<>();
            for (int i = 0; i < target.getSize().width; i++) {
                for (int j = 0; j < target.getSize().height; j++) {
                    targetPoints.add(new Point(target.getPosition().x + i, target.getPosition().y + j));
                }
            }

            if (targetPoints.contains(this.getPosition())) {
                target.takeDamage(this.damage);
                this.healthPoints = 0;
                this.isAlive = false;

            }
        }

    }

    /**
     * Calculates the shortest path to the enemy Castle.
     */
    protected void calculatePath() {

        this.path = pf.genPath(this, (side.equals("left") ? "right" : "left"), null, "rel");
        this.animObj.setPath(this.path);
    }

    /**
     * Adds a waypoint to the Soldier's path.
     */
    public void addWaypoint(Point waypoint) {
        this.wayPoints.add(waypoint);
    }

    /**
     * Returns the waypoints of the Soldier in an ArrayList.
     *
     * @return the waypoints of the Soldier in an ArrayList
     */
    public ArrayList<Point> getWayPoints() {
        return wayPoints;
    }

    public int visEndPoint = 0;

    public ArrayList<Point> getAbsPath() {
        return pf.genPath(this, (side.equals("left") ? "right" : "left"), null, "abs");
    }
}
