package DTK_model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implementation of Mountain Terrain type.
 */
public class Plains extends Terrain implements Serializable {
    /**
     * Constructs a new Plains instance without predefined entities.
     */
    public Plains() {
        super(Types.PLAINS);
        this.speedMod = 1;
    }

    /**
     * Constructs a new Plains instance with predefined entities.
     *
     * @param entities an ArrayList containing the entities
     */
    public Plains(ArrayList<Entity> entities) {
        super(Types.PLAINS, entities);
        speedMod = 1;
    }
}
