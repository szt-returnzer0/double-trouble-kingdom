package DTK_view;

import DTK_model.Map;
import DTK_model.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Queue;
import java.util.*;


/**
 * Implementation of the view of Map editor.
 */
public class MapEditorView extends GameField {
    /**
     * List to check if enough castles were placed.
     */
    private final ArrayList<Queue<Building>> castles = new ArrayList<>(Arrays.asList(new LinkedList<>(), new LinkedList<>()));
    /**
     * List to check if enough barracks were placed.
     */
    private final ArrayList<Queue<Building>> barracks = new ArrayList<>(Arrays.asList(new LinkedList<>(), new LinkedList<>()));

    /**
     * Constructs a new MapEditorView instance.
     *
     * @param dummyGame game dependency, because of inheritance
     * @param frame     the parent frame
     */
    public MapEditorView(Game dummyGame, JFrame frame) {
        super(dummyGame, frame);
        game.getGameState().linkGameField(null);
        middleText = mapRef.getName();
        sideText = "Selection: " + type;
        Timer reP = new Timer(17, e ->
                this.repaint()
        );

        this.hamburgerMenu.attachActionListener(6, e ->
                reP.stop()
        );
        reP.start();

    }

    /**
     * Sets the view's buttons.
     */
    @Override
    protected void setupButtons() {

        this.controlPanel.setButtonColor(0, Color.green, 2);
        this.controlPanel.attachActionListener(0, e -> sideText = "Selection: " + (type = ObjectTypes.PLAINS).text);
        this.controlPanel.setButtonColor(1, Color.blue, 2);
        this.controlPanel.attachActionListener(1, e -> sideText = "Selection: " + (type = ObjectTypes.SWAMP).text);
        this.controlPanel.setButtonColor(2, Color.darkGray, 2);
        this.controlPanel.attachActionListener(2, e -> sideText = "Selection: " + (type = ObjectTypes.MOUNTAIN).text);
        this.controlPanel.setButtonColor(3, Color.yellow, 2);
        this.controlPanel.attachActionListener(3, e -> sideText = "Selection: " + (type = ObjectTypes.DESERT).text);

        this.controlPanel.setButtonColor(4, Color.lightGray, 2);
        this.controlPanel.attachActionListener(4, e -> sideText = "Selection: " + (type = ObjectTypes.CASTLE).text);
        this.controlPanel.setButtonColor(5, new Color(64, 37, 19), 2);
        this.controlPanel.attachActionListener(5, e -> sideText = "Selection: " + (type = ObjectTypes.BARRACKS).text);

        this.controlPanel.attachActionListener(6, e -> sideText = "Selection: " + (type = ObjectTypes.DELETE).text);
        this.controlPanel.setButtonColors(6, new Color[]{
                new Color(255, 142, 142),
                new Color(70, 0, 0),
                new Color(166, 0, 0)});
        this.controlPanel.setButtonText(6, "X");

        FileDialog fileDialog = new FileDialog();

        this.hamburgerMenu.attachActionListener(0, e -> fileDialog.saveMapDialog(mapRef));

        this.hamburgerMenu.attachActionListener(1, e -> {
            Pair<Map, File> loaded = fileDialog.loadMapDialog();
            if (loaded != null) {
                mapRef = loaded.getMap();
            }
        });

    }

    /**
     * Checks if an ArrayList has matching types.
     *
     * @param ent the ArrayList to check
     * @return if it had matching types
     */
    private boolean hasMatchingTypes(ArrayList<Entity> ent) {
        boolean result = false;
        for (Entity entity : ent) {
            result = result || Objects.equals(entity.getType(), this.type);
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
    private boolean notOnOtherBuilding(int xIdx, int yIdx, Dimension size) {
        boolean onBuilding = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                onBuilding = onBuilding && (hasMatchingTypes(mapRef.getTiles()[y][x].getEntities()) || mapRef.getTiles()[y][x].getEntities().isEmpty());//hasNoMatchingTypes(mapRef.getTiles()[y][x].getEntities(), type);
            }
        }
        return onBuilding;
    }

    /**
     * Places a building with placement rules.
     *
     * @param b the building to place
     */
    private void placeLimitedBuilding(Building b) {
        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        String side = xIdx < xLength / 2 ? "left" : "right";
        b.setSide(side);
        if (inverted)
            b.invert();
        ArrayList<Queue<Building>> arr = Objects.equals(b.getType(), ObjectTypes.CASTLE) ? castles : barracks;
        int maxSize = Objects.equals(b.getType(), ObjectTypes.CASTLE) ? 1 : 2;
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && notOnOtherBuilding(xIdx, yIdx, b.getSize()) && (Objects.equals(b.getType(), ObjectTypes.CASTLE) || isEmpty(xIdx, yIdx, b.getSize())) && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {
            if (side.equals("left") && arr.get(0).size() >= maxSize) {
                deleteBuilding(arr.get(0).remove());

            } else if (side.equals("right") && arr.get(1).size() >= maxSize) {
                deleteBuilding(arr.get(1).remove());
            }
            if (side.equals("left")) {
                arr.get(0).add(b);
            } else {
                arr.get(1).add(b);
            }
            for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
                for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                    mapRef.getTiles()[y][x].addEntities(b);
                }
            }

        }
    }

    /**
     * Places a new tile.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    @Override
    protected void placeBlock(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        Map map = mapRef;

        if (yIdx < yLength && xIdx < xLength && yIdx >= 0 && xIdx >= 0)
            try {
                ArrayList<Entity> ent = map.getTiles()[yIdx][xIdx].getEntities();

                if(ObjectTypes.getBuildingTypes().contains(type)){
                   placeLimitedBuilding(ObjectTypes.buildingFactory(type, xIdx, yIdx));
                }
                else if(ObjectTypes.getTerrainTypes().contains(type)){
                    map.getTiles()[yIdx][xIdx] = ObjectTypes.terrainFactory(type,ent);
                }
                if(Objects.equals(type, ObjectTypes.DELETE)){
                    safeDeleteBuilding(ent.get(0));
                }






            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * Deletes a building with placement rules.
     *
     * @param ent the building to delete
     */
    private void safeDeleteBuilding(Entity ent) {
        switch (ent.getType()) {
            case CASTLE -> {
                switch (ent.getSide()) {
                    case "left" -> castles.get(0).remove((Building) ent);
                    case "right" -> castles.get(1).remove((Building) ent);
                }
            }
            case BARRACKS -> {
                switch (ent.getSide()) {
                    case "left" -> barracks.get(0).remove((Building) ent);
                    case "right" -> barracks.get(1).remove((Building) ent);
                }
            }
        }
        deleteBuilding((Building) ent);
    }

}
