package model;

import java.awt.*;
import java.io.Serializable;

public abstract class Building extends Entity implements Serializable {
    protected boolean canUpgrade; // false
    protected boolean isDestroyed; // false

    public Building(Point position, String side) {
        super(position, side);
    }

    @Override
    public void destroy() {
        this.isDestroyed = true;
    }

    public void cleanup() {
        if (isDestroyed)
            super.destroy();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public abstract int upgrade();
}
