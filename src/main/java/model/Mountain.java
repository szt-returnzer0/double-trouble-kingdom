package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Implementation of Mountain Terrain type.
 */
public class Mountain extends Terrain {
    /**
     * Constructs a new Mountain instance without predefined entities.
     *
     * @param gridPos the tile's position on the Map
     */
    public Mountain(Point gridPos) {
        super(gridPos, "Mountain");
        this.speedMod = 0.3;
        // this.texture = path/to/texture
    }

    /**
     * Constructs a new Mountain instance with predefined entities.
     *
     * @param gridPos the tile's position on the Map
     * @param ent     an ArrayList containing the entities
     */
    public Mountain(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Mountain", ent);
        // this.texture = path/to/texture
    }
}
