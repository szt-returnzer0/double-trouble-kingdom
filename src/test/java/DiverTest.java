import DTK_model.Diver;
import DTK_model.Pathfinder;
import DTK_persistence.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiverTest {
    @Test
    @DisplayName("Diver construct")
    void constructTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        double speed = 2.0;
        Point point = new Point(2, 3);
        Diver diver = new Diver(point, speed);
        assertEquals(point, diver.getPosition(), "Position is equal with point");
        assertEquals(speed, diver.getSpeed(), "Speed is equal with speed");
        assertTrue(diver.isAnimated(), "Diver is animated");
        assertEquals(10, diver.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList("Plains", "Desert", "Swamp")), diver.getTerrains(), "Terrain is an ArrayList with Plains, Desert and Swamp in it");
        assertEquals(5, diver.getValue(), "Value is 5");
        assertEquals("Diver", diver.getType(), "Diver's type is diver");

    }
}
