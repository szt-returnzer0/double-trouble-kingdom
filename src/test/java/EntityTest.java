import DTK_model.Castle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityTest {
    @Test
    @DisplayName("Invert Test")
    void invertTest() {
        Castle castle = new Castle(new Point(0, 0));
        int height = castle.getSize().height;
        int width = castle.getSize().width;
        Dimension invertedDimension = new Dimension(height, width);
        castle.invert();
        assertEquals(invertedDimension, castle.getSize(), "The inverted Castle Size should be equal with invertedDimension");
    }

    @Test
    @DisplayName("Take Damage Test")
    void takeDamageTest() {
        Castle castle = new Castle(new Point(0, 0));
        int hp = castle.getHealthPoints();
        int damage = 20;
        castle.takeDamage(damage);
        assertEquals(hp - damage, castle.getHealthPoints(), "The castle's hp  should be reduced by damage");
    }

}
