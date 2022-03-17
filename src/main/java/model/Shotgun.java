package model;

import java.awt.*;

public class Shotgun extends Tower {
    public Shotgun(Point position, String side) {
        super(position, side);
        this.type = "Shotgun";
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
    }

    public void attack() {
    }
}
