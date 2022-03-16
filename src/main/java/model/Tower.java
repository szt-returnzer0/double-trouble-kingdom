package model;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public abstract class Tower extends Building {
    protected int level;
    protected ArrayList<Entity> targets;
    protected boolean canAttack;
    protected int attackSpeed;

    public Tower(Point position) {
        super(position);
        attackSpeed = 1;
    }

    public Tower transform(String type) {
        Tower newTower;
        switch (type) {
            case "Sniper" -> newTower = new Sniper(position);
            case "Shotgun" -> newTower = new Shotgun(position);
            default -> throw new InvalidParameterException("Tower type not Sniper or Shotgun");
        }
        return newTower;
    }

    public void upgrade() {
        if (this.canUpgrade)
            this.level++;

        switch (level) {
            case 1 -> this.damage += this.damage / 2;
            case 2 -> this.attackSpeed += this.attackSpeed / 2;
            case 3 -> this.healthPoints += this.healthPoints / 3;
        }
    }

    protected void selectTargets() {
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Entity> getTargets() {
        return targets;
    }

    public boolean isCanAttack() {
        return canAttack;
    }
}
