package DTK_model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Implementation of the Player class for Double Trouble Kingdom Game, contains
 * the Player's name, entities and gold. Implements the methods of manipulating
 * entities.
 */
public class Player {
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
    private final Sides side;
    /**
     * ArrayList containing the player's entities.
     */
    private ArrayList<Entity> entities;
    /**
     * The player's gold amount.
     */
    private int gold;
    /**
     * Determines if the Player has unlocked all units.
     */
    private boolean isUnitRestricted;
    private int soldierCount;

    /**
     * Constructs the Player with starter gold, entities and name.
     *
     * @param name the name of the Player
     */
    @JsonCreator
    public Player(String name) {
        if (number == 2)
            number = 0;
        this.playerNumber = ++number;
        this.name = name;
        this.gold = 100;
        this.isUnitRestricted = true;
        this.entities = new ArrayList<>();
        this.side = playerNumber == 1 ? Sides.BLUE : Sides.RED;
    }

    /**
     * Returns the player's side.
     * @return the player's side
     */
    public Sides getSide() {
        return side;
    }

    /**
     * Returns if the player has unlocked all unit types.
     *
     * @return if the player has unlocked all unit types
     */
    public boolean isUnitRestricted() {
        return isUnitRestricted;
    }

    /**
     * Sets the unit restriction to the given value.
     * @param isUnitRestricted the value to set the unit restriction to
     */
    public void setUnitRestricted(boolean isUnitRestricted) {
        this.isUnitRestricted = isUnitRestricted;
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
     * Sets the player's entities.
     * @param entities the player's entities
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Adds an entity to the player's entity ArrayList.
     *
     * @param entity the entity to add
     */
    public void addEntity(Entity entity) {
        if (entity.value < this.gold) {
            this.entities.add(entity);
            this.gold -= entity.value;
            if (entity instanceof Soldier)
                soldierCount++;
        }
    }

    /**
     * Returns the number of soldiers the player has.
     *
     * @return the number of soldiers the player has
     */
    public int getSoldierCount() {
        return soldierCount;
    }

    /**
     * Sets the number of soldiers the player has.
     *
     * @param soldierCount the number of soldiers the player has
     */
    public void setSoldierCount(int soldierCount) {
        this.soldierCount = soldierCount;
    }

    /**
     * Remove a soldier from the player's entity ArrayList.
     *
     * @param s the soldier to remove
     */
    public void removeSoldier(Soldier s) {
        soldierCount--;
        entities.remove(s);
        GameState.animBuffer.remove(s.getAnimObj());
        s.getAnimObj().stopAnimation();
        s.getAnimObj().removePath();
    }

    /**
     * Adds an entity to the player's entity ArrayList from a save.
     *
     * @param entity the entity to add
     */
    public void addSavedEntity(Entity entity) {
        this.entities.add(entity);
    }

    /**
     * Upgrades the selected Building, removes the upgrade cost from the player's
     * gold.
     *
     * @param building the Building to upgrade
     */
    public void upgradeBuilding(Building building) {
        if (building.canUpgrade) {
            if (building.getUpgradeCost() < this.gold) {
                building.upgrade();
                this.gold -= building.getUpgradeCost();
                if (building instanceof Barracks)
                    isUnitRestricted = false;
            }
        }
    }

    /**
     * Transforms the selected Tower to the given type, refunds a part of the
     * building cost.
     *
     * @param tower the tower to transform
     * @param type  the type we want
     * @return new Tower Entity
     */
    public Tower transformTower(Tower tower, Types type) { // rewrite with void method
        this.gold += 10;
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

    /**
     * Returns the player's castle
     *
     * @return the player's castle
     */
    @JsonIgnore
    public Building getCastle() {
        for (Entity entity : entities) {
            if (entity instanceof Castle castle) {
                return castle;
            }
        }
        throw new IllegalStateException("No castle found");
    }

    /**
     * Returns the player's soldiers
     *
     * @return the player's soldiers
     */
    public ArrayList<Soldier> getSoldiers() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Soldier) {
                soldiers.add((Soldier) entity);
            }
        }
        return soldiers;
    }

    /**
     * Returns the player's towers
     *
     * @return the player's towers
     */
    public ArrayList<Tower> getTowers() {
        ArrayList<Tower> towers = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Tower) {
                towers.add((Tower) entity);
            }
        }
        return towers;
    }

    /**
     * Calculates the player's gold at round start.
     */
    public void calculateGoldAtRound() {
        gold += 25;
        gold += soldierCount * 2;
    }
}
