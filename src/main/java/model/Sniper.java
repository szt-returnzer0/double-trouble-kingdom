package model;

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
    public Sniper(Point position, String side) {
        super(position, side);
        this.type = "Sniper";
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
        this.damage = 3;
        this.range = 7;
        this.canAttack = true;
    }

    /**
     * Attacks the tower's target if nearby.
     */
    public void attack() {
        this.range = 7;
        if (this.targets.size() != 0 && this.isAlive) {
            if (this.targets.get(0).getPosition().distance(this.position) <= range) {
                this.targets.get(0).takeDamage(this.damage);
                if (attackSpeed > 1)
                    this.targets.get(0).takeDamage(this.damage);

            }
        }
    }
}
