package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class GameField extends GameFieldRenderer {
    // protected final boolean pressed = false;
    protected String type = "None";
    protected JLabel curPlayer;
    //protected final Barracks[] barracks = new Barracks[]{null, null, null, null};
    protected boolean inverted = false;
    protected Timer tick;
    private boolean deleteState;

    public GameField(Game game, JFrame frame) {
        super(game, frame);
        this.game = game;
        setupButtons();

        middleText = "0 sec";
        sideText = getRoundStateText();

        tick = new Timer(1000, (e) -> {
            middleText = "" + game.getGameState().getElapsedTime() + " sec";
            repaint();
        });

        tick.start();


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

    private void draw(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (!deleteState) placeBlock(x, y);
        else delete(x, y);
        System.out.println(deleteState);
        repaint();
    }

    private void delete(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        if (!mapRef.getTiles()[yIdx][xIdx].getEntities().isEmpty()) {
            Building b = (Building) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0);
            String pSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? "left" : "right";
            if (Objects.equals(b.getSide(), pSide) &&
                    !Objects.equals(b.getType(), "Castle")) {
                deleteBuilding(b);
                game.getGameState().getCurrentPlayer().removeEntity(b);
            }
        }
    }

    protected void setupButtons() {
        updateButtons();

        FileDialog fileDialog = new FileDialog();
        this.hamburgerMenu.attachActionListener(0, e -> fileDialog.saveGameDialog(this.game));
        this.hamburgerMenu.attachActionListener(1, e -> {
            refreshGameField();
            repaint();
        });

        this.controlPanel.attachActionListener(5, e -> {
            game.getGameState().nextRoundState();
            sideText = getRoundStateText();
            updateButtons();
            setSelection(null);
            if (deleteState) toggleDelete();
            type = "NoSelection";
        });
        this.controlPanel.setButtonSize(5, new Dimension(100, 50));

        this.controlPanel.attachActionListener(6, e -> {
                    toggleDelete();
                    setSelection(null);
                    type = "NoSelection";
                }
        );

    }

    private String getRoundStateText() {
        return !Objects.equals(game.getGameState().getRoundState(), "Attacking") ? ("Phase: " + game.getGameState().getRoundState() + " | Player: " + game.getGameState().getCurrentPlayer().getPlayerNumber()) : ("Phase: " + game.getGameState().getRoundState());
    }

    public void refreshGameField() {
        frame.remove(this);
        FileDialog fileDialog = new FileDialog();
        Game game = fileDialog.loadGameDialog();
        frame.add(new GameField(game, frame));
        frame.pack();
    }

    private void updateButtons() {
        if (game.getGameState().getRoundState().equals("Building")) {
            this.controlPanel.setButtonText(0, "Bar");
            this.controlPanel.attachActionListener(0, e -> {
                type = "Barricade";
                if (deleteState) toggleDelete();
            });          //Lerakas

            this.controlPanel.setButtonText(1, "Sho");
            this.controlPanel.attachActionListener(1, e -> {
                type = "Shotgun";
                if (deleteState) toggleDelete();
            });
            this.controlPanel.getButtons()[1].setEnabled(true);

            this.controlPanel.setButtonText(2, "Sni");
            this.controlPanel.attachActionListener(2, e -> {
                type = "Sniper";
                if (deleteState) toggleDelete();
            });
            this.controlPanel.getButtons()[2].setEnabled(true);

            this.controlPanel.setButtonText(3, "Brk");
            this.controlPanel.attachActionListener(3, e -> {
                type = "Barracks";
                if (deleteState) toggleDelete();
            });
            this.controlPanel.getButtons()[3].setEnabled(true);

            this.controlPanel.hideButton(4);

        } else if (game.getGameState().getRoundState().equals("Training")) {

            this.controlPanel.setButtonText(0, "Sol");
            this.controlPanel.attachActionListener(0, e -> type = "Soldier");          //Lerakas


            if (!game.getGameState().getCurrentPlayer().isUnitRestricted()) {
                this.controlPanel.setButtonText(1, "Kam");
                this.controlPanel.attachActionListener(1, e -> type = "Kamikaze");
                this.controlPanel.getButtons()[1].setEnabled(true);


                this.controlPanel.setButtonText(2, "Ass");
                this.controlPanel.attachActionListener(2, e -> type = "Assassin");
                this.controlPanel.getButtons()[2].setEnabled(true);


                this.controlPanel.setButtonText(3, "Div");
                this.controlPanel.attachActionListener(3, e -> type = "Diver");
                this.controlPanel.getButtons()[3].setEnabled(true);


                this.controlPanel.setButtonText(4, "Cli");
                this.controlPanel.attachActionListener(4, e -> type = "Climber");
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
            System.out.println(button.getText());
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

    protected void placeBlock(int x, int y) { //Lerakas
        int yIdx = y / scale;
        int xIdx = x / scale;
        Map map = mapRef;

        if (yIdx < yLength && xIdx < xLength)
            try {
                ArrayList<Entity> ent = map.getTiles()[yIdx][xIdx].getEntities();
                switch (type) {
                    case "Barracks" -> placeBuilding(new Barracks(new Point(xIdx, yIdx), ""));
                    case "Barricade" -> placeBuilding(new Barricade(new Point(xIdx, yIdx), ""));
                    case "Sniper" -> placeBuilding(new Sniper(new Point(xIdx, yIdx), ""));
                    case "Shotgun" -> placeBuilding(new Shotgun(new Point(xIdx, yIdx), ""));
                    case "Soldier" -> trainSoldiers(new Soldier(new Point(xIdx, yIdx), 0));
                    case "Assassin" -> trainSoldiers(new Assassin(new Point(xIdx, yIdx), 0));
                    case "Kamikaze" -> trainSoldiers(new Kamikaze(new Point(xIdx, yIdx), 0));
                    case "Diver" -> trainSoldiers(new Diver(new Point(xIdx, yIdx), 0));
                    case "Climber" -> trainSoldiers(new Climber(new Point(xIdx, yIdx), 0));
                    default -> {
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    protected boolean isEmpty(int xIdx, int yIdx, Dimension size) {
        boolean isEmpty = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isEmpty = isEmpty && mapRef.getTiles()[y][x].getEntities().isEmpty();
            }
        }
        return isEmpty;
    }

    protected boolean isBuildable(int xIdx, int yIdx, Dimension size, String side) {
        boolean isBuildable = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isBuildable = isBuildable && mapRef.getTiles()[y][x].getEntities().isEmpty() && !mapRef.getTiles()[y][x].getType().equals("Mountain") && !mapRef.getTiles()[y][x].getType().equals("Swamp");
            }
        }
        if (!((side.equals("left") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1) || (side.equals("right") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
            isBuildable = false;
        return isBuildable;
    }

    protected boolean isInTrainingGround(int xIdx, int yIdx, Soldier s, String side) {
        ArrayList<Entity> entities = mapRef.getTiles()[yIdx][xIdx].getEntities();
        boolean isInTrainingGround = false;
        for (Entity entity : entities) {

            if ((entity.getType().equals("Castle") || entity.getType().equals("Barracks")) && s.getType().equals("Soldier")) {
                isInTrainingGround = true;
            }

            if (entity.getType().equals("Barracks") && !((Barracks) entity).isUpgraded() && s.getType().equals("Soldier")) {
                isInTrainingGround = true;
            }

            if (entity.getType().equals("Barracks") && ((Barracks) entity).isUpgraded()) {
                isInTrainingGround = true;
            }
        }
        if (!((side.equals("left") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1) || (side.equals("right") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
            isInTrainingGround = false;


        return isInTrainingGround;
    }

    private void updateSelection(int x, int y) { //Lerakas
        int yIdx = y / scale;
        int xIdx = x / scale;
        Entity tmp; // Entity Switched Building
        switch (type) {
            case "Castle" -> tmp = new Castle(new Point(xIdx, yIdx), "");
            case "Barracks" -> tmp = new Barracks(new Point(xIdx, yIdx), "");
            case "Barricade" -> tmp = new Barricade(new Point(xIdx, yIdx), "");
            case "Sniper" -> tmp = new Sniper(new Point(xIdx, yIdx), "");
            case "Shotgun" -> tmp = new Shotgun(new Point(xIdx, yIdx), "");
            case "Soldier" -> tmp = (new Soldier(new Point(xIdx, yIdx), 0));
            case "Assassin" -> tmp = (new Assassin(new Point(xIdx, yIdx), 0));
            case "Kamikaze" -> tmp = (new Kamikaze(new Point(xIdx, yIdx), 0));
            case "Diver" -> tmp = (new Diver(new Point(xIdx, yIdx), 0));
            case "Climber" -> tmp = (new Climber(new Point(xIdx, yIdx), 0));
            default -> tmp = null;
        }

        if (inverted && tmp != null) tmp.invert();
        setSelection(tmp);
        repaint();
    }

    protected Point closestEmptyTile(int xIdx, int yIdx) {
        Point[] directions = new Point[]{new Point(-1, 0), new Point(0, 1), new Point(1, 0), new Point(0, -1)};
        Integer[] counts = Arrays.stream(directions).map(e -> countTiles(new Point(xIdx, yIdx), e, 0)).toArray(Integer[]::new);
        int idx = Arrays.asList(counts).indexOf(Arrays.stream(counts).min(Integer::compare).get());
        return new Point(xIdx + directions[idx].x * counts[idx], yIdx + directions[idx].y * counts[idx]);
    }

    private int countTiles(Point from, Point dir, int counter) {
        if (from.x <= 0 || from.x >= xLength - 1 || from.y <= 0 || from.y >= yLength - 1 || hasNoBuilding(mapRef.getTiles()[from.y][from.x]) && unitIsPlaceable(mapRef.getTiles()[from.y][from.x]))//mapRef.getTiles()[from.y][from.x].getEntities().isEmpty())
            return counter;
        return countTiles(new Point(from.x + dir.x, from.y + dir.y), dir, counter + 1);
    }

    private boolean hasNoBuilding(Terrain ter) {
        boolean l = true;
        String towerTypes = "Barricade Sniper Shotgun Barracks Castle";
        for (Entity entity : ter.getEntities()) {
            l = l && !towerTypes.contains(entity.getType());
        }
        return l;
    }

    private boolean unitIsPlaceable(Terrain ter) {
        boolean result;
        switch (ter.getType()) {
            case "Swamp" -> result = Objects.equals(type, "Diver");
            case "Mountain" -> result = Objects.equals(type, "Climber");
            default -> result = true;
        }
        return result;
    }


    protected void deleteBuilding(Building b) {
        if (b != null) {
            for (int y = b.getPosition().y; y < b.getPosition().y + b.getSize().height; y++) {
                for (int x = b.getPosition().x; x < b.getPosition().x + b.getSize().width; x++) {
                    mapRef.getTiles()[y][x].getEntities().clear();
                }
            }
        }
    }

    protected void trainSoldiers(Soldier s) {
        if (game.getGameState().getCurrentPlayer().getGold() >= s.getValue()) {
            int xIdx = s.getPosition().x;
            int yIdx = s.getPosition().y;
            String side = xIdx + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side
            s.setSide(side);
            if (isInTrainingGround(xIdx, yIdx, s, side)) {
                Point point = closestEmptyTile(xIdx, yIdx);
                mapRef.getTiles()[point.y][point.x].addEntities(s);
                game.getGameState().getCurrentPlayer().addEntity(s);
                controlPanel.updateButtonText();
            }
        }
    }

    protected void placeBuilding(Building b) { // Entity switched building
        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        String side = xIdx + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side
        b.setSide(side);
        if (inverted)
            b.invert();
        System.out.println(mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains(b.getType()));
        String playerSide = game.getGameState().getCurrentPlayer().getPlayerNumber() == 1 ? "left" : "right";
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {
            if (isBuildable(xIdx, yIdx, b.getSize(), side)) {
                for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
                    for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                        if (game.getGameState().getCurrentPlayer().getGold() >= b.getValue()) {
                            mapRef.getTiles()[y][x].addEntities(b);
                        }
                    }
                }

                if (game.getGameState().getCurrentPlayer().getGold() >= b.getValue()) {
                    game.getGameState().getCurrentPlayer().addEntity(b);
                    controlPanel.updateButtonText();
                }
            } else if (mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains(b.getType())
                    && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                if ((game.getGameState().getCurrentPlayer().getGold() >= 30 && b.getType().equals("Barracks"))
                        || (game.getGameState().getCurrentPlayer().getGold() >= (((Tower) b).getLevel() * 5 + 10) && "Shotgun Sniper".contains(b.getType()))) {
                    game.getGameState().getCurrentPlayer().upgradeBuilding((Building) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0));
                    this.controlPanel.updateButtonText();
                    repaint();
                }

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains("Shotgun")
                    && Objects.equals(b.getType(), "Sniper") && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains("Sniper")
                    && Objects.equals(b.getType(), "Shotgun") && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx);

            } else if (game.getGameState().getCurrentPlayer().getGold() >= 20
                    && mapRef.getTiles()[yIdx][xIdx].getEntities().stream().map(Entity::getType).toList().contains("Barricade")
                    && (Objects.equals(b.getType(), "Shotgun") || Objects.equals(b.getType(), "Barricade"))
                    && Objects.equals(mapRef.getTiles()[yIdx][xIdx].getEntities().get(0).getSide(), playerSide)) {
                transformTower(b, xIdx, yIdx);
            }
        }


    }


    private void transformTower(Building b, int xIdx, int yIdx) {
        System.out.println("TRANSFORM");
        Building newTower = game.getGameState().getCurrentPlayer().transformTower((Tower) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0), b.getType());
        deleteBuilding((Building) mapRef.getTiles()[yIdx][xIdx].getEntities().get(0));
        placeBuilding(newTower);
        repaint();
    }

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
            controlPanel.updateButtonText();
            deleteState = false;
            repaint();
        }
    }


}
