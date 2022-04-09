package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Implementation of Map class as a record.
 */
public final class Map implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private final String name;
    private final Terrain[][] tiles;

    /**
     * @param name  the name of the Map
     * @param tiles the tile grid of the Map
     */
    public Map(String name, Terrain[][] tiles) {
        this.name = name;
        this.tiles = tiles;
    }

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

    public String name() {
        return name;
    }

    public Terrain[][] tiles() {
        return tiles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Map) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.tiles, that.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tiles);
    }

    @Override
    public String toString() {
        return "Map[" +
                "name=" + name + ", " +
                "tiles=" + tiles + ']';
    }

}
