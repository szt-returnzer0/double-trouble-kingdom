package model;

import java.awt.*;
import java.util.ArrayList;

public class Mountain extends Terrain {
    public Mountain(Point gridPos) {
        super(gridPos, "Mountain");
        this.speedMod = 0.3;
        // this.texture = path/to/texture
    }

    public Mountain(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Mountain", ent);
        // this.texture = path/to/texture
    }
}
