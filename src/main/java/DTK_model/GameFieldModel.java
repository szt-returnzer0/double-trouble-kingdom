package DTK_model;

import java.awt.*;
import java.util.Map;
import java.util.*;

/**
 * Implementation of the GameField's logic.
 */
public class GameFieldModel {

    /**
     * The priceList of Units.
     */
    private static final Map<String, String> priceList = new HashMap<>();

    /**
     * The list of waypoints.
     */
    private static final ArrayList<Point> wayPoints = new ArrayList<>();

    /**
     * Game reference
     */
    private final Game game;

    /**
     * Thw vertical size of the game field.
     */
    private final int xLength;

    /**
     * The horizontal size of the game field.
     */
    private final int yLength;

    /**
     * Constructs a new GameFieldModel.
     *
     * @param game the game reference
     */
    public GameFieldModel(Game game) {
        genPriceList();
        this.game = game;
        xLength = Game.getMapReference().getTiles()[0].length;
        yLength = Game.getMapReference().getTiles().length;
    }

    /**
     * Returns the price of a Unit.
     *
     * @param unit the Unit we query the price of
     * @return the price of the Unit
     */
    public static String getPrice(String unit) {
        return priceList.get(unit);
    }

    /**
     * Checks if Tower attack is animated.
     *
     * @param tower the Tower we query
     * @return true if Tower is attacking, false otherwise
     */
    public static boolean isAttackAnimated(Tower tower) {
        return tower.getTargets() != null && !tower.getTargets().isEmpty() && tower.isCanAttack() && !tower.isDestroyed();
    }

    /**
     * Checks if Tower has a Solider in attack range.
     *
     * @param tower  the Tower we query
     * @param target the target we query
     * @return true if Tower has a Soldier in attack range, false otherwise
     */
    public static boolean isInAttackRange(Tower tower, Entity target) {
        return tower.getPosition().distance(target.getPosition()) <= tower.getRange() && target.isAlive();
    }

    /**
     * Returns the waypoints.
     *
     * @return the waypoints
     */
    public static ArrayList<Point> getWayPoints() {
        return wayPoints;
    }

    /**
     * Generates the priceList.
     */
    private void genPriceList() {
        priceList.put("Sol", (new Soldier(new Point(0, 0))).getValue() + "g");
        priceList.put("Kam", (new Kamikaze(new Point(0, 0))).getValue() + "g");
        priceList.put("Div", (new Diver(new Point(0, 0))).getValue() + "g");
        priceList.put("Cli", (new Climber(new Point(0, 0))).getValue() + "g");
        priceList.put("Ass", (new Assassin(new Point(0, 0))).getValue() + "g");
        priceList.put("Bar", (new Barricade(new Point(0, 0))).getValue() + "g");
        priceList.put("Sni", (new Sniper(new Point(0, 0))).getValue() + "g");
        priceList.put("Sho", (new Shotgun(new Point(0, 0))).getValue() + "g");
        priceList.put("Brk", (new Barracks(new Point(0, 0))).getValue() + "g");
    }

    /**
     * Handle a waypoint.
     *
     * @param xIdx      x coordinate
     * @param yIdx      y coordinate
     * @param selection selection Entity
     */
    public void handleWayPoint(int xIdx, int yIdx, Entity selection) {
        if (Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().isEmpty()) {
            if (selection instanceof Soldier s && s.getTerrains().contains(Game.getMapReference().getTiles()[yIdx][xIdx].getType())) {
                if (wayPoints.contains(new Point(xIdx, yIdx))) {
                    wayPoints.remove(new Point(xIdx, yIdx));
                } else
                    wayPoints.add(new Point(xIdx, yIdx));
            }
        }
    }

