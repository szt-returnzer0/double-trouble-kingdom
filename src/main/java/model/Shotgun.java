package model;

import java.awt.*;

public class Shotgun extends Tower {
    public Shotgun(Point position, String side) {
        super(position, side);
        this.canUpgrade = true;
        this.value = 30;
    }

    public void attack() {
    }
}
