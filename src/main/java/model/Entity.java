package model;

import com.fasterxml.jackson.annotation.*;

import java.awt.*;

/**
 * Implementation of Entity abstract class, containing the Entity types common fields and methods.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({@JsonSubTypes.Type(value = Barracks.class, name = "Barracks"), @JsonSubTypes.Type(value = Castle.class, name = "Castle"), @JsonSubTypes.Type(value = Building.class, name = "Barricade"), @JsonSubTypes.Type(value = Building.class, name = "Sniper"), @JsonSubTypes.Type(value = Building.class, name = "Shotgun"), @JsonSubTypes.Type(value = Soldier.class, name = "Soldier"), @JsonSubTypes.Type(value = Kamikaze.class, name = "Kamikaze"), @JsonSubTypes.Type(value = Diver.class, name = "Diver"), @JsonSubTypes.Type(value = Climber.class, name = "Climber"), @JsonSubTypes.Type(value = Assassin.class, name = "Assassin"),})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Entity {
    /**
     * The health of the Entity.
     */
    protected int healthPoints;

    /**
     * The maximum health of the Entity.
     */
    protected int maxHealthPoints;

    /**
     * The position of the Entity.
     */
    protected Point position;
    /**
     * The type of Entity.
     */
    protected String type;
    /**
     * Determines if the Entity is alive.
     */
    protected boolean isAlive;
    /**
     * The damage the Entity deals.
     */
    protected int damage;


    /**
     * The attack range of the Entity.
     */
    protected int range;

    /**
     * The size of the Entity.
     */
    protected Dimension size;
    /**
     * The cost of the Entity.
     */
    protected int value;
    /**
     * The owner of the Entity.
     */
    protected Player owner;
    /**
     * The Animator of the Entity.
     */
    protected Animator animObj;

    /**
     * Determines if the Entity is animated.
     */
    protected boolean isAnimated;
    /**
     * Determines which side the Entity belongs to.
     */
    protected String side;

    /**
     * Constructs an Entity instance without side.
     *
     * @param position the Entity instance's position on the Map
     */
    protected Entity(Point position) {
        this.position = position;
        this.isAlive = true;
        this.animObj = new Animator(this);
    }

    /**
     * Constructs an Entity instance with side.
     *
     * @param position the Entity instance's position on the Map
     * @param side     the side it belongs to
     */
    protected Entity(Point position, String side) {
        this.position = position;
        this.isAlive = true;
        this.side = side;
        this.animObj = new Animator(this);
    }

    /**
     * Returns the side the Entity belongs to.
     *
     * @return the side the Entity belongs to
     */
    public String getSide() {
        return this.side;
    }

    /**
     * Sets the Entity's side.
     *
     * @param side the side we want to set it
     */
    public void setSide(String side) {
        this.side = side;
    }

    /**
     * Returns the remaining health of the Entity.
     *
     * @return the remaining health of the Entity
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Returns the max health of the Entity.
     *
     * @return the max health of the Entity
     */
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /**
     * Returns the Entity's position.
     *
     * @return Returns the Entity's position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the Entity's position.
     *
     * @param position the new position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Returns the Entity's type.
     *
     * @return the Entity's type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns if the Entity is alive.
     *
     * @return if the Entity is alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets if the Entity is alive.
     *
     * @param alive if the Entity is alive
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Returns the Entity's value.
     *
     * @return the Entity's value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the Entity's owner.
     *
     * @return the Entity's owner
     */
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the Entity's size.
     *
     * @return the Entity's size
     */
    public Dimension getSize() {
        return this.size;
    }

    /**
     * Gets the name of correct sprite.
     *
     * @return name of image.
     */
    @JsonIgnore
    public String getImage() {
        return "empty.png";
    }

    /**
     * Rotates the Entity.
     */
    public void invert() {
        this.size = new Dimension(this.size.height, this.size.width);
    }

    /**
     * Returns the Entity's damage.
     *
     * @return the Entity's damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the Entity's animator.
     *
     * @return the Entity's animator
     */
    public Animator getAnimObj() {
        return animObj;
    }

    /**
     * Gets if the Entity is animated.
     *
     * @return if the Entity is animated
     */
    public boolean isAnimated() {
        return isAnimated;
    }

    /**
     * Sets if the Entity is animated.
     *
     * @param l if the Entity is animated
     */
    public void setAnimated(boolean l) {
        isAnimated = l;
    }

    /**
     * Sets if the Entity is animated.
     *
     * @param l if the Entity is animated
     */
    public void setIsAnimated(boolean l) {
        isAnimated = l;
    }

    /**
     * The Entity takes the passed damage.
     *
     * @param damage the damage to take
     */
    public void takeDamage(int damage) {
        this.healthPoints = Math.max(0, healthPoints - damage);
        this.isAlive = this.healthPoints > 0;
    }

    /**
     * Sets the Entity's health to zero and sets it to dead.
     */
    protected void killUnit() {
        this.healthPoints = 0;
        this.isAlive = false;
    }

}
