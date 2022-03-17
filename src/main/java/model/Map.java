package model;

import java.io.Serializable;

public record Map(String name, Terrain[][] tiles) implements Serializable {

    public String getName() {
        return name;
    }

    public Terrain[][] getTiles() {
        return tiles;
    }
}
