package model;

import java.awt.*;

public class Desert extends Terrain {
    public Desert(Point gridPos) {
        super(gridPos, "Desert");
        this.speedMod = 0.5;
        // this.texture = path/to/texture
    }
}
