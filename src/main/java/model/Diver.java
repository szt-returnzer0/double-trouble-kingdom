package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Diver Soldier type.
 */
public class Diver extends Soldier {
    /**
     * Constructs a new Diver instance
     *
     * @param position the diver's position on the Map
     * @param speed    the diver's current speed
     */
    public Diver(Point position, double speed) {
        super(position, speed);
        this.type = "Diver";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.terrains = new ArrayList<>(Arrays.asList("Plains", "Desert", "Swamp"));
    }
}
