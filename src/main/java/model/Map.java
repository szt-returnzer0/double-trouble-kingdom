package model;

import java.io.Serializable;

/**
 * Implementation of Map class as a record.
 *
 * @param name  the name of the Map
 * @param tiles the tile grid of the Map
 */
public record Map(String name, Terrain[][] tiles) implements Serializable {

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
