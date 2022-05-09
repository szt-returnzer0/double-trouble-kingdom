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
     */
    public Assassin(Point position) {
        super(position);
        this.type = Types.ASSASSIN;
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.terrains = new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT));
        this.range = 2;
        this.soldierTargets = new ArrayList<>();
        this.speed = 6;
    }

    /**
     * Attacks the unit's target if nearby.
     */
    @Override
    public void attack() {
        super.attack();

        for (Soldier soldier : soldierTargets) {
            if (soldier.getPosition().distance(this.getPosition()) <= range) {
                soldier.takeDamage(this.damage);
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
