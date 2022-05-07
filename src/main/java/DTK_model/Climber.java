package DTK_model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Climber Soldier type.
 */
public class Climber extends Soldier {
    /**
     * Constructs a new Climber instance.
     *
     * @param position the climber's position on the Map
     * @param speed    the climber's current speed
     */
    public Climber(Point position) {
        super(position);
        this.type = Types.CLIMBER;
        this.isAnimated = true;
        this.healthPoints = 10;
        this.maxHealthPoints = this.healthPoints;
        this.size = new Dimension(1, 1);
        this.value = 5;
        this.terrains = new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT, Types.MOUNTAIN));
        this.speed = 1;
    }
}
