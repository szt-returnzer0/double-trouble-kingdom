import model.Kamikaze;
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

class KamikazeTest {


    @Test
    @DisplayName("Kamikaze construct")
    void constructTest() {
        Pathfinder.setMap(new Map());
        Point point = new Point(2, 3);
        Kamikaze kamikaze = new Kamikaze(point);
        assertEquals(point, kamikaze.getPosition(), "Position is equal with point");
        assertEquals(4, kamikaze.getSpeed(), "Speed is equal with speed");
        assertTrue(kamikaze.isAnimated(), "Kamikaze is animated");
        assertEquals(10, kamikaze.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT)), kamikaze.getTerrains(), "Terrain is an ArrayList with Plains and Desert in it");
        assertEquals(5, kamikaze.getValue(), "Value is 5");
        assertEquals(Types.KAMIKAZE, kamikaze.getType(), "Kamikaze's type is kamikaze");

    }
}
