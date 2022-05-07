package DTK_model;

import java.awt.*;


/**
 * Implementation of Barracks Building type.
 */
public class Barracks extends Building {
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
        this.upgradeCost = 40;
    }

    /**
     * Returns if the Barrack instance is upgraded.
     *
     * @return if the Barrack instance is upgraded
     */
    public boolean isUpgraded() {
        return this.isUpgraded;
    }

    /**
     * Sets if the Barrack instance is upgraded.
     *
     * @param isUpgraded if the Barrack instance is upgraded
     */
    public void setUpgraded(boolean isUpgraded) {
        this.isUpgraded = isUpgraded;
    }

    /**
     * Upgrades the Barrack instance.
     */
    public void upgrade() {
        this.isUpgraded = true;
    }

    /**
     * Returns the upgrade cost of the Barrack instance.
     *
     * @return the upgrade cost of the Barrack instance
     */
    public int getUpgradeCost() {
        return this.upgradeCost;
    }


}
