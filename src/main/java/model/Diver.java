package model;

import java.awt.*;

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
    public Diver(Point position, int speed) {
        super(position, speed);
        this.type = "Diver";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.size = new Dimension(1, 1);
        this.value = 5;
    }
}
