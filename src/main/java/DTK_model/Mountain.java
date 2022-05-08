package DTK_model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implementation of Mountain Terrain type.
 */
public class Mountain extends Terrain implements Serializable {
    /**
     * Constructs a new Mountain instance without predefined entities.
     */
    public Mountain() {
        super(Types.MOUNTAIN);
        this.speedMod = 3;
    }

    /**
     * Constructs a new Mountain instance with predefined entities.
     *
     * @param entities an ArrayList containing the entities
     */
    public Mountain(ArrayList<Entity> entities) {
        super(Types.MOUNTAIN, entities);
        this.speedMod = 3;
    }
}
