import model.Climber;
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

class ClimberTest {
    @Test
    @DisplayName("Climber construct")
    void constructTest() {
        Pathfinder.setMap(new Map());
        Point point = new Point(2, 3);
        Climber climber = new Climber(point);
        assertEquals(point, climber.getPosition(), "Position is equal with point");
        assertEquals(4, climber.getSpeed(), "Speed is equal with speed");
        assertTrue(climber.isAnimated(), "Climber is animated");
        assertEquals(10, climber.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT, Types.MOUNTAIN)), climber.getTerrains(), "Terrain is an ArrayList with Plains, Desert and Mountain in it");
        assertEquals(5, climber.getValue(), "Value is 5");
        assertEquals(Types.CLIMBER, climber.getType(), "Climber's type is climber");

    }
}
