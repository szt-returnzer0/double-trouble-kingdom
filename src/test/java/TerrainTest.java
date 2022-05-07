import DTK_model.Map;
import DTK_model.Mountain;
import DTK_model.Pathfinder;
import DTK_model.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TerrainTest {
    @Test
    @DisplayName("Add Entities")
    void addEntityTest() {
        Pathfinder.setMap(new Map());
        Mountain mountain = new Mountain();
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        mountain.addEntities(soldier);
        assertEquals(1, mountain.getEntities().size(), "Mountain's Entity ArrayList size should be 1");
    }
}
