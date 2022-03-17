package model;

import java.awt.*;

public class Kamikaze extends Soldier {
    private int splashPercent;

    public Kamikaze(Point position, int speed) {
        super(position, speed);
        this.type = "Kamikaze";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.size = new Dimension(1, 1);
        this.value = 5;
    }

    @Override
    public void attack() {
        super.attack();
    }
}
