package model;

import java.awt.*;

public class Swamp extends Terrain {
    public Swamp(Point gridPos) {
        super(gridPos, "Swamp");
        this.speedMod = 0.3;
        // this.texture = path/to/texture
    }
}
