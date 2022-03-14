package model;

import java.awt.*;
import java.util.ArrayList;

public class Desert extends Terrain {
    public Desert(Point gridPos) {
        super(gridPos, "Desert");
        this.speedMod = 0.5;
        // this.texture = path/to/texture
    }

    public Desert(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Desert", ent);
        // this.texture = path/to/texture
    }
}
