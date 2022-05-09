import DTK_model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapEditorModelTest {
    @Test
    @DisplayName("Place Block Test")
    void placeBlockTest() {
        Map map = new Map();
        Game game = new Game(map);
        MapEditorModel mapEditorModel = new MapEditorModel();
        int x = 0;
        int y = 0;
        GameFieldModel gameFieldModel = new GameFieldModel(game);
        boolean inverted = false;
        mapEditorModel.placeBlock(x, y, gameFieldModel, inverted, Types.SNIPER, game);
        assertEquals(1, Game.getMapReference().getTiles()[0][0].getEntities().size(), "Entities count should be 1");
        mapEditorModel.placeBlock(x, y, gameFieldModel, inverted, Types.MOUNTAIN, game);
        assertEquals(Types.MOUNTAIN, Game.getMapReference().getTiles()[0][0].getType(), "Type should be Mountain");
    }
}
