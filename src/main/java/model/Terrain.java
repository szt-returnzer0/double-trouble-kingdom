package model;

import java.awt.*;
import java.util.ArrayList;

public abstract class Terrain {
    protected Point gridPos;
    protected ArrayList<Entity> entities;
    protected String type;
    protected double speedMod;
    protected Image texture;

    protected Terrain(Point gridPos, String type) {
        this.gridPos = gridPos;
        this.entities = new ArrayList<>();
        this.type = type;
        this.speedMod = 1;
    }

    protected Terrain(Point gridPos, String type, ArrayList<Entity> entities) {
        this.gridPos = gridPos;
        this.type = type;
        this.speedMod = 1;
        this.entities = entities;
    }

    public Point getGridPos() {
        return gridPos;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void addEntities(Entity entity) {
        this.entities.add(entity);
    }

    public String getType() {
        return type;
    }

    public double getSpeedMod() {
        return speedMod;
    }
}
