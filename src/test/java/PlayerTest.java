import DTK_model.*;
import DTK_persistence.Database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    @DisplayName("Simulation test")
    void SimulationTest() {
        Game game = new Game(new Database(), new Map(), "test1", "test2");
        Pathfinder.setMap(game.getMap());


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

        game.getGameState().getPlayers().get(0).addEntity(new Soldier(new Point(0, 0)));
        game.getGameState().getPlayers().get(1).addEntity(new Assassin(new Point(0, 0)));

        assertEquals(123, game.getGameState().getPlayers().get(0).getGold(), "Player money is not correct");
        assertEquals(120, game.getGameState().getPlayers().get(1).getGold(), "Player money is not correct");

        assertEquals("test1", game.getGameState().getPlayers().get(0).getName(), "Player name is not correct");
        assertEquals(4, game.getGameState().getPlayers().get(0).getEntities().size(), "Player entities size is not correct");

        assertEquals("test2", game.getGameState().getPlayers().get(1).getName(), "Player name is not correct");
        assertEquals(4, game.getGameState().getPlayers().get(1).getEntities().size(), "Player entities size is not correct");

        assertEquals(game.getGameState().getPlayers().get(0).getEntities().stream().filter(e -> e.getType().equals(Types.CASTLE)).toArray()[0], game.getGameState().getPlayers().get(0).getCastle(), "Castle is not correct");
        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals(Types.CASTLE)).toArray()[0], game.getGameState().getPlayers().get(1).getCastle(), "Castle is not correct");

        game.getGameState().getPlayers().get(0).addEntity(new Sniper(new Point(0, 0)));
        game.getGameState().getPlayers().get(1).addEntity(new Shotgun(new Point(0, 0)));

        assertEquals(1, game.getGameState().getPlayers().get(0).getSoldierCount(), "Soldier count is not correct");
        assertEquals(1, game.getGameState().getPlayers().get(1).getSoldierCount(), "Assassin count is not correct");

        assertEquals(game.getGameState().getPlayers().get(0).getEntities().stream().filter(e -> e.getType().equals(Types.SNIPER)).toArray()[0], game.getGameState().getPlayers().get(0).getTowers().get(0), "Tower is not correct");
        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals(Types.SHOTGUN)).toArray()[0], game.getGameState().getPlayers().get(1).getTowers().get(0), "Tower is not correct");

        assertEquals(game.getGameState().getPlayers().get(0).getEntities().stream().filter(e -> e.getType().equals(Types.SOLDIER)).toArray()[0], game.getGameState().getPlayers().get(0).getSoldiers().get(0), "Soldier is not correct");
        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType().equals(Types.ASSASSIN)).toArray()[0], game.getGameState().getPlayers().get(1).getSoldiers().get(0), "Soldier is not correct");

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

        game.getGameState().getPlayers().get(1).addEntity(game.getGameState().getPlayers().get(1).transformTower(game.getGameState().getPlayers().get(1).getTowers().get(0), Types.SNIPER));

        assertEquals(game.getGameState().getPlayers().get(1).getEntities().stream().filter(e -> e.getType() == Types.SNIPER).toArray()[0],
                game.getGameState().getPlayers().get(1).getTowers().get(1), "Tower is not correct");


    }

    @Test
    @DisplayName("Add Gold Test")
    void addGoldTest() {
        Player player = new Player("Player");
        int startingGold = player.getGold();
        int addedGold = 20;
        player.addGold(addedGold);
        assertEquals(startingGold + addedGold, player.getGold(), "Player gold should be equal with addedGold+startingGold");
    }

    @Test
    @DisplayName("Add Saved Entity Test")
    void addSavedEntityTest() {
        Pathfinder.setMap(new Map());
        Player player = new Player("Player");
        Soldier soldier = new Soldier(new Point(0, 0));
        player.addSavedEntity(soldier);
        assertEquals(1, player.getEntities().size(), "Player Entity Array size should be 1");
    }

    @Test
    @DisplayName("Remove Entity Test")
    void removeEntityTest() {
        Sniper sniper = new Sniper(new Point(0, 0));
        Player player = new Player("Player");
        int startingGold = player.getGold();
        player.addSavedEntity(sniper);
        player.removeEntity(sniper);
        assertEquals(startingGold + sniper.getValue() / 3, player.getGold(), "Player gold should be equal 110");
        assertEquals(0, player.getEntities().size(), "Player Entity Array should be empty");
    }
}
