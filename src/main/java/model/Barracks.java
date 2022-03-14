package model;

import java.awt.*;

public class Barracks extends Building {
    private boolean isUpgraded;

    public Barracks(Point position, String side) {
        super(position);
        this.type = "Barracks";
        this.size = new Dimension(2, 4);
        this.side = side;
    }

    public void upgrade() {
    }
}
