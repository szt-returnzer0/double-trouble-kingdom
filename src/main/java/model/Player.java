package model;

import java.util.ArrayList;

public class Player {
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

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }
}
