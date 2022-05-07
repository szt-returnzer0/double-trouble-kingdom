package DTK_model;

import java.awt.*;

/**
 * Implementation of Shotgun Tower type.
 */
public class Shotgun extends Tower {

    /**
     * Constructs a Shotgun instance.
     *
     * @param position the sniper's position on the Map
     * @param side     the side it belongs to
     */
    public Shotgun(Point position, Sides side) {
        super(position, side);
        this.type = Types.SHOTGUN;
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
        this.damage = 5;
        this.range = 3;
        this.canAttack = true;
        this.targetsPerAttack = 5;
    }

    public Shotgun(Point position) {
        super(position);
        this.type = Types.SHOTGUN;
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
        this.damage = 5;
        this.range = 3;
        this.canAttack = true;
        this.targetsPerAttack = 5;
    }
}
