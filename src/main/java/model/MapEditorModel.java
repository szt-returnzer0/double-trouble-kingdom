package model;

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
     * @param xIndex index of the row
     * @param yIndex index of the column
     * @param size   the size of the building
     * @return if we don't try to build on an existing building
     */
    private boolean notOnOtherBuilding(int xIndex, int yIndex, Dimension size, Types type) {
        boolean onBuilding = true;
        for (int y = yIndex; y < yIndex + size.height; y++) {
            for (int x = xIndex; x < xIndex + size.width; x++) {
                onBuilding = onBuilding && (hasMatchingTypes(Game.getMapReference().getTiles()[y][x].getEntities(), type) || Game.getMapReference().getTiles()[y][x].getEntities().isEmpty());
            }
        }
        return onBuilding;
    }

    /**
     * Places a building with placement rules.
     *
     * @param building the building to place
     */
    private void placeLimitedBuilding(Building building, boolean inverted, Types type, GameFieldModel gameFieldModel) {
        int xIndex = building.getPosition().x;
        int yIndex = building.getPosition().y;
        //Sides side = xIndex < xLength / 2 ? Sides.BLUE : Sides.RED;
        Sides side = xIndex + building.getSize().width / 2 - 1 < xLength / 2 ? Sides.BLUE : Sides.RED;
        building.setSide(side);
        if (inverted)
            building.invert();


        ArrayList<Queue<Building>> arr = Objects.equals(building.getType(), Types.CASTLE) ? castles : barracks;
        int maxSize = Objects.equals(building.getType(), Types.CASTLE) ? 1 : 2;
        if (xIndex + building.getSize().width <= xLength && yIndex + building.getSize().height <= yLength && notOnOtherBuilding(xIndex, yIndex, building.getSize(), type) && (Objects.equals(building.getType(), Types.CASTLE) || gameFieldModel.isEmpty(xIndex, yIndex, building.getSize())) && !(xIndex > xLength / 2.0 - 1 - (building.getSize().width) && xIndex < xLength / 2.0)) {
            if (side.equals(Sides.BLUE) && arr.get(0).size() >= maxSize) {
                gameFieldModel.deleteBuilding(arr.get(0).remove());

            } else if (side.equals(Sides.RED) && arr.get(1).size() >= maxSize) {
                gameFieldModel.deleteBuilding(arr.get(1).remove());
            }
            if (side.equals(Sides.BLUE)) {
                arr.get(0).add(building);
            } else {
                arr.get(1).add(building);
            }
            for (int y = yIndex; y < yIndex + building.getSize().height; y++) {
                for (int x = xIndex; x < xIndex + building.getSize().width; x++) {
                    Game.getMapReference().getTiles()[y][x].addEntities(building);
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
     * @param xIndex         the x index of the tile
     * @param yIndex         the y index of the tile
     * @param gameFieldModel the game field model
     * @param inverted       whether the building is inverted
     * @param type           the type of the building
     */
    public void placeBlock(int xIndex, int yIndex, GameFieldModel gameFieldModel, boolean inverted, Types type) {
        if (yIndex < yLength && xIndex < xLength && yIndex >= 0 && xIndex >= 0) {

            ArrayList<Entity> entities = Game.getMapReference().getTiles()[yIndex][xIndex].getEntities();

            if (Types.getBuildingTypes().contains(type)) {
                placeLimitedBuilding(Types.buildingFactory(type, xIndex, yIndex), inverted, type, gameFieldModel);
            } else if (Types.getTerrainTypes().contains(type)) {
                Game.getMapReference().getTiles()[yIndex][xIndex] = Types.terrainFactory(type, entities);
            }
            if (Objects.equals(type, Types.DELETE) && !entities.isEmpty()) {
                safeDeleteBuilding((Building) entities.get(0), gameFieldModel);
            }
        }
    }
}
