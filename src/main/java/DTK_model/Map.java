package DTK_model;

import java.awt.*;
import java.io.Serializable;

/**
 * Implementation of Map class as a record.
 */
public final class Map implements Serializable {
    /**
     * The name of the map.
     */
    private final String name;
    /**
     * The tiles of the map.
     */
    private final Terrain[][] tiles;

    /**
     * @param name  the name of the Map
     * @param tiles the tile grid of the Map
     */
    public Map(String name, Terrain[][] tiles) {
        this.name = name;
        this.tiles = tiles;
    }

    /**
     * Copy constructor for Map class.
     *
     * @param map the Map to copy
     */
    public Map(Map map) {
        this.name = map.name;
        this.tiles = map.tiles;
    }

    public Map() {
        this.name = "Alapértelmezett";
        this.tiles = new Terrain[32][64];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Plains();
            }
        }
        Building[] buildings = new Building[]{new Castle(new Point(3, 8), Sides.BLUE), new Castle(new Point(56, 8), Sides.RED),
                new Barracks(new Point(3, 5), Sides.BLUE), new Barracks(new Point(57, 5), Sides.RED),
                new Barracks(new Point(3, 24), Sides.BLUE), new Barracks(new Point(57, 24), Sides.RED),
        };

        for (Building building : buildings) {
            if (building instanceof Barracks) {
                building.invert();
            }
            for (int y = building.getPosition().y; y < building.getPosition().y + building.getSize().height; y++) {
                for (int x = building.getPosition().x; x < building.getPosition().x + building.getSize().width; x++) {
                    tiles[y][x].addEntities(building);
                }
            }
        }
    }

    /**
     * Returns the Map instance's name.
     *
     * @return the Map instance's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Map instance's tile grid.
     *
     * @return the Map instance's tile grid
     */
    public Terrain[][] getTiles() {
        return tiles;
    }


}
