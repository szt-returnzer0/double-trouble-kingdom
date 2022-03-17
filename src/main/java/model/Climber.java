package model;

import java.awt.*;

public class Climber extends Soldier {
    public Climber(Point position, int speed) {
        super(position, speed);
        this.type = "Climber";
        this.isAnimated = true;
        this.healthPoints = 10;
        this.size = new Dimension(1, 1);
        this.value = 5;
    }
}
