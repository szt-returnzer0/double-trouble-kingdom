package model;

import java.awt.*;
import java.util.ArrayList;

public class Plains extends Terrain {
    public Plains(Point gridPos) {
        super(gridPos, "Plains");
        // this.texture = path/to/texture
    }

    public Plains(Point gridPos, ArrayList<Entity> ent) {
        super(gridPos, "Plains", ent);
        // this.texture = path/to/texture
    }
}
