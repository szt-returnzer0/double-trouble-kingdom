package model;

import java.awt.*;

public class Barricade extends Tower {
    public Barricade(Point position, String side) {
        super(position, side);
        this.type = "Barricade"; //Lerakas!!!!
        this.value = 10;
        this.size = new Dimension(2, 2);
    }


}
