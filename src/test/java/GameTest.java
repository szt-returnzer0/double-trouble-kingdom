import DTK_model.Game;
import DTK_persistence.Database;
import DTK_persistence.FileHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    @DisplayName("Database test")
    void dbTest() {
        Game gameNoDb = new Game(null, FileHandler.loadMap(new File("Test.dtk")), null, null);
        assertNull(gameNoDb.getDatabase(), "Database is not null");
        Game gameDb = new Game(new Database(), FileHandler.loadMap(new File("Test.dtk")), null, null);
        assertNotEquals(null, gameDb.getDatabase(), "Database is null");
    }

    @Test
    @DisplayName("Player name test")
    void playerNameTest() {
        Game game = new Game(null, FileHandler.loadMap(new File("Test.dtk")), "P1", "P2");
        assertEquals("P1", game.getGameState().getPlayers().get(0).getName(), "Name not P1");
        assertEquals("P2", game.getGameState().getPlayers().get(1).getName(), "Name not P2");
    }

    @Test
    @DisplayName("Map test")
    void mapTest() {
        Game game = new Game(null, FileHandler.loadMap(new File("Test.dtk")), "P1", "P2");
        assertEquals("Test", game.getMap().getName(), "Map name is not correct");
        assertEquals(32, game.getMap().getTiles().length, "Height of map is not correct");
        assertEquals(64, game.getMap().getTiles()[0].length, "Width of map is not correct");
    }

    @Test
    @DisplayName("Pause/Resume/End test")
    void pauseResumeEndTest() throws InterruptedException {
        Game game = new Game(null, FileHandler.loadMap(new File("Test.dtk")), "P1", "P2");
        game.pauseGame();
        assertEquals(0, game.getGameState().getElapsedTime(), "Time is not 0");
        Thread.sleep(2000);
        game.restartGame();
        assertEquals(0, game.getGameState().getElapsedTime(), "Time is not 0");
        assertFalse(game.getGameState().isEnded(), "isEnded is not false");
    }

}