    /**
     * Deletes an entity.
     *
     * @param xIdx x Index
     * @param yIdx y Index
     */
    public void delete(int xIdx, int yIdx) {
        if (!Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().isEmpty()) {
            Building b = (Building) Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0);
            Sides pSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? Sides.BLUE : Sides.RED;
            if (Objects.equals(b.getSide(), pSide) &&
                    !(b instanceof Castle)) {
                deleteBuilding(b);
                game.getGameState().getCurrentPlayer().removeEntity(b);
            }
        }
    }

    /**
     * Places an entity.
     *
     * @param xIdx     x Index
     * @param yIdx     y Index
     * @param inverted if the entity is inverted
     * @param type     the type of the entity
     */
    public void placeEntity(int xIdx, int yIdx, boolean inverted, Types type) {
        if (yIdx < yLength && xIdx < xLength && yIdx >= 0 && xIdx >= 0) {
            if (Types.getBuildingTypes().contains(type)) {
                placeBuilding(Types.buildingFactory(type, xIdx, yIdx), inverted, type);
            } else if (Types.getSoldierTypes().contains(type)) {
                trainSoldiers(Types.soldierFactory(type, xIdx, yIdx), type);
            }

        }

        game.getGameState().setTargets();
    }

    /**
     * Checks if the tile is empty.
     *
     * @param xIdx index of the row
     * @param yIdx index of the column
     * @param size the size of the Entity
     * @return if the tile is empty
     */
    public boolean isEmpty(int xIdx, int yIdx, Dimension size) {
        boolean isEmpty = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isEmpty = isEmpty && Game.getMapReference().getTiles()[y][x].getEntities().isEmpty();
            }
        }
        return isEmpty;
    }

    /**
     * Check if we can build.
     *
     * @param xIdx index of the row
     * @param yIdx index of the column
     * @param size the size of the building
     * @param side the side it belongs to
     * @return if we can build
     */
    private boolean isBuildable(int xIdx, int yIdx, Dimension size, Sides side) {
        boolean isBuildable = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isBuildable = isBuildable && Game.getMapReference().getTiles()[y][x].getEntities().isEmpty()
                        && !Objects.equals(Game.getMapReference().getTiles()[y][x].getType(), Types.MOUNTAIN)
                        && !Objects.equals(Game.getMapReference().getTiles()[y][x].getType(), Types.SWAMP);
            }
        }
        if (!((side.equals(Sides.BLUE) && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1)
                || (side.equals(Sides.RED) && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
            isBuildable = false;
        return isBuildable;
    }

    /**
     * Check if we can train a unit.
     *
     * @param xIdx index of the row
     * @param yIdx index of the column
     * @param s    the unit instance
     * @param side the side it belongs to
     * @return if we can train it
     */
    private boolean isInTrainingGround(int xIdx, int yIdx, Soldier s, Sides side) {
        ArrayList<Entity> entities = Game.getMapReference().getTiles()[yIdx][xIdx].getEntities();
        boolean isInTrainingGround = false;
        for (Entity entity : entities) {


            if ((entity.getType().equals(Types.CASTLE) || entity.getType().equals(Types.BARRACKS))
                    && Objects.equals(s.getType(), Types.SOLDIER)) {
                isInTrainingGround = true;
            }

            if (Objects.equals(entity.getType(), Types.BARRACKS) && !((Barracks) entity).isUpgraded()
                    && Objects.equals(s.getType(), Types.SOLDIER)) {
                isInTrainingGround = true;
            }

            if (Objects.equals(entity.getType(), Types.BARRACKS) && ((Barracks) entity).isUpgraded()) {
                isInTrainingGround = true;
            }
        }
        if (!((side.equals(Sides.BLUE) && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1)
                || (side.equals(Sides.RED) && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
            isInTrainingGround = false;

        return isInTrainingGround;
    }


    /**
     * Determines the closest empty tile.
     *
     * @param xIdx index of the row
     * @param yIdx index of the column
     * @return the location of the closest empty tile
     */
    private Point closestEmptyTile(int xIdx, int yIdx, Types type) {
        Point[] directions = new Point[]{new Point(-1, 0), new Point(0, 1), new Point(1, 0), new Point(0, -1)};
        Integer[] counts = Arrays.stream(directions).map(e -> countTiles(new Point(xIdx, yIdx), e, 0, type))
                .toArray(Integer[]::new);
        int idx = Arrays.asList(counts).indexOf(Arrays.stream(counts).min(Integer::compare).get());
        return new Point(xIdx + directions[idx].x * counts[idx], yIdx + directions[idx].y * counts[idx]);
    }

    /**
     * Counts the tiles from a position to a direction.
     *
     * @param from    the point the count from
     * @param dir     the direction to go
     * @param counter the count of tiles
     * @return the count of tiles
     */
    private int countTiles(Point from, Point dir, int counter, Types type) {
        if (from.x < 0 || from.x > xLength - 1 || from.y < 0 || from.y > yLength - 1) {
            return 10000;
        }
        if (hasNoBuilding(Game.getMapReference().getTiles()[from.y][from.x]) && unitIsPlaceable(Game.getMapReference().getTiles()[from.y][from.x], type))
            return counter;
        return countTiles(new Point(from.x + dir.x, from.y + dir.y), dir, counter + 1, type);
    }

    /**
     * Check if a tile has no building.
     *
     * @param ter the tile to check
     * @return if it has no building
     */
    private boolean hasNoBuilding(Terrain ter) {
        boolean l = true;
        for (Entity entity : ter.getEntities()) {
            l = l && !Types.getBuildingTypes().contains(entity.getType());
        }
        return l;
    }

    /**
     * Checks if a unit is placeable.
     *
     * @param ter the tile to check
     * @return if its placeable
     */
    private boolean unitIsPlaceable(Terrain ter, Types type) {
        boolean result;
        switch (ter.getType()) {
            case SWAMP -> result = Objects.equals(type, Types.DIVER);
            case MOUNTAIN -> result = Objects.equals(type, Types.CLIMBER);
            default -> result = true;
        }
        return result;
    }

    /**
     * Deletes a building from the Map
     *
     * @param b the building to delete
     */
    public void deleteBuilding(Building b) {
        if (b != null) {
            for (int y = b.getPosition().y; y < b.getPosition().y + b.getSize().height; y++) {
                for (int x = b.getPosition().x; x < b.getPosition().x + b.getSize().width; x++) {
                    Game.getMapReference().getTiles()[y][x].getEntities().clear();
                }
            }
        }
    }

    /**
     * Trains soldiers.
     *
     * @param s the soldier to train.
     */
    private void trainSoldiers(Soldier s, Types type) {
        if (game.getGameState().getCurrentPlayer().getGold() >= s.getValue()) {
            s.setOwner(game.getGameState().getCurrentPlayer());
            int xIdx = s.getPosition().x;
            int yIdx = s.getPosition().y;
            Sides side = xIdx <= xLength / 2 ? Sides.BLUE : Sides.RED;

            s.setSide(side);
            if (isInTrainingGround(xIdx, yIdx, s, side)) {
                Point point = closestEmptyTile(xIdx, yIdx, type);
                s.setPosition(point);

                GameState.getAnimBuffer().add(s.getAnimObj());
                s.getAnimObj().setSeconds(1 / s.getSpeed());
                s.getAnimObj().setSpeedModifier(Game.getMapReference().getTiles()[point.y][point.x].getSpeedMod());
                s.setIsAnimated(false);
                wayPoints.forEach(s::addWaypoint);
                wayPoints.clear();
                Game.getMapReference().getTiles()[point.y][point.x].addEntities(s);
                game.getGameState().getCurrentPlayer().addEntity(s);
            }
        }
    }

    /**
     * Places a building.
     *
     * @param b the building to place
     */
    private void placeBuilding(Building b, boolean inverted, Types type) {


        b.setOwner(game.getGameState().getCurrentPlayer());
        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        Sides side = xIdx + b.getSize().width / 2 - 1 <= xLength / 2 ? Sides.BLUE : Sides.RED;
        b.setSide(side);

        if (inverted)
            b.invert();


        if (destroyedOnPos(xIdx, yIdx))
            return;

        Building enemyCastle = game.getGameState().getEnemyCastle(b.getOwner().getPlayerNumber());
        Soldier testUnit = new Soldier(closestEmptyTile(
                enemyCastle.getPosition().x + enemyCastle.getSize().width / 2
                        + (b.getSide().equals(Sides.BLUE) ? 1 : -1),
                enemyCastle.getPosition().y + enemyCastle.getSize().height / 2, type));
        testUnit.setSide(enemyCastle.getSide());
        Pathfinder pf = new Pathfinder();
        Pathfinder.setMap(Game.getMapReference());


        if (pf.Dijkstra(testUnit, enemyCastle.getSide().equals(Sides.RED) ? Sides.BLUE : Sides.RED, b) == null)
            return;


        Sides playerSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? Sides.BLUE : Sides.RED;
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength
                && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {

            if (isBuildable(xIdx, yIdx, b.getSize(), side)) {

                placeOnEmptyField(yIdx, b, xIdx);

                if (game.getGameState().getCurrentPlayer().getGold() >= b.getValue()) {
                    game.getGameState().getCurrentPlayer().addEntity(b);
                }
            } else if (Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList()
                    .contains(b.getType())
                    && Objects.equals(Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                if ((game.getGameState().getCurrentPlayer().getGold() >= 30
                        && Objects.equals(b.getType(), Types.BARRACKS))
                        || (Types.getTowerTypes().contains(b.getType()) && game.getGameState().getCurrentPlayer().getGold() >= (((Tower) b).getLevel() * 5 + 10)
                        && Types.getUpgradeable().contains(b.getType()))) {
                    game.getGameState().getCurrentPlayer()
                            .upgradeBuilding((Building) Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0));
                }

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList()
                    .contains(Types.SHOTGUN)
                    && Objects.equals(b.getType(), Types.SNIPER)
                    && Objects.equals(Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx, inverted, type);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList()
                    .contains(Types.SNIPER)
                    && Objects.equals(b.getType(), Types.SHOTGUN)
                    && Objects.equals(Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx, inverted, type);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList()
                    .contains(Types.BARRICADE)
                    && (Objects.equals(b.getType(), Types.SHOTGUN) || Objects.equals(b.getType(), Types.BARRICADE))
                    && Objects.equals(Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx, inverted, type);
            }
        }

    }


    /**
     * Checks if the building can be placed on the given tile
     *
     * @param yIdx the y-coordinate of the tile
     * @param b    the building to be placed
     * @param xIdx the x-coordinate of the tile
     */
    private void placeOnEmptyField(int yIdx, Building b, int xIdx) {
        for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
            for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                if (game.getGameState().getCurrentPlayer().getGold() >= b.getValue()) {
                    Game.getMapReference().getTiles()[y][x].addEntities(b);
                }
            }
        }
    }


    /**
     * Check if a building is destroyed on the position
     *
     * @param xIdx the x-coordinate of the tile
     * @param yIdx the y-coordinate of the tile
     * @return true if the building is destroyed
     */
    private boolean destroyedOnPos(int xIdx, int yIdx) {
        if (Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().size() > 0) {
            for (Entity e : Game.getMapReference().getTiles()[yIdx][xIdx].getEntities()) {
                if (e instanceof Tower t && t.isDestroyed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Transforms a Tower
     *
     * @param b    the tower to transform
     * @param xIdx index of the row
     * @param yIdx index of the column
     */
    private void transformTower(Building b, int xIdx, int yIdx, boolean inverted, Types type) {

        Building newTower = game.getGameState().getCurrentPlayer()
                .transformTower((Tower) Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0), b.getType());
        deleteBuilding((Building) Game.getMapReference().getTiles()[yIdx][xIdx].getEntities().get(0));
        placeBuilding(newTower, inverted, type);
        //repaint();
    }

}
