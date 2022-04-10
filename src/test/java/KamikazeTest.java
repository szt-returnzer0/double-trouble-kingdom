import model.Kamikaze;
import model.Pathfinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.FileHandler;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KamikazeTest {
//    @Test
//    @DisplayName("Kamikaze Attack")
//    void attackTest() {
//
//        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
//        Kamikaze kamikaze = new Kamikaze(new Point(0, 0), 0);
//        Barricade barricadeAttacked = new Barricade(new Point(0, 0), null);
//        Barricade barricade = new Barricade(new Point(20, 20), null);
//        kamikaze.selectTargets(new ArrayList<>(Arrays.asList(barricadeAttacked, barricade)));
//        kamikaze.setSplashPercent(0);
//        kamikaze.attack();
//        assertEquals(barricadeAttacked.getMaxHealthPoints(), barricadeAttacked.getHealthPoints(), "BarricadeAttacked health points is the same");
//        assertEquals(barricade.getMaxHealthPoints(), barricade.getHealthPoints(), "Barricade health points is the same");
//        assertTrue(kamikaze.isAlive(), "Kamikaze is alive");
//        kamikaze.attack();
//        assertEquals(barricadeAttacked.getMaxHealthPoints() - kamikaze.getDamage(), barricadeAttacked.getHealthPoints(), "BarricadeAttacked health point is decreased by kamikaze's damage");
//        assertEquals(barricade.getMaxHealthPoints(), barricade.getHealthPoints(), "Barricade health points is the same");
//        assertFalse(kamikaze.isAlive(), "Kamikaze is not alive");
//    }

    @Test
    @DisplayName("Kamikaze construct")
    void constructTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        double speed = 2.0;
        Point point = new Point(2, 3);
        Kamikaze kamikaze = new Kamikaze(point, speed);
        assertEquals(point, kamikaze.getPosition(), "Position is equal with point");
        assertEquals(speed, kamikaze.getSpeed(), "Speed is equal with speed");
        assertTrue(kamikaze.isAnimated(), "Kamikaze is animated");
        assertEquals(10, kamikaze.getHealthPoints(), "HealthPoints is 10 ");
        assertEquals(new ArrayList<>(Arrays.asList("Plains", "Desert")), kamikaze.getTerrains(), "Terrain is an ArrayList with Plains and Desert in it");
        assertEquals(5, kamikaze.getValue(), "Value is 5");
        assertEquals("Kamikaze", kamikaze.getType(), "Kamikaze's type is kamikaze");

    }
}
