package DTK_model;

import java.awt.*;

/**
 * Implementation of Castle Building type.
 */
public class Castle extends Building {

    /**
     * Constructs a new Castle instance.
     *
     * @param position the castle's position on the Map
     * @param side     the side it belongs to
     */
    public Castle(Point position, String side) {
        super(position, side);
        this.type = "Castle";
        this.isAnimated = false;
        this.healthPoints = 100;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(5, 15);
    }

    /**
     * Upgrades the Building instance, Castles cannot be upgraded
     */
    public void upgrade() {
        throw new UnsupportedOperationException("Castles cannot be upgraded");
    }

    public int getUpgradeCost() {
        throw new UnsupportedOperationException("Castles cannot be upgraded");
    }
}
