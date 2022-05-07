import DTK_model.*;
import DTK_persistence.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {
    @Test
    @DisplayName("Tower construct")
    void constructTest() {
        Point point = new Point(5, 2);
        Barricade barricade = new Barricade(point, null);
        assertEquals(20, barricade.getHealthPoints(), "Health point is 20");
        assertEquals("Barricade", barricade.getType(), "Barricade's type is barricade");
        assertEquals(10, barricade.getValue(), "Value is 10");
        assertEquals(0, barricade.getRange(), "Barricade's range is 0");
        assertEquals(0, barricade.getDamage(), "Barricade's damage is 0");
        assertFalse(barricade.isCanAttack(), "Barricade can't attack");

        Sniper sniper = new Sniper(point, null);
        assertEquals(20, sniper.getHealthPoints(), "Health point is 20");
        assertEquals("Sniper", sniper.getType(), "Sniper's type is sniper");
        assertEquals(30, sniper.getValue(), "Value is 30");
        assertEquals(7, sniper.getRange(), "Sniper's range is 7");
        assertEquals(3, sniper.getDamage(), "Sniper's damage is 3");
        assertTrue(sniper.isCanAttack(), "Sniper can attack");

        Shotgun shotgun = new Shotgun(point, null);
        assertEquals(20, shotgun.getHealthPoints(), "Health point is 20");
        assertEquals("Shotgun", shotgun.getType(), "Shotgun's type is shotgun");
        assertEquals(30, shotgun.getValue(), "Value is 30");
        assertEquals(3, shotgun.getRange(), "Shotgun's range is 3");
        assertEquals(5, shotgun.getDamage(), "Shotgun's damage is 5");
        assertTrue(shotgun.isCanAttack(), "Shotgun can attack");
    }

    @Test
    @DisplayName("Sniper attack")
    void sniperAttackTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        Sniper sniper = new Sniper(new Point(0, 0), null);
        Soldier soldier1 = new Soldier(new Point(1, 0), 0);
        Soldier soldier2 = new Soldier(new Point(7, 0), 0);
        Soldier soldier3 = new Soldier(new Point(8, 0), 0);
        ArrayList<Soldier> targets = new ArrayList<>(Arrays.asList(soldier1, soldier2, soldier3));
        sniper.selectTargets(targets);
        sniper.attack();
        assertEquals(soldier1.getMaxHealthPoints() - sniper.getDamage(), soldier1.getHealthPoints(), "Soldier1 health point decreased by Sniper tower's damage");
        assertEquals(soldier2.getMaxHealthPoints(), soldier2.getHealthPoints(), "Soldier2 health point is the same");
        assertEquals(soldier3.getMaxHealthPoints(), soldier3.getHealthPoints(), "Soldier3 health point is the same");
    }

    @Test
    @DisplayName("Shotgun attack")
    void shotgunAttackTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        Shotgun shotgun = new Shotgun(new Point(0, 0), null);
        Soldier soldier1 = new Soldier(new Point(1, 0), 0);
        Soldier soldier2 = new Soldier(new Point(3, 0), 0);
        Soldier soldier3 = new Soldier(new Point(4, 0), 0);
        ArrayList<Soldier> targets = new ArrayList<>(Arrays.asList(soldier1, soldier2, soldier3));
        shotgun.selectTargets(targets);
        shotgun.attack();
        assertEquals(soldier1.getMaxHealthPoints() - shotgun.getDamage(), soldier1.getHealthPoints(), "Soldier1 health point decreased by Shotgun tower's damage");
        assertEquals(soldier2.getMaxHealthPoints() - shotgun.getDamage(), soldier2.getHealthPoints(), "Soldier2 health point decreased by Shotgun tower's damage");
        assertEquals(soldier3.getMaxHealthPoints(), soldier3.getHealthPoints(), "Soldier3 health point is the same");
    }

    @Test
    @DisplayName("Tower Upgrade")
    void towerUpgradeTest() {
        Barricade barricadeToSniper = new Barricade(new Point(0, 0), null);
        Barricade barricadeToShotgun = new Barricade(new Point(0, 0), null);
        Tower sniper = barricadeToSniper.transform("Sniper");
        assertEquals("Sniper", sniper.getType(), "Sniper's type is sniper");
        assertEquals(30, sniper.getValue(), "Value is 30");
        assertEquals(7, sniper.getRange(), "Sniper's range is 7");
        assertEquals(3, sniper.getDamage(), "Sniper's damage is 3");
        assertTrue(sniper.isCanAttack(), "Sniper can attack");
        sniper.upgrade();
        assertEquals(5, sniper.getDamage(), "Sniper's damage is 5");
        sniper.upgrade();
        assertEquals(2, sniper.getAttackSpeed(), "Sniper's attack speed is 2");
        sniper.upgrade();
        assertEquals(30, sniper.getHealthPoints(), "Health point is 30");
        assertEquals(30, sniper.getMaxHealthPoints(), "Max health point is 30");

        Tower shotgun = barricadeToShotgun.transform("Shotgun");
        assertEquals("Shotgun", shotgun.getType(), "Shotgun's type is shotgun");
        assertEquals(30, shotgun.getValue(), "Value is 30");
        assertEquals(3, shotgun.getRange(), "Shotgun's range is 3");
        assertEquals(5, shotgun.getDamage(), "Shotgun's damage is 5");
        assertTrue(shotgun.isCanAttack(), "Shotgun can attack");
        shotgun.upgrade();
        assertEquals(7, shotgun.getDamage(), "Shotgun's damage is 7");
        shotgun.upgrade();
        assertEquals(2, shotgun.getAttackSpeed(), "Shotgun's attack speed is 2");
        shotgun.upgrade();
        assertEquals(30, shotgun.getHealthPoints(), "Health point is 30");
        assertEquals(30, shotgun.getMaxHealthPoints(), "Max health point is 30");
    }

    @Test
    @DisplayName("Take Damage")
    void takeDamageTest() {
        Barricade barricade = new Barricade(new Point(0, 0), null);
        int damage = 20;
        barricade.takeDamage(damage);
        assertEquals(barricade.getMaxHealthPoints() - damage, barricade.getHealthPoints(), "Barricade' hp should be reduced by damage");


    }
}
