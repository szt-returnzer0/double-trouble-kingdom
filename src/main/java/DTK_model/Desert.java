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
        super("Desert");
        this.speedMod = 2;

    }

    /**
     * Constructs a new Desert instance with predefined entities.
     *
     * @param ent an ArrayList containing the entities
     */
    public Desert(ArrayList<Entity> ent) {
        super("Desert", ent);
        this.speedMod = 2;
    }
}