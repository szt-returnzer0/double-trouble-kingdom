package model;

import java.awt.*;
import java.io.Serializable;

public class Castle extends Building implements Serializable {

    public Castle(Point position, String side) {
        super(position);
        this.type = "Castle";
        this.isAnimated = false;
        this.healthPoints = 100;
        this.size = new Dimension(5, 15);
        this.side = side;
    }


}
