package model;

import java.awt.*;

/**
 * Implementation of Barracks Building type.
 */
public class Barracks extends Building {
    /**
     * Determines if the Barrack instance is upgraded to train special units.
     */
    private boolean isUpgraded;

    /**
     * Constructs a new Barracks instance.
     *
     * @param position the barrack's position on the Map
     * @param side     the side it belongs to
     */
    public Barracks(Point position, String side) {
        super(position, side);
        this.type = "Barracks";
        this.size = new Dimension(2, 4);
        this.isUpgraded = false;
        this.canUpgrade = true;
        this.value = 50;
        this.healthPoints = 50;
        this.maxHealthPoints = this.healthPoints;
    }

    /**
     * Returns if the Barrack instance is upgraded.
     *
     * @return if the Barrack instance is upgraded
     */
    public boolean isUpgraded() {
        return this.isUpgraded;
    }

    public void setUpgraded(boolean isUpgraded) {
        this.isUpgraded = isUpgraded;
    }

    /**
     * Upgrades the Barrack instance.
     *
     * @return the cost of the upgrade
     */
    @Override
    public int upgrade() {
        if (!this.isUpgraded) {
            this.isUpgraded = true;
            return 30;
        }
        return 0;
    }
}
