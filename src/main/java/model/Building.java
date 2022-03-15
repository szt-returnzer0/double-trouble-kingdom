package model;

import java.awt.*;
import java.io.Serializable;

public abstract class Building extends Entity implements Serializable {
    public Building(Point position) {
        super(position);
    }
}
