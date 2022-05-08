import DTK_model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldModelTest {
    @Test
    @DisplayName("Is Attack Animated Test")
    void isAttackAnimatedTest() {
        Map map = new Map();
        Game game = new Game(map);
        GameFieldModel gameFieldModel = new GameFieldModel(game);
        Sniper sniper = new Sniper(new Point(0, 0), null);
        assertFalse(GameFieldModel.isAttackAnimated(sniper), "isAttackAnimated should be false when there's no targets");
        Soldier soldier = new Soldier(new Point(0, 0));
        ArrayList<Soldier> targets = new ArrayList<>();
        targets.add(soldier);
        sniper.selectTargets(targets);
        assertTrue(GameFieldModel.isAttackAnimated(sniper), "isAttackAnimated should be true when there's a target");
    }

    @Test
    @DisplayName("Is in Attack Range Test")
    void isInAttackRangeTest() {
        Map map = new Map();
        Game game = new Game(map);
        GameFieldModel gameFieldModel = new GameFieldModel(game);
        Sniper sniper = new Sniper(new Point(0, 0), null);
        Soldier soldier1 = new Soldier(new Point(0, 0));
        Soldier soldier2 = new Soldier(new Point(8, 0));
        assertTrue(GameFieldModel.isInAttackRange(sniper, soldier1), "The Soldier should be in attack range");
        assertFalse(GameFieldModel.isInAttackRange(sniper, soldier2), "The Soldier shouldn't be in attack range");
    }

    @Test
    @DisplayName("Handle Waypoint Test")
    void HandleWaypointTest() {
        Map map = new Map();
        Game game = new Game(map);
        GameFieldModel gameFieldModel = new GameFieldModel(game);
        Point point = new Point(0, 0);
        Soldier soldier = new Soldier(point);
        gameFieldModel.handleWayPoint(0, 0, soldier);
        assertTrue(GameFieldModel.getWayPoints().contains(point), "The waypoint should contain the Point");
        gameFieldModel.handleWayPoint(0, 0, soldier);
        assertFalse(GameFieldModel.getWayPoints().contains(point), "The waypoint shouldn't contain the Point");
    }

    @Test
    @DisplayName("Delete,Delete Building and Place Entity Test")
    void deleteTest() {
        Map map = new Map();
        Game game = new Game(map);
        int x = 2;
        int y = 3;
        GameFieldModel gameFieldModel = new GameFieldModel(game);
        gameFieldModel.placeEntity(x, y, false, Types.SNIPER);
        assertEquals(1, Game.getMapReference().getTiles()[y][x].getEntities().size(), "The entities size should be 1");
        gameFieldModel.delete(x, y);
        assertEquals(0, game.getMap().getTiles()[y][x].getEntities().size(), "The entities size should be 0");
        Sniper sniper = new Sniper(new Point(x, y), null);
        gameFieldModel.deleteBuilding(sniper);
        assertEquals(0, game.getMap().getTiles()[y][x].getEntities().size(), "The entities size should be 0");
    }

    @Test
    @DisplayName("Is Empty Test")
    void isEmptyTest() {
        Map map = new Map();
        Game game = new Game(map);
        GameFieldModel gameFieldModel = new GameFieldModel(game);
        assertTrue(gameFieldModel.isEmpty(0, 0, new Dimension(2, 3)), "The place should be empty");
    }


}
