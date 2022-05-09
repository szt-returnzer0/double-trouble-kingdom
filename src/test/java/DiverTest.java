import model.Diver;
import model.Map;
import model.Pathfinder;
import model.Types;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiverTest {
    @Test
    @DisplayName("Diver construct")
    void constructTest() {
        Pathfinder.setMap(new Map());

        Point point = new Point(2, 3);
        Diver diver = new Diver(point);
        assertEquals(point, diver.getPosition(), "Position is equal with point");
        assertEquals(5, diver.getSpeed(), "Speed is equal with speed");
        assertTrue(diver.isAnimated(), "Diver is animated");
        assertEquals(10, diver.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT, Types.SWAMP)), diver.getTerrains(), "Terrain is an ArrayList with Plains, Desert and Swamp in it");
        assertEquals(5, diver.getValue(), "Value is 5");
        assertEquals(Types.DIVER, diver.getType(), "Diver's type is diver");

    }
}
