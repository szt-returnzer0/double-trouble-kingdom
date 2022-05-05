package model;

import java.awt.*;

/**
 * Implementation of the archerless Barricade Tower type.
 */
public class Barricade extends Tower {
    /**
     * Constructs a new Barricade instance.
     *
     * @param position the barricade's position on the Map
     * @param side     the side it belongs to
     */
    public Barricade(Point position, String side) {
        super(position, side);
        this.type = "Barricade";
        this.value = 10;
        this.size = new Dimension(2, 2);
    }

    /**
     * Attack method for the Barricade.
     */
    @Override
    public void attack() {
        throw new UnsupportedOperationException();
    }
}
