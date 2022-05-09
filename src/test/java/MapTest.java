import model.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    static class LoadTest {
        @Test
        @DisplayName("LoadMap")
        void loadMapTest() {
            Map map = new Map();
            assertEquals("Alapértelmezett", map.getName(), "Map name is not correct");
            assertEquals(32, map.getTiles().length, "Height of map is not correct");
            assertEquals(64, map.getTiles()[0].length, "Width of map is not correct");
        }
    }
}
