package model;

import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.util.ArrayList;

/**
 * Implementation of the Tower Building type.
 */
public abstract class Tower extends Building {
    /**
     * The level of the Tower.
     */
    protected int level;
    /**
     * The targets of the Tower in an ArrayList.
     */
    protected ArrayList<Entity> targets;
    /**
     * Determines if the Tower can attack.
     */
    protected boolean canAttack;
    /**
     * The Tower's attacking speed.
     */
    protected int attackSpeed;

    /**
     * Constructs a new Tower instance.
     *
     * @param position the tower's position on the Map
     * @param side     the side it belongs to
     */
    public Tower(Point position, String side) {
        super(position, side);
        this.healthPoints = 20;
        this.maxHealthPoints = this.healthPoints;
        attackSpeed = 1;
    }

    /**
     * Transforms the Tower's subclass to a new Tower subclass.
     *
     * @param type the type to convert to
     * @return the new Tower subclass instance
     */
    public Tower transform(String type) {
        switch (type) {
            case "Sniper" -> {
                return new Sniper(position, side);
            }
            case "Shotgun" -> {
                return new Shotgun(position, side);
            }
            default -> {
                try {
                    throw new OperationNotSupportedException("Tower type not Sniper or Shotgun");
                } catch (OperationNotSupportedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    /**
     * Upgrades the Tower.
     *
     * @return the cost of the upgrade
     */
    @Override
    public int upgrade() {
        if (this.canUpgrade && this.level < 3) {
            this.level++;

            switch (level) {
                case 1 -> {
                    this.damage += 2;
                    return 10;
                }
                case 2 -> {
                    this.attackSpeed += 1;
                    return 15;
                }
                case 3 -> {
                    this.healthPoints += 10;
                    this.maxHealthPoints += 10;
                    return 20;
                }
            }
        }

        return 0;
    }

    /**
     * Selects the Tower's targets.
     */
    protected void selectTargets(ArrayList<Soldier> entities) {
        targets = new ArrayList<>();
        targets.addAll(entities);
    }

    /**
     * Returns the Tower's level.
     *
     * @return the Tower's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the Tower's targets in an ArrayList.
     *
     * @return the Tower's targets in an ArrayList
     */
    public ArrayList<Entity> getTargets() {
        return targets;
    }

    /**
     * Returns if the Tower can attack.
     *
     * @return if the Tower can attack
     */
    public boolean isCanAttack() {
        return canAttack;
    }

    public abstract void attack();

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        this.isAlive = true;
        this.isDestroyed = this.healthPoints <= 0;
    }

}
