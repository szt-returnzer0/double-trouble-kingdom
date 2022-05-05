package model;

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
     * @param speed    the kamikaze's current speed
     */
    public Kamikaze(Point position, double speed) {
        super(position, speed);
        this.type = "Kamikaze";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.range = 4;
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert"));
        this.towerTargets = new ArrayList<>();
    }

    /**
     * Attacks the unit's target if nearby.
     */
    @Override
    public void attack() {
        super.attack();
        if (canSplash()) {
            if (!this.towerTargets.isEmpty()) {
                for (Tower tower : this.towerTargets) {
                    if (!tower.isDestroyed && tower.getPosition().distance(this.getPosition()) <= range) {
                        tower.takeDamage(this.damage);
                        killUnit();
                    }
                }
            }
        }
    }

    private boolean canSplash() {
        return (Math.random() * 150) >= 100;
    }

    public void selectTargets(ArrayList<Tower> targets) {
        this.towerTargets = targets;
    }
}
