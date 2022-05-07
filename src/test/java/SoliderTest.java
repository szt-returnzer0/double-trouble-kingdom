import DTK_model.Castle;
import DTK_model.Pathfinder;
import DTK_model.Soldier;
import DTK_persistence.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SoliderTest {
    @Test
    @DisplayName("Soldier Attack")
    void attackTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        Castle castleAttacked = new Castle(new Point(0, 0), null);
        Castle castle = new Castle(new Point(10, 10), null);
        soldier.selectTarget(castleAttacked);
        soldier.attack();
        assertEquals(castleAttacked.getMaxHealthPoints() - soldier.getDamage(), castleAttacked.getHealthPoints(), "CastleAttacked health point is decreased by soldier's damage");
        assertFalse(soldier.isAlive(), "Soldier is not alive");
        soldier = new Soldier(new Point(0, 0), 0);
        soldier.selectTarget(castle);
        soldier.attack();
        assertEquals(castle.getMaxHealthPoints(), castle.getHealthPoints(), "Castle's health points remains the same");
        assertTrue(soldier.isAlive(), "Soldier is alive");
    }

    @Test
    @DisplayName("Soldier construct")
    void constructTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        double speed = 2.0;
        Point point = new Point(2, 3);
        Soldier soldier = new Soldier(point, speed);
        assertEquals(point, soldier.getPosition(), "Position is equal with point");
        assertEquals(speed, soldier.getSpeed(), "Speed is equal with speed");
        assertTrue(soldier.isAnimated(), "Soldier is animated");
        assertEquals(10, soldier.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList("Plains", "Desert")), soldier.getTerrains(), "Terrain is an ArrayList with Plains and Desert in it");
        assertEquals(2, soldier.getValue(), "Value is 2");
        assertEquals("Soldier", soldier.getType(), "Soldier's type is soldier");

    }
}
