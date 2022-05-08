package DTK_model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Soldier Entity type.
 */
public class Soldier extends Entity {
    /**
     * The start of visualization highlighting.
     */
    public int visualizationStart;
    /**
     * The end of visualization highlighting.
     */
    public int visualizationEnd;
    /**
     * The current speed of the Soldier.
     */
    protected double speed;
    /**
     * An ArrayList which contains which tiles the Solider can cross.
     */
    protected ArrayList<Types> terrains;
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
     * The location of the enemy Castle's Tile locations.
     */
    protected ArrayList<Point> castleParts;

    /**
     * Constructs a new Soldier instance.
     *
     * @param position the soldier's position
     */
    public Soldier(Point position) {
        super(position);
        this.type = Types.SOLDIER;
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 2;
        this.speed = 2;
        this.terrains = new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT));
        this.pf = new Pathfinder();
        this.damage = 10;
        this.wayPoints = new ArrayList<>();
        this.visualizationStart = this.visualizationEnd = 0;
        this.castleParts = new ArrayList<>();
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
    public ArrayList<Types> getTerrains() {
        return terrains;
    }

    /**
     * Returns the path of the Solider to the enemy Castle in an ArrayList.
     *
     * @return the path of the Solider to the enemy Castle in an ArrayList
     */
    public ArrayList<Point> getPath() {
        return this.path;
    }

    @Override
    public String getImage() {
        String color = (side.equals(Sides.BLUE) ? "Blue" : "Red");
        if (path == null || path.isEmpty()) {
            return color + "Front0.png";
        }
        String[][] Directions = new String[][]{(new String[]{"Front", "Back", "Front"}), (new String[]{"Left", "Front", "Right"}), (new String[]{"Front", "Front", "Front"})};
        return color + Directions[1 + path.get(0).y][1 + path.get(0).x] + animObj.getFrame() + ".png";
    }


    /**
     * Selects a target for the Soldier to attack.
     *
     * @param target the target to select
     */
    public void selectTarget(Entity target) {
        this.target = target;
    }

    /**
     * Attacks the unit's target if nearby.
     */
    public void attack() {
        if (target != null && target.isAlive()) {
            calculateCastleParts();

            if (castleParts.contains(this.getPosition())) {
                target.takeDamage(this.damage);
                killUnit();
            }
        }

    }

    private void calculateCastleParts() {
        for (int i = 0; i < target.getSize().width; i++) {
            for (int j = 0; j < target.getSize().height; j++) {
                castleParts.add(new Point(target.getPosition().x + i, target.getPosition().y + j));
            }
        }
    }

    /**
     * Calculates the shortest path to the enemy Castle.
     */
    protected void calculatePath() {
        this.path = pf.genPath(this, (side.equals(Sides.BLUE) ? Sides.RED : Sides.BLUE), null, "rel");
        this.animObj.setPath(this.path);
    }

    /**
     * Adds a waypoint to the Soldier's path.
     *
     * @param waypoint the waypoint to add
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

    /**
     * Returns the Soldier's absolute path of the found path in an ArrayList.
     * @return the Soldier's absolute path of the found path in an ArrayList
     */
    public ArrayList<Point> getAbsPath() {
        return pf.genPath(this, (side.equals(Sides.BLUE) ? Sides.RED : Sides.BLUE), null, "abs");
    }
}
