package model;

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
     * @param xIndex    x coordinate
     * @param yIndex    y coordinate
     * @param selection selection Entity
     */
    public void handleWayPoint(int xIndex, int yIndex, Entity selection) {
        if (Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().isEmpty()) {
            if (selection instanceof Soldier soldier && soldier.getTerrains().contains(Game.getMapReference().getTiles()[yIndex][xIndex].getType())) {
                if (wayPoints.contains(new Point(xIndex, yIndex))) {
                    wayPoints.remove(new Point(xIndex, yIndex));
                } else
                    wayPoints.add(new Point(xIndex, yIndex));
            }
        }
    }

    /**
     * Deletes an entity.
     *
     * @param xIndex x Index
     * @param yIndex y Index
     */
    public void delete(int xIndex, int yIndex) {
        if (!Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().isEmpty()) {
            Building building = (Building) Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0);
            Sides pSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? Sides.BLUE : Sides.RED;
            if (Objects.equals(building.getSide(), pSide) &&
                    !(building instanceof Castle)) {
                deleteBuilding(building);
                game.getGameState().getCurrentPlayer().removeEntity(building);
            }
        }
    }

    /**
     * Places an entity.
     *
     * @param xIndex   x Index
     * @param yIndex   y Index
     * @param inverted if the entity is inverted
     * @param type     the type of the entity
     */
    public void placeEntity(int xIndex, int yIndex, boolean inverted, Types type) {
        if (yIndex < yLength && xIndex < xLength && yIndex >= 0 && xIndex >= 0) {
            if (Types.getBuildingTypes().contains(type)) {
                placeBuilding(Types.buildingFactory(type, xIndex, yIndex), inverted, type);
            } else if (Types.getSoldierTypes().contains(type)) {
                trainSoldiers(Types.soldierFactory(type, xIndex, yIndex), type);
            }

        }

        game.getGameState().setTargets();
    }

    /**
     * Checks if the tile is empty.
     *
     * @param xIndex index of the row
     * @param yIndex index of the column
     * @param size the size of the Entity
     * @return if the tile is empty
     */
    public boolean isEmpty(int xIndex, int yIndex, Dimension size) {
        boolean isEmpty = true;
        for (int y = yIndex; y < yIndex + size.height; y++) {
            for (int x = xIndex; x < xIndex + size.width; x++) {
                isEmpty = isEmpty && Game.getMapReference().getTiles()[y][x].getEntities().isEmpty();
            }
        }
        return isEmpty;
    }

    /**
     * Check if we can build.
     *
     * @param xIndex index of the row
     * @param yIndex index of the column
     * @param size the size of the building
     * @param side the side it belongs to
     * @return if we can build
     */
    private boolean isBuildable(int xIndex, int yIndex, Dimension size, Sides side) {
        boolean isBuildable = true;
        for (int y = yIndex; y < yIndex + size.height; y++) {
            for (int x = xIndex; x < xIndex + size.width; x++) {
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
     * @param xIndex index of the row
     * @param yIndex index of the column
     * @param soldier    the unit instance
     * @param side the side it belongs to
     * @return if we can train it
     */
    private boolean isInTrainingGround(int xIndex, int yIndex, Soldier soldier, Sides side) {
        ArrayList<Entity> entities = Game.getMapReference().getTiles()[yIndex][xIndex].getEntities();
        boolean isInTrainingGround = false;
        for (Entity entity : entities) {


            if ((entity.getType().equals(Types.CASTLE) || entity.getType().equals(Types.BARRACKS))
                    && Objects.equals(soldier.getType(), Types.SOLDIER)) {
                isInTrainingGround = true;
            }

            if (Objects.equals(entity.getType(), Types.BARRACKS) && !((Barracks) entity).isUpgraded()
                    && Objects.equals(soldier.getType(), Types.SOLDIER)) {
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
     * @param xIndex index of the row
     * @param yIndex index of the column
     * @return the location of the closest empty tile
     */
    private Point closestEmptyTile(int xIndex, int yIndex, Types type) {
        Point[] directions = new Point[]{new Point(-1, 0), new Point(0, 1), new Point(1, 0), new Point(0, -1)};
        Integer[] counts = Arrays.stream(directions).map(e -> countTiles(new Point(xIndex, yIndex), e, 0, type))
                .toArray(Integer[]::new);
        int idx = Arrays.asList(counts).indexOf(Arrays.stream(counts).min(Integer::compare).get());
        return new Point(xIndex + directions[idx].x * counts[idx], yIndex + directions[idx].y * counts[idx]);
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
     * @param building the building to delete
     */
    public void deleteBuilding(Building building) {
        if (building != null) {
            for (int y = building.getPosition().y; y < building.getPosition().y + building.getSize().height; y++) {
                for (int x = building.getPosition().x; x < building.getPosition().x + building.getSize().width; x++) {
                    Game.getMapReference().getTiles()[y][x].getEntities().clear();
                }
            }
        }
    }

    /**
     * Trains soldiers.
     *
     * @param soldier the soldier to train.
     */
    private void trainSoldiers(Soldier soldier, Types type) {
        if (game.getGameState().getCurrentPlayer().getGold() >= soldier.getValue()) {
            soldier.setOwner(game.getGameState().getCurrentPlayer());
            int xIndex = soldier.getPosition().x;
            int yIndex = soldier.getPosition().y;
            Sides side = xIndex <= xLength / 2 ? Sides.BLUE : Sides.RED;

            soldier.setSide(side);
            if (isInTrainingGround(xIndex, yIndex, soldier, side)) {
                Point point = closestEmptyTile(xIndex, yIndex, type);
                soldier.setPosition(point);

                GameState.getAnimBuffer().add(soldier.getAnimObj());
                soldier.getAnimObj().setSeconds(1 / soldier.getSpeed());
                soldier.getAnimObj().setSpeedModifier(Game.getMapReference().getTiles()[point.y][point.x].getSpeedMod());
                soldier.setIsAnimated(false);
                wayPoints.forEach(soldier::addWaypoint);
                wayPoints.clear();
                Game.getMapReference().getTiles()[point.y][point.x].addEntities(soldier);
                game.getGameState().getCurrentPlayer().addEntity(soldier);
            }
        }
    }

    /**
     * Places a building.
     *
     * @param building the building to place
     */
    private void placeBuilding(Building building, boolean inverted, Types type) {


        building.setOwner(game.getGameState().getCurrentPlayer());
        int xIndex = building.getPosition().x;
        int yIndex = building.getPosition().y;
        Sides side = xIndex + building.getSize().width / 2 - 1 <= xLength / 2 ? Sides.BLUE : Sides.RED;
        building.setSide(side);

        if (inverted)
            building.invert();


        if (destroyedOnPosition(xIndex, yIndex))
            return;

        Building enemyCastle = game.getGameState().getEnemyCastle(building.getOwner().getPlayerNumber());
        Soldier testUnit = new Soldier(closestEmptyTile(
                enemyCastle.getPosition().x + enemyCastle.getSize().width / 2
                        + (building.getSide().equals(Sides.BLUE) ? 1 : -1),
                enemyCastle.getPosition().y + enemyCastle.getSize().height / 2, type));
        testUnit.setSide(enemyCastle.getSide());
        Pathfinder pathfinder = new Pathfinder();
        Pathfinder.setMap(Game.getMapReference());


        if (pathfinder.Dijkstra(testUnit, enemyCastle.getSide().equals(Sides.RED) ? Sides.BLUE : Sides.RED, building) == null)
            return;


        Sides playerSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? Sides.BLUE : Sides.RED;
        if (xIndex + building.getSize().width <= xLength && yIndex + building.getSize().height <= yLength
                && !(xIndex > xLength / 2.0 - 1 - (building.getSize().width) && xIndex < xLength / 2.0)&& xIndex != xLength/2.0) {

            if (isBuildable(xIndex, yIndex, building.getSize(), side)) {

                placeOnEmptyField(yIndex, building, xIndex);

                if (game.getGameState().getCurrentPlayer().getGold() >= building.getValue()) {
                    game.getGameState().getCurrentPlayer().addEntity(building);
                }
            } else if (Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().stream().map(Entity::getType).toList()
                    .contains(building.getType())
                    && Objects.equals(Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0).getSide(), playerSide)) {
                if ((game.getGameState().getCurrentPlayer().getGold() >= 30
                        && Objects.equals(building.getType(), Types.BARRACKS))
                        || (Types.getTowerTypes().contains(building.getType()) && game.getGameState().getCurrentPlayer().getGold() >= (((Tower) building).getLevel() * 5 + 10)
                        && Types.getUpgradeable().contains(building.getType()))) {
                    game.getGameState().getCurrentPlayer()
                            .upgradeBuilding((Building) Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0));
                }

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().stream().map(Entity::getType).toList()
                    .contains(Types.SHOTGUN)
                    && Objects.equals(building.getType(), Types.SNIPER)
                    && Objects.equals(Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0).getSide(), playerSide)) {
                transformTower(building, xIndex, yIndex, inverted, type);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().stream().map(Entity::getType).toList()
                    .contains(Types.SNIPER)
                    && Objects.equals(building.getType(), Types.SHOTGUN)
                    && Objects.equals(Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0).getSide(), playerSide)) {
                transformTower(building, xIndex, yIndex, inverted, type);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().stream().map(Entity::getType).toList()
                    .contains(Types.BARRICADE)
                    && (Objects.equals(building.getType(), Types.SHOTGUN) || Objects.equals(building.getType(), Types.BARRICADE))
                    && Objects.equals(Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0).getSide(), playerSide)) {
                transformTower(building, xIndex, yIndex, inverted, type);
            }
        }

    }


    /**
     * Checks if the building can be placed on the given tile
     *
     * @param yIndex the y-coordinate of the tile
     * @param building    the building to be placed
     * @param xIndex the x-coordinate of the tile
     */
    private void placeOnEmptyField(int yIndex, Building building, int xIndex) {
        for (int y = yIndex; y < yIndex + building.getSize().height; y++) {
            for (int x = xIndex; x < xIndex + building.getSize().width; x++) {
                if (game.getGameState().getCurrentPlayer().getGold() >= building.getValue()) {
                    Game.getMapReference().getTiles()[y][x].addEntities(building);
                }
            }
        }
    }


    /**
     * Check if a building is destroyed on the position
     *
     * @param xIndex the x-coordinate of the tile
     * @param yIndex the y-coordinate of the tile
     * @return true if the building is destroyed
     */
    private boolean destroyedOnPosition(int xIndex, int yIndex) {
        if (Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().size() > 0) {
            for (Entity entity : Game.getMapReference().getTiles()[yIndex][xIndex].getEntities()) {
                if (entity instanceof Tower tower && tower.isDestroyed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Transforms a Tower
     *
     * @param building    the tower to transform
     * @param xIndex index of the row
     * @param yIndex index of the column
     */
    private void transformTower(Building building, int xIndex, int yIndex, boolean inverted, Types type) {

        Building newTower = game.getGameState().getCurrentPlayer()
                .transformTower((Tower) Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0), building.getType());
        deleteBuilding((Building) Game.getMapReference().getTiles()[yIndex][xIndex].getEntities().get(0));
        placeBuilding(newTower, inverted, type);
    }

}
