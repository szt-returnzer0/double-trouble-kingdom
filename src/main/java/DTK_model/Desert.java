package DTK_model;


import java.util.ArrayList;

/**
 * Implementation of Desert Terrain type.
 */
public class Desert extends Terrain {
    /**
     * Constructs a new Desert instance without predefined entities.
     */
    public Desert() {
        super(Types.DESERT);
        this.speedMod = 2;

    }

    /**
     * Constructs a new Desert instance with predefined entities.
     *
     * @param entities an ArrayList containing the entities
     */
    public Desert(ArrayList<Entity> entities) {
        super(Types.DESERT, entities);
        this.speedMod = 2;
    }
}
