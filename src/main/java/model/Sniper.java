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
        if (this.targets.size() != 0 && !this.isDestroyed) {
            for (Entity target : this.targets) {
                if (target.getPosition().distance(this.position) <= range) {
                    target.takeDamage(this.damage);
                    if (attackSpeed > 1)
                        target.takeDamage(this.damage);
                    return;
                }
            }
        }
    }
}
