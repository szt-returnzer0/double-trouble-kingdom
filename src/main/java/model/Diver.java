package model;

import java.awt.*;

public class Diver extends Soldier {
    public Diver(Point position, int speed) {
        super(position, speed);
        this.type = "Diver";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.size = new Dimension(1, 1);
        this.value = 5;
    }
}
