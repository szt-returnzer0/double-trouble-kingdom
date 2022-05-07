package DTK_view;

import DTK_model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.SimpleTimeZone;

/**
 * Implementation of the GameField class, which displays the game table.
 */
public class GameField extends GameFieldRenderer {
    /**
     * The selection type.
     */
    protected ObjectTypes type = ObjectTypes.NONE;

    /**
     * Determines if the selection is inverted.
     */
    protected boolean inverted = false;
    /**
     * Determines if delete mode is on.
     */
    private boolean deleteState;

    /**
     * The list of waypoints.
     */
    private final ArrayList<Point> wayPoints = new ArrayList<>();

    /**
     * Constructs a new GameField instance.
     *
     * @param game  Game dependency injection
     * @param frame the parent frame
     */
    public GameField(Game game, JFrame frame) {
        super(game, frame);
        GameFieldModel gameFieldModel = new GameFieldModel();
        this.game = game;
        setupButtons();
        game.getGameState().loadBuildings(mapRef);

        middleText = "0 sec";
        sideText = getRoundStateText();


        game.getGameState().linkGameField(this);
        if (game.getDatabase() == null) {
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                        setSelection(null);
                        draw(e);
                    }

                }
            });


        }

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateSelection(e.getX(), e.getY());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (ObjectTypes.getSoldierTypes().contains(type)) {
                        handleWayPoint(e.getX(), e.getY());
                    } else
                        inverted = !inverted;
                    updateSelection(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    setSelection(null);
                    draw(e);
                }

            }
        });

        repaint();
    }

    /**
     * Handle a waypoint.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    private void handleWayPoint(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        if (mapRef.getTiles()[yIdx][xIdx].getEntities().isEmpty()) {
            if (selection instanceof Soldier s && s.getTerrains().contains(mapRef.getTiles()[yIdx][xIdx].getType())) {
                if (wayPoints.contains(new Point(xIdx, yIdx))) {
                    wayPoints.remove(new Point(xIdx, yIdx));
                } else
                    wayPoints.add(new Point(xIdx, yIdx));
            }
        }
        //System.out.println(wayPoints);
    }

    /**
     * Returns the waypoints.
     *
     * @return the waypoints
     */
    public ArrayList<Point> getWayPoints() {
        return wayPoints;
    }

    /**
     * Updates the UIState.
     */
    public void updateUIState() {
        middleText = "" + game.getGameState().getElapsedTime() + " sec";
        if (!game.getGameState().getRoundState().equals("Attacking")) {
            sideText = getRoundStateText();
            controlPanel.showControlPanel();
            updateButtons();
        } else
            controlPanel.hideControlPanel();
        if (game.getGameState().isEnded()) {
            controlPanel.hideControlPanel();
        }
        repaint();
    }


    /**
     * Draws or removes new Entities on the canvas.
     */
    private void draw(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (!deleteState) placeBlock(x, y);
        else delete(x, y);
        repaint();
    }

    /**
     * Deletes an Entity from the canvas and the player's entities
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    private void delete(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        if (!mapRef.getTiles()[yIdx][xIdx].getEntities().isEmpty()) {
            Building b = (Building) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0);
            String pSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? "left" : "right";
            if (Objects.equals(b.getSide(), pSide) &&
                    !(b instanceof Castle)) {
                deleteBuilding(b);
                game.getGameState().getCurrentPlayer().removeEntity(b);
            }
        }
    }

    /**
     * Sets the panel's buttons
     */
    protected void setupButtons() {
        updateButtons();

        FileDialog fileDialog = new FileDialog();
        this.hamburgerMenu.attachActionListener(0, e -> fileDialog.saveGameDialog(this.game));
        this.hamburgerMenu.attachActionListener(1, e -> {
            refreshGameField();
            repaint();
        });

        this.hamburgerMenu.attachActionListener(5, e -> game.getGameState().togglePathVisualization());

        this.controlPanel.attachActionListener(5, e -> {
            getWayPoints().clear();
            game.getGameState().nextRoundState();
            sideText = getRoundStateText();
            updateButtons();
            setSelection(null);
            if (deleteState) toggleDelete();
            type = ObjectTypes.NO_SELECTION;
        });
        this.controlPanel.setButtonSize(5, new Dimension(100, 50));

        this.controlPanel.attachActionListener(6, e -> {
                    toggleDelete();
                    setSelection(null);
                    type = ObjectTypes.NO_SELECTION;
                }
        );

    }

    /**
     * Returns the Round state as a String.
     *
     * @return the Round state as a String
     */
    private String getRoundStateText() {
        return !Objects.equals(game.getGameState().getRoundState(), "Attacking") ? ("Phase: " + game.getGameState().getRoundState() + " | Player: " + game.getGameState().getCurrentPlayer().getPlayerNumber()) : ("Phase: " + game.getGameState().getRoundState());
    }

    /**
     * Redraws the canvas.
     */
    public void refreshGameField() {
        frame.remove(this);
        FileDialog fileDialog = new FileDialog();
        Game loadedGame = fileDialog.loadGameDialog();
        if (loadedGame != null) {
            this.game = loadedGame;
        }
        frame.add(new GameField(game, frame));
        frame.pack();
    }

    /**
     * Updates the buttons' state.
     */
    private void updateButtons() {
        if (game.getGameState().getRoundState().equals("Building")) {
            this.controlPanel.setButtonText(0, "Bar");
            this.controlPanel.attachActionListener(0, e -> {
                type = ObjectTypes.BARRICADE;
                if (deleteState) toggleDelete();
            });

            this.controlPanel.setButtonText(1, "Sho");
            this.controlPanel.attachActionListener(1, e -> {
                type = ObjectTypes.SHOTGUN;
                if (deleteState) toggleDelete();
            });
            this.controlPanel.getButtons()[1].setEnabled(true);

            this.controlPanel.setButtonText(2, "Sni");
            this.controlPanel.attachActionListener(2, e -> {
                type = ObjectTypes.SNIPER;
                if (deleteState) toggleDelete();
            });
            this.controlPanel.getButtons()[2].setEnabled(true);

            this.controlPanel.setButtonText(3, "Brk");
            this.controlPanel.attachActionListener(3, e -> {
                type = ObjectTypes.BARRACKS;
                if (deleteState) toggleDelete();
            });
            this.controlPanel.getButtons()[3].setEnabled(true);

            this.controlPanel.hideButton(4);

        } else if (game.getGameState().getRoundState().equals("Training")) {

            this.controlPanel.setButtonText(0, "Sol");
            this.controlPanel.attachActionListener(0, e -> type = ObjectTypes.SOLDIER);          //Lerakas


            if (!game.getGameState().getCurrentPlayer().isUnitRestricted()) {
                this.controlPanel.setButtonText(1, "Kam");
                this.controlPanel.attachActionListener(1, e -> type = ObjectTypes.KAMIKAZE);
                this.controlPanel.getButtons()[1].setEnabled(true);


                this.controlPanel.setButtonText(2, "Ass");
                this.controlPanel.attachActionListener(2, e -> type = ObjectTypes.ASSASSIN);
                this.controlPanel.getButtons()[2].setEnabled(true);


                this.controlPanel.setButtonText(3, "Div");
                this.controlPanel.attachActionListener(3, e -> type = ObjectTypes.DIVER);
                this.controlPanel.getButtons()[3].setEnabled(true);


                this.controlPanel.setButtonText(4, "Cli");
                this.controlPanel.attachActionListener(4, e -> type = ObjectTypes.CLIMBER);
                this.controlPanel.getButtons()[4].setEnabled(true);
            } else {
                for (int i = 1; i < 5; i++) {
                    this.controlPanel.hideButton(i);
                }
            }
        }


        JRoundedButton[] buttons = this.controlPanel.getButtons();
        for (int i = 0, buttonsLength = buttons.length - 2; i < buttonsLength; i++) {
            JRoundedButton button = buttons[i];
            Color tmp;
            switch (button.getText()) {
                case "Brk" -> tmp = new Color(64, 37, 19);
                case "Bar" -> tmp = new Color(208, 146, 110);
                case "Sni" -> tmp = new Color(118, 110, 106);
                case "Sho" -> tmp = new Color(17, 15, 15);
                case "Sol" -> tmp = new Color(190, 30, 30);
                case "Ass" -> tmp = new Color(45, 15, 80);
                case "Kam" -> tmp = new Color(72, 18, 25);
                case "Div" -> tmp = new Color(22, 107, 107);
                case "Cli" -> tmp = new Color(175, 112, 81);
                default -> tmp = Color.gray;
            }
            button.setColors(new Color[]{new Color(Math.min(tmp.getRed() + 30, 255), Math.min(tmp.getGreen() + 30, 255), Math.min(tmp.getBlue() + 30, 255)), new Color(0, 0, 0, 0), tmp});
        }
        controlPanel.updateButtonText();
        repaint();
    }

    /**
     * Places the Entity.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    protected void placeBlock(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;

        System.out.println(type);
        System.out.println(ObjectTypes.getBuildingTypes());
        if (yIdx < yLength && xIdx < xLength && yIdx >= 0 && xIdx >= 0){
            if(ObjectTypes.getBuildingTypes().contains(type)){
                placeBuilding(ObjectTypes.buildingFactory(type, xIdx, yIdx));
            }
            else if(ObjectTypes.getSoldierTypes().contains(type)){
                trainSoldiers(ObjectTypes.soldierFactory(type, xIdx, yIdx));
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
    protected boolean isEmpty(int xIdx, int yIdx, Dimension size) {
        boolean isEmpty = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isEmpty = isEmpty && mapRef.getTiles()[y][x].getEntities().isEmpty();
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
    protected boolean isBuildable(int xIdx, int yIdx, Dimension size, String side) {
        boolean isBuildable = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isBuildable = isBuildable && mapRef.getTiles()[y][x].getEntities().isEmpty() && !Objects.equals(mapRef.getTiles()[y][x].getType(), ObjectTypes.MOUNTAIN) && !Objects.equals(mapRef.getTiles()[y][x].getType(), ObjectTypes.SWAMP);
            }
        }
        if (!((side.equals("left") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1) || (side.equals("right") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
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
    protected boolean isInTrainingGround(int xIdx, int yIdx, Soldier s, String side) {
        ArrayList<Entity> entities = mapRef.getTiles()[yIdx][xIdx].getEntities();
        boolean isInTrainingGround = false;
        for (Entity entity : entities) {


            /*

            if ((entity.getType().equals("Castle") || entity.getType().equals("Barracks")) && s.getType().equals("Soldier")) {
                isInTrainingGround = true;
            }

            if (entity.getType().equals("Barracks") && !((Barracks) entity).isUpgraded() && s.getType().equals("Soldier")) {
                isInTrainingGround = true;
            }

            if (entity.getType().equals("Barracks") && ((Barracks) entity).isUpgraded()) {
                isInTrainingGround = true;
            }
             */

            if ((entity.getType().equals(ObjectTypes.CASTLE) || entity.getType().equals(ObjectTypes.BARRACKS)) && Objects.equals(s.getType(), ObjectTypes.SOLDIER)) {
                isInTrainingGround = true;
            }

            if (Objects.equals(entity.getType(), ObjectTypes.BARRACKS) && !((Barracks) entity).isUpgraded() && Objects.equals(s.getType(), ObjectTypes.SOLDIER)) {
                isInTrainingGround = true;
            }

            if (Objects.equals(entity.getType(), ObjectTypes.BARRACKS) && ((Barracks) entity).isUpgraded()) {
                isInTrainingGround = true;
            }
        }
        if (!((side.equals("left") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1) || (side.equals("right") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
            isInTrainingGround = false;


        return isInTrainingGround;
    }

    /**
     * Updates the current selection to be drawn on the canvas.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    private void updateSelection(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        Entity tmp = null;
        if(ObjectTypes.getBuildingTypes().contains(type))
            tmp = ObjectTypes.buildingFactory(type, xIdx, yIdx);
        else if (ObjectTypes.getSoldierTypes().contains(type))
            tmp = ObjectTypes.soldierFactory(type, xIdx, yIdx);

        if (inverted && tmp != null) tmp.invert();
        setSelection(tmp);
        repaint();
    }

    /**
     * Determines the closest empty tile.
     *
     * @param xIdx index of the row
     * @param yIdx index of the column
     * @return the location of the closest empty tile
     */
    protected Point closestEmptyTile(int xIdx, int yIdx) {
        Point[] directions = new Point[]{new Point(-1, 0), new Point(0, 1), new Point(1, 0), new Point(0, -1)};
        Integer[] counts = Arrays.stream(directions).map(e -> countTiles(new Point(xIdx, yIdx), e, 0)).toArray(Integer[]::new);
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
    private int countTiles(Point from, Point dir, int counter) {
        if (from.x < 0 || from.x > xLength - 1 || from.y < 0 || from.y > yLength - 1) {
            return 10000;
        }
        if (hasNoBuilding(mapRef.getTiles()[from.y][from.x]) && unitIsPlaceable(mapRef.getTiles()[from.y][from.x]))
            return counter;
        return countTiles(new Point(from.x + dir.x, from.y + dir.y), dir, counter + 1);
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
            l = l && !ObjectTypes.getBuildingTypes().contains(entity.getType());
        }
        return l;
    }

    /**
     * Checks if a unit is placeable.
     *
     * @param ter the tile to check
     * @return if its placeable
     */
    private boolean unitIsPlaceable(Terrain ter) {
        boolean result;
        switch (ter.getType()) {
            case SWAMP-> result = Objects.equals(type, ObjectTypes.DIVER);
            case MOUNTAIN -> result = Objects.equals(type, ObjectTypes.CLIMBER);
            default -> result = true;
        }
        return result;
    }

    /**
     * Deletes a building from the Map
     *
     * @param b the building to delete
     */
    protected void deleteBuilding(Building b) {
        if (b != null) {
            for (int y = b.getPosition().y; y < b.getPosition().y + b.getSize().height; y++) {
                for (int x = b.getPosition().x; x < b.getPosition().x + b.getSize().width; x++) {
                    mapRef.getTiles()[y][x].getEntities().clear();
                }
            }
        }
    }

    /**
     * Trains soldiers.
     *
     * @param s the soldier to train.
     */
    protected void trainSoldiers(Soldier s) {
        if (game.getGameState().getCurrentPlayer().getGold() >= s.getValue()) {
            s.setOwner(game.getGameState().getCurrentPlayer());
            int xIdx = s.getPosition().x;
            int yIdx = s.getPosition().y;
            String side = xIdx + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side
            s.setSide(side);
            if (isInTrainingGround(xIdx, yIdx, s, side)) {
                Point point = closestEmptyTile(xIdx, yIdx);
                System.out.println(point);
                s.setPosition(point);

                GameState.animBuffer.add(s.getAnimObj());
                s.getAnimObj().setSeconds(1 / s.getSpeed());
                s.getAnimObj().setSpeedModifier(mapRef.getTiles()[point.y][point.x].getSpeedMod());
                s.setIsAnimated(false);
                wayPoints.forEach(s::addWaypoint);
                wayPoints.clear();
                mapRef.getTiles()[point.y][point.x].addEntities(s);
                game.getGameState().getCurrentPlayer().addEntity(s);
                controlPanel.updateButtonText();
            }
        }
    }

    //public static ArrayList<Point> testpath = new ArrayList<>(); //visualization

    /**
     * Places a building.
     *
     * @param b the building to place
     */
    protected void placeBuilding(Building b) {

        System.out.println(0);

        b.setOwner(game.getGameState().getCurrentPlayer());


        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        String side = xIdx + 3 < xLength / 2 ? "left" : "right";
        b.setSide(side);


        if (inverted)
            b.invert();

        System.out.println(1);

        if (destroyedOnPos(xIdx, yIdx)) return;


        Building enemyCastle = game.getGameState().getEnemyCastle(b.getOwner().getPlayerNumber());
        Soldier testUnit = new Soldier(closestEmptyTile(enemyCastle.getPosition().x + enemyCastle.getSize().width / 2 + (b.getSide().equals("left") ? 1 : -1), enemyCastle.getPosition().y + enemyCastle.getSize().height / 2));
        testUnit.setSide(enemyCastle.getSide());
        Pathfinder pf = new Pathfinder();
        Pathfinder.setMap(mapRef);

        System.out.println(2);

        if (pf.Dijkstra(testUnit, enemyCastle.getSide().equals("right") ? "left" : "right", b) == null) return;

        System.out.println(55555);

        String playerSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? "left" : "right";
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {
            System.out.println(13);
            if (isBuildable(xIdx, yIdx, b.getSize(), side)) {
                System.out.println(3);
                placeOnEmptyField(yIdx, b, xIdx);

                if (game.getGameState().getCurrentPlayer().getGold() >= b.getValue()) {
                    game.getGameState().getCurrentPlayer().addEntity(b);
                    controlPanel.updateButtonText();
                }
            } else if (mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains(b.getType())
                    && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                if ((game.getGameState().getCurrentPlayer().getGold() >= 30 && Objects.equals(b.getType(), ObjectTypes.BARRACKS))
                        || (game.getGameState().getCurrentPlayer().getGold() >= (((Tower) b).getLevel() * 5 + 10) && ObjectTypes.getUpgradeable().contains(b.getType()))) {
                    game.getGameState().getCurrentPlayer().upgradeBuilding((Building) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0));
                    this.controlPanel.updateButtonText();
                    repaint();
                }

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains(ObjectTypes.SHOTGUN)
                    && Objects.equals(b.getType(), ObjectTypes.SNIPER) && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains(ObjectTypes.SNIPER)
                    && Objects.equals(b.getType(), ObjectTypes.SHOTGUN) && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains(ObjectTypes.BARRICADE)
                    && (Objects.equals(b.getType(), ObjectTypes.SHOTGUN) || Objects.equals(b.getType(), ObjectTypes.BARRICADE))
                    && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx);
            }
        }
        System.out.println(9);
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
                    mapRef.getTiles()[y][x].addEntities(b);
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
        if (mapRef.getTiles()[yIdx][xIdx].getEntities().size() > 0) {
            for (Entity e : mapRef.getTiles()[yIdx][xIdx].getEntities()) {
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
    private void transformTower(Building b, int xIdx, int yIdx) {

        Building newTower = game.getGameState().getCurrentPlayer().transformTower((Tower) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0), b.getType());
        deleteBuilding((Building) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0));
        placeBuilding(newTower);
        repaint();
    }

    /**
     * Toggles the delete mode.
     */
    private void toggleDelete() {

        if ("Building".equals(game.getGameState().getRoundState()) && !deleteState) {

            this.controlPanel.setButtonColors(6, new Color[]{
                    new Color(255, 142, 142),
                    new Color(70, 0, 0),
                    new Color(166, 0, 0)});
            this.controlPanel.setButtonText(6, "X");
            deleteState = true;
            repaint();

        } else {
            this.controlPanel.setButtonColors(6, new Color[]{
                    new Color(255, 205, 0),
                    new Color(255, 205, 0),
                    new Color(255, 205, 0)});
            this.controlPanel.setButtonText(6, "0");
            controlPanel.updateButtonText();
            deleteState = false;
            repaint();
        }
    }


}
