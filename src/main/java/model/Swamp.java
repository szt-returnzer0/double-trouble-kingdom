package model;

import java.awt.*;
import java.util.ArrayList;

public class Swamp extends Terrain {
    public Swamp(Point gridPos) {
        super(gridPos, "Swamp");
        this.speedMod = 0.3;
        // this.texture = path/to/texture
    }

    public Swamp(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Swamp", ent);
        // this.texture = path/to/texture
    }
}
