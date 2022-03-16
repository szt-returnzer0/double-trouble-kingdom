package model;

import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.util.ArrayList;

public abstract class Tower extends Building {
    protected int level;
    protected ArrayList<Entity> targets;
    protected boolean canAttack;
    protected int attackSpeed;

    public Tower(Point position, String side) {
        super(position, side);
        attackSpeed = 1;
    }

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

    @Override
    public int upgrade() {
        if (this.canUpgrade && this.level < 3)
            this.level++;

        switch (level) {
            case 1 -> {
                this.damage += this.damage / 2;
                return 10;
            }
            case 2 -> {
                this.attackSpeed += this.attackSpeed / 2;
                return 15;
            }
            case 3 -> {
                this.healthPoints += this.healthPoints / 3;
                return 20;
            }
        }

        return 0;
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
