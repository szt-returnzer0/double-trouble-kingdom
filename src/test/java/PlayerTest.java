import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.FileHandler;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    @DisplayName("Construct test")
    void ConstructTest() {
        Game game = new Game(null, FileHandler.loadMap(new File("Test.dtk")), "test1", "test2");
        Pathfinder.setMap(game.getMap());

        game.getGameState().loadBuildings(game.getMap());

        assertEquals("test1", game.getGameState().getPlayers().get(0).getName(), "Player name is not correct");
        assertEquals(3, game.getGameState().getPlayers().get(0).getEntities().size(), "Player entities size is not correct");

        assertEquals("test2", game.getGameState().getPlayers().get(1).getName(), "Player name is not correct");
        assertEquals(3, game.getGameState().getPlayers().get(1).getEntities().size(), "Player entities size is not correct");

        assertEquals(100, game.getGameState().getPlayers().get(0).getGold(), "Player money is not correct");
        assertEquals(100, game.getGameState().getPlayers().get(1).getGold(), "Player money is not correct");

        game.getGameState().getPlayers().get(0).calculateGoldAtRound();
        game.getGameState().getPlayers().get(1).calculateGoldAtRound();

        assertEquals(125, game.getGameState().getPlayers().get(0).getGold(), "Player money is not correct");
        assertEquals(125, game.getGameState().getPlayers().get(1).getGold(), "Player money is not correct");

        game.getGameState().getPlayers().get(0).addEntity(new Soldier(new Point(0, 0), 0));
        game.getGameState().getPlayers().get(1).addEntity(new Assassin(new Point(0, 0), 0));

        assertEquals(123, game.getGameState().getPlayers().get(0).getGold(), "Player money is not correct");
        assertEquals(120, game.getGameState().getPlayers().get(1).getGold(), "Player money is not correct");

        assertEquals("test1", game.getGameState().getPlayers().get(0).getName(), "Player name is not correct");
        assertEquals(4, game.getGameState().getPlayers().get(0).getEntities().size(), "Player entities size is not correct");

        assertEquals("test2", game.getGameState().getPlayers().get(1).getName(), "Player name is not correct");
        assertEquals(4, game.getGameState().getPlayers().get(1).getEntities().size(), "Player entities size is not correct");

        assertEquals(game.getGameState().getPlayers().get(0).getEntities().stream().filter(e -> e.getType().equals("Castle")).toArray()[0], game.getGameState().getPlayers().get(0).getCastle(), "Castle is not correct");
        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals("Castle")).toArray()[0], game.getGameState().getPlayers().get(1).getCastle(), "Castle is not correct");

        game.getGameState().getPlayers().get(0).addEntity(new Sniper(new Point(0, 0), null));
        game.getGameState().getPlayers().get(1).addEntity(new Shotgun(new Point(0, 0), null));

        assertEquals(1, game.getGameState().getPlayers().get(0).getSoldierCount(), "Soldier count is not correct");
        assertEquals(1, game.getGameState().getPlayers().get(1).getSoldierCount(), "Assassin count is not correct");

        assertEquals(game.getGameState().getPlayers().get(0).getEntities().stream().filter(e -> e.getType().equals("Sniper")).toArray()[0], game.getGameState().getPlayers().get(0).getTowers().get(0), "Tower is not correct");
        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals("Shotgun")).toArray()[0], game.getGameState().getPlayers().get(1).getTowers().get(0), "Tower is not correct");

        assertEquals(game.getGameState().getPlayers().get(0).getEntities().stream().filter(e -> e.getType().equals("Soldier")).toArray()[0], game.getGameState().getPlayers().get(0).getSoldiers().get(0), "Soldier is not correct");
        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals("Assassin")).toArray()[0], game.getGameState().getPlayers().get(1).getSoldiers().get(0), "Soldier is not correct");

        game.getGameState().getPlayers().get(0).removeSoldier(game.getGameState().getPlayers().get(0).getSoldiers().get(0));
        game.getGameState().getPlayers().get(1).removeSoldier(game.getGameState().getPlayers().get(1).getSoldiers().get(0));

        assertTrue(game.getGameState().getPlayers().get(0).getSoldiers().isEmpty(), "Soldier is not correct");
        assertTrue(game.getGameState().getPlayers().get(1).getSoldiers().isEmpty(), "Soldier is not correct");

        game.getGameState().getPlayers().get(0).upgradeBuilding(game.getGameState().getPlayers().get(0).getTowers().get(0));

        assertEquals(1, game.getGameState().getPlayers().get(0).getTowers().get(0).getLevel(), "Building upgrade is not correct");

        game.getGameState().getPlayers().get(0).upgradeBuilding(game.getGameState().getPlayers().get(0).getTowers().get(0));
        game.getGameState().getPlayers().get(0).upgradeBuilding(game.getGameState().getPlayers().get(0).getTowers().get(0));

        assertEquals(3, game.getGameState().getPlayers().get(0).getTowers().get(0).getLevel(), "Building upgrade is not correct");

        game.getGameState().getPlayers().get(0).upgradeBuilding(game.getGameState().getPlayers().get(0).getTowers().get(0));

        assertEquals(3, game.getGameState().getPlayers().get(0).getTowers().get(0).getLevel(), "Building upgrade is not correct");

        //revise if return val change
        game.getGameState().getPlayers().get(1).addEntity(game.getGameState().getPlayers().get(1).transformTower(game.getGameState().getPlayers().get(1).getTowers().get(0), "Sniper"));

        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals("Sniper")).toArray()[0],
                game.getGameState().getPlayers().get(1).getTowers().get(1), "Tower is not correct");


    }
}