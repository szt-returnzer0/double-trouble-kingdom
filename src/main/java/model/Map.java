package model;

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

    /**
     * To string method for Map class.
     *
     * @return the Map instance as a string
     */
    @Override
    public String toString() {
        return "Map[" +
                "name=" + name + ", " +
                "tiles=" + tiles + ']';
    }

}
