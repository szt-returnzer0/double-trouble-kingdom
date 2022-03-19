package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implementation of the Player class for Double Trouble Kingdom Game, contains the Player's name, entities and gold. Implements the methods of manipulating entities.
 */
public class Player implements Serializable {
    /**
     * Static counter to determine the player's playerNumber.
     */
    static private int number;
    /**
     * The player's number.
     */
    private final int playerNumber;
    /**
     * The player's name.
     */
    private final String name;
    /**
     * ArrayList containing the player's entities.
     */
    private final ArrayList<Entity> entities;
    /**
     * The player's gold amount.
     */
    private int gold;

    /**
     * Constructs the Player with starter gold, entities and name.
     *
     * @param name the name of the Player
     */
    public Player(String name) {
        if (number == 2) number = 0;
        this.playerNumber = ++number;
        this.name = name;
        this.gold = 100;
        this.entities = new ArrayList<>(); // 1x Castle 2x Barrack
    }

    /**
     * Returns the player's number.
     *
     * @return the player's number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's gold amount.
     *
     * @return the player's gold amount
     */
    public int getGold() {
        return gold;
    }

    /**
     * Adds gold to the player's gold amount.
     *
     * @param amount the amount of gold to add
     */
    public void addGold(int amount) {
        this.gold += amount;
    }

    /**
     * Returns an ArrayList with the player's entities.
     *
     * @return an ArrayList with the player's entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Adds an entity to the player's entity ArrayList.
     *
     * @param entity the entity to add
     */
    public void addEntity(Entity entity) { // or string as parameter to create Factory
        this.entities.add(entity);
        this.gold -= entity.value;
    }

    /**
     * Upgrades the selected Building, removes the upgrade cost from the player's gold.
     *
     * @param building the Building to upgrade
     */
    public void upgradeBuilding(Building building) {
        this.gold -= building.upgrade();
    }

    /**
     * Transforms the selected Tower to the given type, refunds a part of the building cost.
     *
     * @param tower the tower to transform
     * @param type  the type we want
     * @return new Tower Entity
     */
    public Tower transformTower(Tower tower, String type) { // rewrite with void method
        this.gold += 20;
        return tower.transform(type);
    }

    /**
     * Removes the given entity from the player's entities ArrayList.
     *
     * @param entity the entity to remove
     */
    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
        addGold(entity.value / 3);
    }
}
