package model;

import java.awt.*;

public class Barracks extends Building {
    private boolean isUpgraded;

    public Barracks(Point position, String side) {
        super(position, side);
        this.type = "Barracks";
        this.size = new Dimension(2, 4);
        this.isUpgraded = false;
        this.canUpgrade = true;
        this.value = 50;
    }

    public boolean isUpgraded() {
        return this.isUpgraded;
    }

    @Override
    public int upgrade() {
        if (!this.isUpgraded) {
            this.isUpgraded = true;
            return 30;
        }
        return 0;
    }
}
