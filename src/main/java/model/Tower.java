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
    protected ArrayList<Soldier> targets;
    /**
     * Determines if the Tower can attack.
     */
    protected boolean canAttack;

    /**
     * The attack range of the Tower.
     */
    protected int range;


    /**
     * The Tower's attacking speed.
     */
    protected int attackSpeed;

    /**
     * The maximum amount of targets the Tower can attack at once.
     */
    protected int targetCount;

    /**
     * The amount of targets the Tower can attack at once.
     */
    protected int targetsPerAttack;


    /**
     * Constructs a new Tower instance.
     *
     * @param position the tower's position on the Map
     * @param side     the side it belongs to
     */
    public Tower(Point position, Sides side) {
        super(position, side);
        this.healthPoints = 20;
        this.maxHealthPoints = this.healthPoints;
        this.attackSpeed = 1;
        this.range = 0;
        this.canAttack = false;
        this.targets = new ArrayList<>();
        this.upgradeCost = 5;

    }

    /**
     * Constructs a new Tower instance.
     * @param  position the tower's position on the Map
     */
    public Tower(Point position) {
        super(position);
        this.healthPoints = 20;
        this.maxHealthPoints = this.healthPoints;
        this.attackSpeed = 1;
        this.range = 0;
        this.canAttack = false;
        this.targets = new ArrayList<>();
        this.upgradeCost = 5;

    }

    /**
     * Transforms the Tower's subclass to a new Tower subclass.
     *
     * @param type the type to convert to
     * @return the new Tower subclass instance
     */
    public Tower transform(Types type) {
        switch (type) {
            case SNIPER -> {
                return new Sniper(position, side);
            }
            case SHOTGUN -> {
                return new Shotgun(position, side);
            }
            default -> {
                try {
                    throw new OperationNotSupportedException("Tower type is not Sniper or Shotgun");
                } catch (OperationNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }

    /**
     * Upgrades the Tower.
     */
    @Override
    public void upgrade() {
        if (this.level < 3) {
            this.level++;

            switch (level) {
                case 1 -> this.damage += 2;

                case 2 -> this.attackSpeed += 1;

                case 3 -> {
                    this.healthPoints += 10;
                    this.maxHealthPoints += 10;
                }
            }
        }

    }

    public int getUpgradeCost() {
        return (this.level + 1) * this.upgradeCost;
    }


    /**
     * Selects the Tower's targets.
     *
     * @param entities the entities to select
     */
    public void selectTargets(ArrayList<Soldier> entities) {
        targets = entities;
    }

    /**
     * Returns the Tower's level.
     *
     * @return the Tower's level
     */
    public int getLevel() {
        return level;
    }


    @Override
    public String getImage() {
        String color = (side.equals(Sides.BLUE) ? "Blue" : "Red");
        if (isDestroyed)
            return color + "Destroyed.png";
        return type.text + color + level + ".png";
    }

    /**
     * Returns the Tower's targets in an ArrayList.
     *
     * @return the Tower's targets in an ArrayList
     */
    public ArrayList<Soldier> getTargets() {
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

    /**
     * Returns the Tower's range.
     * @return the Tower's range
     */
    public int getRange() {
        return range;
    }

    /**
     * Attacks the targets.
     */
    public void attack() {
        targetCount = 0;

        for (Soldier target : this.targets) {
            if (target.getPosition().distance(this.position) <= range && targetCount < targetsPerAttack && !target.isTargeted()) {
                target.setTargeted(true);
                target.takeDamage(this.damage);
                if (attackSpeed > 1)
                    target.takeDamage(this.damage);
                targetCount++;
            }
        }
    }

    /**
     * Takes damage from an attack.
     *
     * @param damage the damage to take
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        this.isAlive = true;
        this.isDestroyed = this.healthPoints <= 0;
    }

    /**
     * Returns the attack speed of the Tower.
     * @return the attack speed of the Tower
     */
    public int getAttackSpeed() {
        return attackSpeed;
    }
}
