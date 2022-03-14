package model;

import java.awt.*;

public class Castle extends Building {

    public Castle(Point position, String side) {
        super(position);
        this.type = "Castle";
        this.isAnimated = false;
        this.healthPoints = 100;
        this.size = new Dimension(5, 15);
    }
}
