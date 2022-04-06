package model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Map class as a record.
 */
public final class Map implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private String name;
    private Terrain[][] tiles;

    /**
     * @param name  the name of the Map
     * @param tiles the tile grid of the Map
     */
    @JsonCreator
    public Map(String name, Terrain[][] tiles) {
        this.name = name;
        this.tiles = tiles;
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

    void setMap(Map map) {
        this.name = map.name;
        this.tiles = map.tiles;
    }
}
