package model;

import java.awt.*;

public abstract class Entity {
    protected int healthPoints;
    protected Point position;
    protected String type;
    protected boolean isAlive;
    protected int damage;
    protected Dimension size;
    protected int value;
    protected Player owner;
    protected Animator animObj;
    protected Image texture;
    protected boolean isAnimated;

    protected Entity(Point position) {
        this.position = position;
        this.isAlive = true;

    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getValue() {
        return value;
    }

    public Player getOwner() {
        return owner;
    }

    public void decreaseHealthPoints(int value) {
        this.healthPoints -= value;
    }

    public void destroy() {
    }

    public void animate(int x, int y) {
    }

}
