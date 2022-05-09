import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SoliderTest {
    @Test
    @DisplayName("Soldier Attack")
    void attackTest() {
        Pathfinder.setMap(new Map());
        Soldier soldier = new Soldier(new Point(0, 0));
        Castle castleAttacked = new Castle(new Point(0, 0));
        Castle castle = new Castle(new Point(10, 10));
        soldier.selectTarget(castleAttacked);
        soldier.attack();
        assertEquals(castleAttacked.getMaxHealthPoints() - soldier.getDamage(), castleAttacked.getHealthPoints(), "CastleAttacked health point is decreased by soldier's damage");
        assertFalse(soldier.isAlive(), "Soldier is not alive");
        soldier = new Soldier(new Point(0, 0));
        soldier.selectTarget(castle);
        soldier.attack();
        assertEquals(castle.getMaxHealthPoints(), castle.getHealthPoints(), "Castle's health points remains the same");
        assertTrue(soldier.isAlive(), "Soldier is alive");
    }

    @Test
    @DisplayName("Soldier construct")
    void constructTest() {
        Pathfinder.setMap(new Map());
        Point point = new Point(2, 3);
        Soldier soldier = new Soldier(point);
        assertEquals(point, soldier.getPosition(), "Position is equal with point");
        assertEquals(5, soldier.getSpeed(), "Speed is equal with speed");
        assertTrue(soldier.isAnimated(), "Soldier is animated");
        assertEquals(10, soldier.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT)), soldier.getTerrains(), "Terrain is an ArrayList with Plains and Desert in it");
        assertEquals(2, soldier.getValue(), "Value is 2");
        assertEquals(Types.SOLDIER, soldier.getType(), "Soldier's type is soldier");

    }
}
