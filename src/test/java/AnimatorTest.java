import model.Animator;
import model.Pathfinder;
import model.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.FileHandler;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AnimatorTest {
    @Test
    @DisplayName("StartAnim StopAnim")
    void animTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        soldier.setSide("left");
        Animator animator = new Animator(soldier);
        animator.startanim();
        assertEquals(soldier.getPath(), animator.getPath(), "Animator should get the Path of the soldier");
        assertTrue(soldier.isAnimated(), "Solider should be animated");
        animator.stopanim();
        assertFalse(soldier.isAnimated(), "Solider shouldn't be animated");
    }

    @Test
    @DisplayName("Remove Path")
    void removePathTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        soldier.setSide("left");
        Animator animator = new Animator(soldier);
        animator.setPath(soldier.getPath());
        animator.removePath();
        assertNull(animator.getPath(), "Path should be null");
    }
}
