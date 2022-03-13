package model;

import java.awt.*;
import java.util.ArrayList;

public abstract class Tower extends Building {
    protected int level;
    protected ArrayList<Entity> targets;
    protected boolean canAttack;

    public Tower(Point position) {
        super(position);
    }

    public void upgrade() {
    }

    protected void selectTargets() {
    }
}
