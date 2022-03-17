package model;

public class Map {
    private final String name;
    private final Terrain[][] tiles;

    public Map(String name, Terrain[][] tiles) {
        this.name = name;
        this.tiles = tiles;
    }

    public String getName() {
        return name;
    }

    public Terrain[][] getTiles() {
        return tiles;
    }
}
