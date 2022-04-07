package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.awt.*;

/**
 * Implementation of Entity abstract class, containing the Entity types common fields and methods.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Barracks.class, name = "Barracks"),
        @JsonSubTypes.Type(value = Castle.class, name = "Castle"),
        @JsonSubTypes.Type(value = Building.class, name = "Barricade"),
        @JsonSubTypes.Type(value = Building.class, name = "Sniper"),
        @JsonSubTypes.Type(value = Building.class, name = "Shotgun"),
        @JsonSubTypes.Type(value = Soldier.class, name = "Soldier"),
        @JsonSubTypes.Type(value = Kamikaze.class, name = "Kamikaze"),
        @JsonSubTypes.Type(value = Diver.class, name = "Diver"),
        @JsonSubTypes.Type(value = Climber.class, name = "Climber"),
        @JsonSubTypes.Type(value = Assassin.class, name = "Assassin"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Entity {
    /**
     * The health of the Entity.
     */
    protected int healthPoints;
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
     * The damage of the Entity's weapon.
     */
    protected int damage;
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
     * The texture of the Entity.
     */
    protected Image texture;
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
     * Retruns the remaining health of the Entity.
     *
     * @return the remaining health of the Entit
     */
    public int getHealthPoints() {
        return healthPoints;
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

    /**
     * Decreeases the Entity's healthPoints.
     *
     * @param value the amount we want to decrease
     */
    public void decreaseHealthPoints(int value) {
        this.healthPoints -= value;
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
     * Rotates the Entity.
     */
    public void invert() {
        this.size = new Dimension(this.size.height, this.size.width);
    }

    /**
     * Destroys the Entity.
     */
    public void destroy() {
    }

    /**
     * Animates the Entity
     *
     * @param x the horizontal coordinate of the animation
     * @param y the vertical coordinate of the animation
     */
    public void animate(int x, int y) {
    }
}
