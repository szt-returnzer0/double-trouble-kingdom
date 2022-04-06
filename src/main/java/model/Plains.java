package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Implementation of Mountain Terrain type.
 */
public class Plains extends Terrain {
    /**
     * Constructs a new Plains instance without predefined entities.
     *
     * @param gridPos the tile's position on the Map
     */
    public Plains(Point gridPos) {
        super(gridPos, "Plains");
        this.speedMod = 1;
        // this.texture = path/to/texture
    }

    /**
     * Constructs a new Plains instance with predefined entities.
     *
     * @param gridPos the tile's position on the Map
     * @param ent     an ArrayList containing the entities
     */
    public Plains(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Plains", ent);
        speedMod = 1;
        // this.texture = path/to/texture
    }
}
