import model.Mountain;
import model.Pathfinder;
import model.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.FileHandler;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TerrainTest {
    @Test
    @DisplayName("Add Entities")
    void addEntityTest() {
        Pathfinder.setMap(FileHandler.loadMap(new File("Test.dtk")));
        Mountain mountain = new Mountain(new Point(0, 0));
        Soldier soldier = new Soldier(new Point(0, 0), 0);
        mountain.addEntities(soldier);
        assertEquals(1, mountain.getEntities().size(), "Mountain's Entity ArrayList size should be 1");
    }
}