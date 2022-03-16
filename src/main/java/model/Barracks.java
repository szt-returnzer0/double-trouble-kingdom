package model;

import java.awt.*;

public class Barracks extends Building {
    private boolean isUpgraded;

    public Barracks(Point position, String side) {
        super(position, side);
        this.type = "Barracks";
        this.size = new Dimension(2, 4);
        //this.side = side;
        this.isUpgraded = false;
        this.canUpgrade = true;
        this.value = 50;
    }

    @Override
    public int upgrade() {
        this.isUpgraded = true;
        return 50;
    }
}
