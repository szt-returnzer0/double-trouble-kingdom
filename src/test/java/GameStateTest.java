import model.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.FileHandler;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateTest {
    @Test
    @DisplayName("RoundSwitch test")
    void roundSwitchTest() {
        Game game = new Game(null, FileHandler.loadMap(new File("Test.dtk")), null, null);
        assertEquals("Building", game.getGameState().getRoundState(), "The round should be building");
        game.getGameState().nextRoundState();
        assertEquals("Training", game.getGameState().getRoundState(), "The round should be training");
        game.getGameState().nextRoundState();
        assertEquals("Attacking", game.getGameState().getRoundState(), "The round should be attacking");
        game.getGameState().nextRoundState();
        assertEquals(1, game.getGameState().getCurrentPlayer().getPlayerNumber(), "Player 1 should be the current player");
        assertEquals("Building", game.getGameState().getRoundState(), "The round should be building");
    }
}
