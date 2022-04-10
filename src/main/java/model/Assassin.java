package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Assassin special soldier type.
 */
public class Assassin extends Soldier {

    /**
     * The soldier targets of the Assassin.
     */
    private ArrayList<Soldier> soldierTargets;

    /**
     * Constructs a new Assassin instance.
     *
     * @param position the assassin's position
     * @param speed    the assassin's current speed
     */
    public Assassin(Point position, double speed) {
        super(position, speed);
        this.type = "Assassin";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert"));

    }

    /**
     * Attacks the unit's target if nearby.
     */
    @Override
    public void attack() {
        int range = 20;
        super.attack();
        if (soldierTargets != null) for (Soldier soldier : soldierTargets) {
            System.out.println(soldier.getPosition().distance(this.getPosition()));
            if (soldier.getPosition().distance(this.getPosition()) <= range) {
                soldier.takeDamage(this.damage);
                System.out.println(this.type + " attacked " + soldier.getType() + " for " + this.damage + " damage.");
            }
        }
    }

    /**
     * Sets the soldier's targets.
     *
     * @param targets the soldier's targets
     */
    public void selectTargets(ArrayList<Soldier> targets) {
        this.soldierTargets = targets;
    }


}
