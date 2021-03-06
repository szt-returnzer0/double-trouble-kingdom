import model.Barracks;
import model.Castle;
import model.Types;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    @Test
    @DisplayName("Construct Buildings")
    void buildingConstructTest() {
        Point point = new Point(5, 3);
        Barracks barracks = new Barracks(point);
        Castle castle = new Castle(point);
        assertEquals(point, barracks.getPosition(), "Position is equal with point");
        assertEquals(Types.BARRACKS, barracks.getType(), "Barracks' type is barracks");
        assertEquals(50, barracks.getValue(), "Value is 50");
        assertEquals(50, barracks.getHealthPoints(), "Health point is 50 ");
        assertEquals(point, castle.getPosition(), "Position is equal with point");
        assertEquals(Types.CASTLE, castle.getType(), "Castle's type is castle");
        assertEquals(100, castle.getHealthPoints(), "Health point is 100");
    }

    @Test
    @DisplayName("Upgrade Barracks")
    void barracksUpgradeTest() {
        Barracks barracks = new Barracks(new Point(0, 0));
        assertFalse(barracks.isUpgraded(), "Barracks is not upgraded");
        barracks.upgrade();
        assertTrue(barracks.isUpgraded(), "Barracks is upgraded");
    }
}
