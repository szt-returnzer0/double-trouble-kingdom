package model;

import java.awt.*;

public class Shotgun extends Tower {
    public Shotgun(Point position) {
        super(position);
        this.canUpgrade = true;
        this.value = 30;
    }

    public void attack() {
    }
}
