import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AssassinTest {
    @Test
    @DisplayName("Assassin Attack")
    void attackTest() {

        Pathfinder.setMap(new Map());
        Assassin assassin = new Assassin(new Point(0, 0));
        ArrayList<Soldier> targets = new ArrayList<>(Arrays.asList(new Soldier(new Point(0, 0)), new Soldier(new Point(1, 1)), new Soldier(new Point(10, 10))));
        assassin.selectTargets(targets);
        assassin.attack();
        assertFalse(targets.get(0).isAlive(), "Soldier(with pos 0,0) gets killed");
        assertFalse(targets.get(1).isAlive(), "Soldier(with pos 1,1) gets killed");
        assertTrue(targets.get(2).isAlive(), "Soldier(with pos 10,10) is alive");
    }

    @Test
    @DisplayName("Assassin construct")
    void constructTest() {
        Pathfinder.setMap(new Map());
        Point point = new Point(2, 3);
        Assassin assassin = new Assassin(point);
        assertEquals(point, assassin.getPosition(), "Position is equal with point");
        assertEquals(6, assassin.getSpeed(), "Speed is equal with speed");
        assertTrue(assassin.isAnimated(), "Assassin is animated");
        assertEquals(10, assassin.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList(Types.PLAINS, Types.DESERT)), assassin.getTerrains(), "Terrain is an ArrayList with Plains and Desert in it");
        assertEquals(5, assassin.getValue(), "Value is 5");
        assertEquals(Types.ASSASSIN, assassin.getType(), "Assassin's type is assassin");

    }
}
