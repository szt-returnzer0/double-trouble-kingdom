package view;

import model.Map;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.*;

/**
 * Implementation of the view of Map editor.
 */
public class MapEditorView extends GameField {
    //private final Castle[] castles = new Castle[]{null, null};
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
        tick.stop();
        middleText = mapRef.getName();
        sideText = "Selection: " + type;
    }

    /**
     * Sets the view's buttons.
     */
    @Override
    protected void setupButtons() {

        this.controlPanel.setButtonColor(0, Color.green, 2);
        this.controlPanel.attachActionListener(0, e -> sideText = "Selection: " + (type = "Plains"));
        this.controlPanel.setButtonColor(1, Color.blue, 2);
        this.controlPanel.attachActionListener(1, e -> sideText = "Selection: " + (type = "Swamp"));
        this.controlPanel.setButtonColor(2, Color.darkGray, 2);
        this.controlPanel.attachActionListener(2, e -> sideText = "Selection: " + (type = "Mountain"));
        this.controlPanel.setButtonColor(3, Color.yellow, 2);
        this.controlPanel.attachActionListener(3, e -> sideText = "Selection: " + (type = "Desert"));

        this.controlPanel.setButtonColor(4, Color.lightGray, 2);
        this.controlPanel.attachActionListener(4, e -> sideText = "Selection: " + (type = "Castle"));
        this.controlPanel.setButtonColor(5, new Color(64, 37, 19), 2);
        this.controlPanel.attachActionListener(5, e -> sideText = "Selection: " + (type = "Barracks"));

        this.controlPanel.attachActionListener(6, e -> sideText = "Selection: " + (type = "Delete"));
        this.controlPanel.setButtonColors(6, new Color[]{
                new Color(255, 142, 142),
                new Color(70, 0, 0),
                new Color(166, 0, 0)});
        this.controlPanel.setButtonText(6, "X");

        FileDialog fileDialog = new FileDialog();

        this.hamburgerMenu.attachActionListener(0, e -> fileDialog.saveMapDialog(mapRef));

        this.hamburgerMenu.attachActionListener(1, e -> {
            Map loadedMap = fileDialog.loadMapDialog();
            if (loadedMap != null) {
                mapRef = loadedMap;
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
        ArrayList<Queue<Building>> arr = Objects.equals(b.getType(), "Castle") ? castles : barracks;
        int maxSize = Objects.equals(b.getType(), "Castle") ? 1 : 2;
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && notOnOtherBuilding(xIdx, yIdx, b.getSize()) && (Objects.equals(b.getType(), "Castle") || isEmpty(xIdx, yIdx, b.getSize())) && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {
            if (side.equals("left") && arr.get(0).size() >= maxSize) {
                deleteBuilding(arr.get(0).remove());
                //arr.get(0).add(b);

            } else if (side.equals("right") && arr.get(1).size() >= maxSize) {
                deleteBuilding(arr.get(1).remove());
                //arr.get(1).add(b);
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

        if (yIdx < yLength && xIdx < xLength)
            try {
                ArrayList<Entity> ent = map.getTiles()[yIdx][xIdx].getEntities();
                switch (type) {
                    case "Plains" -> map.getTiles()[yIdx][xIdx] = new Plains(new Point(xIdx, yIdx), ent);
                    case "Swamp" -> map.getTiles()[yIdx][xIdx] = new Swamp(new Point(xIdx, yIdx), ent);
                    case "Mountain" -> map.getTiles()[yIdx][xIdx] = new Mountain(new Point(xIdx, yIdx), ent);
                    case "Desert" -> map.getTiles()[yIdx][xIdx] = new Desert(new Point(xIdx, yIdx), ent);
                    case "Castle" -> placeLimitedBuilding(new Castle(new Point(xIdx, yIdx), ""));
                    case "Barracks" -> placeLimitedBuilding(new Barracks(new Point(xIdx, yIdx), ""));
                    case "Delete" -> {
                        if (!ent.isEmpty()) safeDeleteBuilding(ent.get(0));
                    }
                    default -> {
                    }
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
            case "Castle" -> {
                switch (ent.getSide()) {
                    case "left" -> castles.get(0).remove((Building) ent);
                    case "right" -> castles.get(1).remove((Building) ent);
                }
            }
            case "Barracks" -> {
                switch (ent.getSide()) {
                    case "left" -> barracks.get(0).remove((Building) ent);
                    case "right" -> barracks.get(1).remove((Building) ent);
                }
            }
        }
        deleteBuilding((Building) ent);
    }

    /**
     * Checks if the necessary building were placed.
     */
    private void checkForBuildings() {
        Set<Entity> buildings = new HashSet<>();
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (!mapRef.getTiles()[y][x].getEntities().isEmpty())
                    buildings.add(mapRef.getTiles()[y][x].getEntities().get(0));
            }
        }
        for (Entity building : buildings) {
            System.out.println(building.getType() + " " + building.getSide());
            switch (building.getType()) {
                case "Castle" -> {
                    switch (building.getSide()) {
                        case "left" -> castles.get(0).add((Building) building);
                        case "right" -> castles.get(1).add((Building) building);
                    }
                }
                case "Barracks" -> {
                    switch (building.getSide()) {
                        case "left" -> barracks.get(0).add((Building) building);
                        case "right" -> barracks.get(1).add((Building) building);
                    }
                }
            }
        }
    }
}
