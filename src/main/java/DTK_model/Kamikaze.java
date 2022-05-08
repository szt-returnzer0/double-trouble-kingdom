package DTK_model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Kamikaze Soldier type.
 */
public class Kamikaze extends Soldier {

    /**
     * The towers the Kamikaze can target.
     */
    private ArrayList<Tower> towerTargets;

    /**
     * Constructs a new Kamikaze instance.
     *
     * @param position the kamikaze's position on the Map
     */
    public Kamikaze(Point position) {
        super(position);
        this.type = Types.KAMIKAZE;
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.range = 4;
        this.terrains = new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT));
        this.towerTargets = new ArrayList<>();
        this.speed = 4;
    }

    /**
     * Attacks the unit's target if nearby.
     */
    @Override
    public void attack() {
        super.attack();
        if (canSplash()) {

                for (Tower tower : this.towerTargets) {
                    if (!tower.isDestroyed && tower.getPosition().distance(this.getPosition()) <= range) {
                        tower.takeDamage(this.damage);
                        killUnit();
                    }
                }
            }

    }

    /**
     * Checks if the Kamikaze can splash.
     * @return true if the Kamikaze can splash
     */
    private boolean canSplash() {
        return (Math.random() * 150) >= 100;
    }

    /**
     * Sets the Kamikaze's target list.
     * @param targets the targets to add
     */
    public void selectTargets(ArrayList<Tower> targets) {
        this.towerTargets = targets;
    }
}
