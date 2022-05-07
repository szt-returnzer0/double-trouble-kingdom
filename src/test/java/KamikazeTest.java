import DTK_model.Kamikaze;
import DTK_model.Map;
import DTK_model.Pathfinder;
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
        double speed = 2.0;
        Point point = new Point(2, 3);
        Kamikaze kamikaze = new Kamikaze(point);
        assertEquals(point, kamikaze.getPosition(), "Position is equal with point");
        assertEquals(speed, kamikaze.getSpeed(), "Speed is equal with speed");
        assertTrue(kamikaze.isAnimated(), "Kamikaze is animated");
        assertEquals(10, kamikaze.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList("Plains", "Desert")), kamikaze.getTerrains(), "Terrain is an ArrayList with Plains and Desert in it");
        assertEquals(5, kamikaze.getValue(), "Value is 5");
        assertEquals("Kamikaze", kamikaze.getType(), "Kamikaze's type is kamikaze");

    }
}
