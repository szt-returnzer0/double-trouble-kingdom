package model;

import java.awt.*;

public class Mountain extends Terrain {
    public Mountain(Point gridPos) {
        super(gridPos, "Mountain");
        this.speedMod = 0.3;
        // this.texture = path/to/texture
    }
}
