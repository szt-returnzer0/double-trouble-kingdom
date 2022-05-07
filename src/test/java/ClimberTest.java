import DTK_model.Climber;
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

class ClimberTest {
    @Test
    @DisplayName("Climber construct")
    void constructTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        double speed = 2.0;
        Point point = new Point(2, 3);
        Climber climber = new Climber(point, speed);
        assertEquals(point, climber.getPosition(), "Position is equal with point");
        assertEquals(speed, climber.getSpeed(), "Speed is equal with speed");
        assertTrue(climber.isAnimated(), "Climber is animated");
        assertEquals(10, climber.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList("Plains", "Desert", "Mountain")), climber.getTerrains(), "Terrain is an ArrayList with Plains, Desert and Mountain in it");
        assertEquals(5, climber.getValue(), "Value is 5");
        assertEquals("Climber", climber.getType(), "Climber's type is climber");

    }
}
