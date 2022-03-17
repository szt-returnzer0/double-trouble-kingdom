package model;

import java.awt.*;

public class Sniper extends Tower {
    public Sniper(Point position, String side) {
        super(position, side);
        this.type = "Sniper";
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
    }

    public void attack() {
    }
}
