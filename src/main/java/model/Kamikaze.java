package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Kamikaze Soldier type.
 */
public class Kamikaze extends Soldier {

    /**
     * The percentage of attacking a nearby Tower.
     */
    private int splashPercent;

    private ArrayList<Tower> towerTargets;


    /**
     * Constructs a new Kamikaze instance
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
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert"));
        this.splashPercent = 0;
    }

    /**
     * Attacks the unit's target if nearby.
     */
    @Override
    public void attack() {
        if (this.isAlive) {
            int range = 4;
            super.attack();
            //this.splashPercent += 50;
            this.splashPercent = (int) (Math.random() * 150);
            if (this.splashPercent >= 100) {
                this.splashPercent = 0;
                if (this.towerTargets != null)
                    for (Tower tower : this.towerTargets) {
                        if (tower.getPosition().distance(this.getPosition()) <= range && !tower.isDestroyed) {
                            tower.takeDamage(this.damage);
                            this.healthPoints = 0;
                            this.isAlive = false;
                            System.out.println("Kamikaze attacked tower");
                        }
                    }
            }
        }
    }

    public void selectTargets(ArrayList<Tower> targets) {
        this.towerTargets = targets;
    }
}
