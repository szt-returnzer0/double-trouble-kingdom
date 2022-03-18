package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    static private int number;
    private final int playerNumber;
    private final String name;
    private final ArrayList<Entity> entities;
    private int gold;

    public Player(String name) {
        this.playerNumber = ++number;
        this.name = name;
        this.gold = 100;
        this.entities = new ArrayList<>(); // 1x Castle 2x Barrack
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) { // or string as parameter
        this.entities.add(entity);
        this.gold -= entity.value;
    }

    public void upgradeBuilding(Building building) {
        this.gold -= building.upgrade();
    }

    public Tower transformTower(Tower tower, String type) {
        this.gold += 20;
        return tower.transform(type);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }
}
