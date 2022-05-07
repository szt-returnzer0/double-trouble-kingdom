import DTK_model.Animator;
import DTK_model.Map;
import DTK_model.Pathfinder;
import DTK_model.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class AnimatorTest {
    @Test
    @DisplayName("StartAnim StopAnim")
    void animTest() {
        Pathfinder.setMap(new Map());
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        soldier.setSide("left");
        Animator animator = new Animator(soldier);
        animator.startAnimation();
        assertEquals(soldier.getPath(), animator.getPath(), "Animator should get the Path of the soldier");
        assertTrue(soldier.isAnimated(), "Solider should be animated");
        animator.stopAnimation();
        assertFalse(soldier.isAnimated(), "Solider shouldn't be animated");
    }

    @Test
    @DisplayName("Remove Path")
    void removePathTest() {
        Pathfinder.setMap(new Map());
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        soldier.setSide("left");
        Animator animator = new Animator(soldier);
        animator.setPath(soldier.getPath());
        animator.removePath();
        assertNull(animator.getPath(), "Path should be null");
    }
}
