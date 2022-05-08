package DTK_model;

import java.io.Serializable;

/**
 * This class is used to store the sides of the DTK model.
 */
public enum Sides implements Serializable  {
    /**
     * The left side of the Map.
     */
    BLUE(0),

    /**
     * The right side of the Map.
     */
    RED(1),

    /**
     * If we search the path to a waypoint.
     */
    WAYPOINT(2);

    /**
     * The id of the side.
     */
    public final int id;

    /**
     * Constructor for the sides.
     * @param id The id of the side.
     */
    Sides(int id) {
        this.id = id;

    }

}
