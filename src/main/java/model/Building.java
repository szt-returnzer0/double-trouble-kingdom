package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;

/**
 * Implements the Building Entity type.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Barracks.class, name = "Barracks"),
        @JsonSubTypes.Type(value = Barricade.class, name = "Barricade"),
        @JsonSubTypes.Type(value = Sniper.class, name = "Sniper"),
        @JsonSubTypes.Type(value = Shotgun.class, name = "Shotgun"),
})
public abstract class Building extends Entity {
    protected boolean canUpgrade; // false
    protected boolean isDestroyed; // false

    /**
     * Constructs a new Building instance.
     *
     * @param position the building's position on the Map
     * @param side     the side it belongs to
     */
    public Building(Point position, String side) {
        super(position, side);
    }

    /**
     * Destroys the Building instance, converts it to a ruin.
     */
    @Override
    public void destroy() {
        this.isDestroyed = true;
    }

    /**
     * Cleans up the Building after it became a ruin.
     */
    public void cleanup() {
        if (isDestroyed)
            super.destroy();
    }

    /**
     * Returns if the Building instance is a ruin.
     *
     * @return if the Building instance is a ruin
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }

    /**
     * Upgrades the Building instance, implementation depends on Building type.
     *
     * @return the cost of the upgrade
     */
    public abstract int upgrade();
}
