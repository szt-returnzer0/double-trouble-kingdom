package model;

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
    public Shotgun(Point position, String side) {
        super(position, side);
        this.type = "Shotgun";
        this.canUpgrade = true;
        this.value = 30;
        this.size = new Dimension(2, 2);
        this.damage = 5;
        this.range = 3;
        this.canAttack = true;
    }

    /**
     * Attacks the tower's target if nearby.
     */
    public void attack() {
        this.range = 3;
        if (this.targets.size() != 0 && !this.isDestroyed) {
            for (Entity target : this.targets) {
                if (target.getPosition().distance(this.position) <= range) {
                    target.takeDamage(this.damage);
                    if (attackSpeed > 1)
                        target.takeDamage(this.damage);
                }
            }
        }
    }
}
