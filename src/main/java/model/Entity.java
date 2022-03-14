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
    protected String side;

    protected Entity(Point position) {
        this.position = position;
        this.isAlive = true;

    }

    public String getSide() {
        return this.side;
    }

    public void setSide(String side) {
        this.side = side;
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

    public Dimension getSize() {
        return this.size;
    }

    public void destroy() {
    }

    public void animate(int x, int y) {
    }

}
