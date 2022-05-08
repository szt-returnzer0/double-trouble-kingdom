package DTK_model;

import java.awt.*;

/**
 * Implementation of Sniper Tower type.
 */
public class Sniper extends Tower {
    /**
     * Constructs a Sniper instance.
     *
     * @param position the sniper's position on the Map
     * @param side     the side it belongs to
     */
    public Sniper(Point position, Sides side) {
        super(position, side);
        this.type = Types.SNIPER;
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
        this.damage = 3;
        this.range = 7;
        this.canAttack = true;
        this.targetsPerAttack = 1;
    }

    /**
     * Constructs a Sniper instance.
     * @param position the sniper's position on the Map
     */
    public Sniper(Point position) {
        super(position);
        this.type = Types.SNIPER;
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
        this.damage = 3;
        this.range = 7;
        this.canAttack = true;
        this.targetsPerAttack = 1;
    }
}
