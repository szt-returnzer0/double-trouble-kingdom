package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementation of Desert Terrain type.
 */
public class Desert extends Terrain {
    /**
     * Constructs a new Desert instance without predefined entities.
     *
     * @param gridPos the tile's position on the Map
     */
    public Desert(Point gridPos) {
        super(gridPos, "Desert");
        this.speedMod = 2;

    }

    /**
     * Constructs a new Desert instance with predefined entities.
     *
     * @param gridPos the tile's position on the Map
     * @param ent     an ArrayList containing the entities
     */
    public Desert(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Desert", ent);
        this.speedMod = 2;
    }
}
