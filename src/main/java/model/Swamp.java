package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Implementation of Swamp Terrain type.
 */
public class Swamp extends Terrain {
    /**
     * Constructs a new Swamp instance without predefined entities.
     *
     * @param gridPos the tile's position on the Map
     */
    public Swamp(Point gridPos) {
        super(gridPos, "Swamp");
        this.speedMod = 3;
    }

    /**
     * Constructs a new Swamp instance with predefined entities.
     *
     * @param gridPos the tile's position on the Map
     * @param ent     an ArrayList containing the entities
     */
    public Swamp(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Swamp", ent);
        this.speedMod = 3;
    }
}
