package DTK_model;

import java.awt.*;
import java.util.Queue;
import java.util.*;

/**
 * Implementation of MapEditor model class.
 */
public class MapEditorModel {

    /**
     * List to check if enough castles were placed.
     */
    private final ArrayList<Queue<Building>> castles = new ArrayList<>(Arrays.asList(new LinkedList<>(), new LinkedList<>()));
    /**
     * List to check if enough barracks were placed.
     */
    private final ArrayList<Queue<Building>> barracks = new ArrayList<>(Arrays.asList(new LinkedList<>(), new LinkedList<>()));

    private final int xLength;
    private final int yLength;

    /**
     * Constructor for MapEditorModel.
     */
    public MapEditorModel() {
        xLength = Game.getMapReference().getTiles()[0].length;
        yLength = Game.getMapReference().getTiles().length;
    }

    /**
     * Checks if an ArrayList has matching types.
     *
     * @param entities the ArrayList to check
     * @return if it had matching types
     */
    private boolean hasMatchingTypes(ArrayList<Entity> entities, Types type) {
        boolean result = false;
        for (Entity entity : entities) {
            result = result || Objects.equals(entity.getType(), type);
        }
        return result;
    }

    /**
     * Checks if we don't build on other buildings
     *
     * @param xIdx index of the row
     * @param yIdx index of the column
     * @param size the size of the building
     * @return if we don't try to build on an existing building
     */
    private boolean notOnOtherBuilding(int xIdx, int yIdx, Dimension size, Types type) {
        boolean onBuilding = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                onBuilding = onBuilding && (hasMatchingTypes(Game.getMapReference().getTiles()[y][x].getEntities(), type) || Game.getMapReference().getTiles()[y][x].getEntities().isEmpty());
            }
        }
        return onBuilding;
    }

    /**
     * Places a building with placement rules.
     *
     * @param b the building to place
     */
    private void placeLimitedBuilding(Building b, boolean inverted, Types type, GameFieldModel gameFieldModel, Game game) {
        b.setOwner(game.getGameState().getCurrentPlayer());
        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        Sides side = xIdx + b.getSize().width / 2 - 1 < xLength / 2 ? Sides.BLUE : Sides.RED;
        b.setSide(side);

        if (inverted)
            b.invert();

        if (!castles.get(0).isEmpty() && !castles.get(1).isEmpty()) {
            Building enemyCastle = b.getSide() == Sides.BLUE ? castles.get(1).element() : castles.get(0).element();
            Soldier testUnit = new Soldier(gameFieldModel.closestEmptyTile(
                    enemyCastle.getPosition().x + enemyCastle.getSize().width / 2
                            + (b.getSide().equals(Sides.BLUE) ? 1 : -1),
                    enemyCastle.getPosition().y + enemyCastle.getSize().height / 2, Types.SOLDIER));
            testUnit.setSide(enemyCastle.getSide());
            Pathfinder pf = new Pathfinder();
            Pathfinder.setMap(Game.getMapReference());
            if (pf.Dijkstra(testUnit, enemyCastle.getSide().equals(Sides.RED) ? Sides.BLUE : Sides.RED, b) == null)
                return;
        }

        ArrayList<Queue<Building>> arr = Objects.equals(b.getType(), Types.CASTLE) ? castles : barracks;
        int maxSize = Objects.equals(b.getType(), Types.CASTLE) ? 1 : 2;
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && notOnOtherBuilding(xIdx, yIdx, b.getSize(), type) && (Objects.equals(b.getType(), Types.CASTLE) || gameFieldModel.isEmpty(xIdx, yIdx, b.getSize())) && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {
            if (side.equals(Sides.BLUE) && arr.get(0).size() >= maxSize) {
                gameFieldModel.deleteBuilding(arr.get(0).remove());

            } else if (side.equals(Sides.RED) && arr.get(1).size() >= maxSize) {
                gameFieldModel.deleteBuilding(arr.get(1).remove());
            }
            if (side.equals(Sides.BLUE)) {
                arr.get(0).add(b);
            } else {
                arr.get(1).add(b);
            }
            for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
                for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                    Game.getMapReference().getTiles()[y][x].addEntities(b);
                }
            }

        }
    }


    /**
     * Deletes a building with placement rules.
     *
     * @param building the building to delete
     */
    private void safeDeleteBuilding(Building building, GameFieldModel gameFieldModel) {

        Sides side = building.getSide();
        switch (building.getType()) {
            case CASTLE -> castles.get(side.id).remove(building);
            case BARRACKS -> barracks.get(side.id).remove(building);
            default -> throw new IllegalArgumentException("Unexpected value: " + building.getType());

        }
        gameFieldModel.deleteBuilding(building);

    }

    /**
     * Handles the placement of a building or unit.
     *
     * @param xIdx           the x index of the tile
     * @param yIdx           the y index of the tile
     * @param gameFieldModel the game field model
     * @param inverted       whether the building is inverted
     * @param type           the type of the building
     */
    public void placeBlock(int xIdx, int yIdx, GameFieldModel gameFieldModel, boolean inverted, Types type, Game game) {
        if (yIdx < yLength && xIdx < xLength && yIdx >= 0 && xIdx >= 0) {

            ArrayList<Entity> ent = Game.getMapReference().getTiles()[yIdx][xIdx].getEntities();

            if (Types.getBuildingTypes().contains(type)) {
                placeLimitedBuilding(Types.buildingFactory(type, xIdx, yIdx), inverted, type, gameFieldModel, game);
            } else if (Types.getTerrainTypes().contains(type)) {
                //Game.getMapReference().getTiles()[yIdx][xIdx] = Types.terrainFactory(type, ent);
                placeTerrain(xIdx, yIdx, type, ent, gameFieldModel, game);

            }
            if (Objects.equals(type, Types.DELETE) && !ent.isEmpty()) {
                safeDeleteBuilding((Building) ent.get(0), gameFieldModel);
            }
        }
    }

    private void placeTerrain(int xIdx, int yIdx, Types type, ArrayList<Entity> ent, GameFieldModel gameFieldModel, Game game) {
        if (!ent.isEmpty() || castles.get(0).isEmpty() || castles.get(1).isEmpty())
            return;
        Building b = new Barricade(new Point(xIdx, yIdx));
        b.getSize().width = 1;
        b.getSize().height = 1;

        b.setOwner(game.getGameState().getCurrentPlayer());

        Sides side = xIdx + b.getSize().width / 2 - 1 < xLength / 2 ? Sides.BLUE : Sides.RED;
        b.setSide(side);
        Building enemyCastle = b.getSide() == Sides.BLUE ? castles.get(1).element() : castles.get(0).element();
        Soldier testUnit = new Soldier(gameFieldModel.closestEmptyTile(
                enemyCastle.getPosition().x + enemyCastle.getSize().width / 2
                        + (b.getSide().equals(Sides.BLUE) ? 1 : -1),
                enemyCastle.getPosition().y + enemyCastle.getSize().height / 2, Types.SOLDIER));
        testUnit.setSide(enemyCastle.getSide());
        Pathfinder pf = new Pathfinder();
        Pathfinder.setMap(Game.getMapReference());


        if (pf.Dijkstra(testUnit, enemyCastle.getSide().equals(Sides.RED) ? Sides.BLUE : Sides.RED, b) == null)
            return;


        Game.getMapReference().getTiles()[yIdx][xIdx] = Types.terrainFactory(type, ent);
    }
}
