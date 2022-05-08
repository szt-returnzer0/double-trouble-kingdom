package DTK_model;

import java.awt.*;

/**
 * Implementation of the archerless Barricade Tower type.
 */
public class Barricade extends Tower {

    /**
     * Constructs a new Barricade instance.
     *
     * @param position the barricade's position on the Map
     */
    public Barricade(Point position) {
        super(position);
        this.type = Types.BARRICADE;
        this.value = 10;
        this.size = new Dimension(2, 2);
    }

    /**
     * Attack method for the Barricade.
     */
    @Override
    public void attack() {
        throw new UnsupportedOperationException("Barricades cannot attack.");
    }
}
