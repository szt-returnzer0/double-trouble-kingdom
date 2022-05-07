package DTK_model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.Random;

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
@JsonIgnoreProperties("texture")
public abstract class Terrain {
    /**
     * An ArrayList of Entities on the tile.
     */
    protected ArrayList<Entity> entities;
    /**
     * The type of the tile.
     */
    protected Types type;
    /**
     * The speed modifier of the tile.
     */
    protected int speedMod;
    /**
     * The Random class for version generation for the tile.
     */
    private static final Random random = new Random();
    /**
     * The version of the tile.
     */
    protected int tileVersion;

    /**
     * Constructs a new Terrain instance without entities.
     *
     * @param type the type of tile
     */
    protected Terrain(Types type) {
        this.entities = new ArrayList<>();
        this.type = type;
        int randomInt = Terrain.random.nextInt(28);
        this.tileVersion = randomInt > 2 ? 0 : randomInt;
    }


    /**
     * Constructs a new Terrain instance without entities.
     *
     * @param type     the type of tile
     * @param entities an ArrayList containing the entities
     */
    protected Terrain(Types type, ArrayList<Entity> entities) {
        this.type = type;
        this.entities = entities;
        int randomInt = Terrain.random.nextInt(28);
        this.tileVersion = randomInt > 2 ? 0 : randomInt;
    }

    /**
     * Returns the version of the tile.
     *
     * @return the version of the tile
     */
    public int getTileVersion() {
        return tileVersion;
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
    public Types getType() {
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


}
