package model;

import java.awt.*;

public class Sniper extends Tower {
    public Sniper(Point position) {
        super(position);
        this.canUpgrade = true;
    }

    public void attack() {
    }
}
