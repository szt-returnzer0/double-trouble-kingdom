package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Assassin special soldier type.
 */
public class Assassin extends Soldier {
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
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert"));
    }

    /**
     * Attacks the unit's target if nearby.
     */
    @Override
    public boolean attack() {
        super.attack();
        return true;
    }
}
