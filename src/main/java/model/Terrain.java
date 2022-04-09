package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;
import java.util.ArrayList;

/**
 * Implementation of Terrain class.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Plains.class, name = "Plains"),
        @JsonSubTypes.Type(value = Desert.class, name = "Desert"),
        @JsonSubTypes.Type(value = Swamp.class, name = "Swamp"),
        @JsonSubTypes.Type(value = Mountain.class, name = "Mountain"),
})
public abstract class Terrain {
    /**
     * The position of the tile.
     */
    protected Point gridPos;
    /**
     * An ArrayList of Entities on the tile.
     */
    protected ArrayList<Entity> entities;
    /**
     * The type of the tile.
     */
    protected String type;
    /**
     * The speed modifier of the tile.
     */
    protected int speedMod;

    /**
     * The texture of the tile.
     */
    protected String texture;

    public String getTexture() {
        return texture;
    }

    /**
     * Constructs a new Terrain instance without entities.
     *
     * @param gridPos the position of the tile
     * @param type    the type of tile
     */
    protected Terrain(Point gridPos, String type) {
        this.gridPos = gridPos;
        this.entities = new ArrayList<>();
        this.type = type;
    }

    /**
     * Constructs a new Terrain instance without entities.
     *
     * @param gridPos  the position of the tile
     * @param type     the type of tile
     * @param entities an ArrayList containing the entities
     */
    protected Terrain(Point gridPos, String type, ArrayList<Entity> entities) {
        this.gridPos = gridPos;
        this.type = type;
        this.entities = entities;
    }


    /**
     * Returns the position of the tile.
     *
     * @return the position of the tile
     */
    public Point getGridPos() {
        return gridPos;
    }

    /**
     * Returns the entities on the tile in an ArrayList.
     *
     * @return the entities on the tile in an ArrayList
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Sets the entities on the tile with an ArrayList.
     *
     * @param entities the entities to set on the tile in an ArrayList
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Adds a new Entity to the tile
     *
     * @param entity a new Entity to add
     */
    public void addEntities(Entity entity) {
        this.entities.add(entity);
    }

    /**
     * Returns the type of the tile.
     *
     * @return the type of the tile
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the speed modifier of the tile.
     *
     * @return the speed modifier of the tile
     */
    public int getSpeedMod() {
        return speedMod;
    }

    /**
     * Converts the tile's state to a String.
     *
     * @return the tile's state as a String
     */
    public String typeToString() {
        return "( " + type + getEntities() + " ) ";
    }

    //remove entity
    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }
}
