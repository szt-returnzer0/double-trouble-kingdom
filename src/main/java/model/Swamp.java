package model;

import java.util.ArrayList;

/**
 * Implementation of Swamp Terrain type.
 */
public class Swamp extends Terrain {
    /**
     * Constructs a new Swamp instance without predefined entities.
     */
    public Swamp() {
        super(Types.SWAMP);
        this.speedMod = 3;
    }

    /**
     * Constructs a new Swamp instance with predefined entities.
     *
     * @param entities an ArrayList containing the entities
     */
    public Swamp(ArrayList<Entity> entities) {
        super(Types.SWAMP, entities);
        this.speedMod = 3;
    }
}
