package DTK_model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Diver Soldier type.
 */
public class Diver extends Soldier implements Serializable {
    /**
     * Constructs a new Diver instance
     *
     * @param position the diver's position on the Map
     */
    public Diver(Point position) {
        super(position);
        this.type = Types.DIVER;
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.terrains = new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT, Types.SWAMP));
        this.speed = 3;
    }
}
