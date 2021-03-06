package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;
import java.io.Serializable;

/**
 * Implements the Building Entity type.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Castle.class, name = "Castle"),
        @JsonSubTypes.Type(value = Barracks.class, name = "Barracks"),
        @JsonSubTypes.Type(value = Barricade.class, name = "Barricade"),
        @JsonSubTypes.Type(value = Sniper.class, name = "Sniper"),
        @JsonSubTypes.Type(value = Shotgun.class, name = "Shotgun"),
})
public abstract class Building extends Entity implements Serializable {
    /**
     * Returns if the building can be upgraded.
     */
    protected boolean canUpgrade;

    /**
     * Returns if the building is destroyed.
     */
    protected boolean isDestroyed;

    /**
     * Determines if the Barrack instance is upgraded to train special units.
     */
    protected boolean isUpgraded;

    /**
     * Returns the building's upgrade cost.
     */
    protected int upgradeCost;

    /**
     * Constructs a new Building instance.
     *
     * @param position the building's position on the Map
     * @param side     the side it belongs to
     */
    public Building(Point position, Sides side) {
        super(position, side);
        isUpgraded = false;
    }

    /**
     * Constructs a new Building instance.
     *
     * @param position the building's position on the Map
     */
    public Building(Point position) {
        super(position);
        isUpgraded = false;
    }

    /**
     * Destroys the Building instance, converts it to a ruin.
     */
    public void destroy() {
        this.isDestroyed = true;
    }


    /**
     * Returns if the Building instance is a ruin.
     *
     * @return if the Building instance is a ruin
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    /**
     * Sets if the Building instance is destroyed.
     *
     * @param destroyed if the Building instance is destroyed
     */
    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }

    @Override
    public String getImage() {
        int invert = (size.width > size.height) ? 1 : 0;
        if (canUpgrade && invert == 1)
            invert++;

        int upgrade = isUpgraded ? 1 : 0;
        String color = (side.equals(Sides.BLUE) ? "Blue" : "Red");
        return type.text + color + (upgrade + invert) + ".png";
    }

    /**
     * Upgrades the Building instance, implementation depends on Building type.
     */
    public abstract void upgrade();

    /**
     * Returns the upgrade cost of the Building instance.
     *
     * @return the upgrade cost of the Building instance
     */
    @JsonIgnore
    public abstract int getUpgradeCost();
}
