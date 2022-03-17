package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class GameField extends GameFieldRenderer {
    protected final boolean pressed = false;
    protected String type = "Plains";
    protected Timer timer;
    protected Game game;
    protected JLabel curPlayer;


    //protected final Barracks[] barracks = new Barracks[]{null, null, null, null};
    protected boolean inverted = false;

    public GameField(Game game, JFrame frame) {
        super(game, frame);
        this.game = game;
        setupButtons();


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                    setSelection(null);
                    draw(e);
                }

            }
        });

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
        placeBlock(x, y);
        repaint();
    }

    protected void setupButtons() {
        updateButtons();
        this.controlPanel.attachActionListener(5, e -> {
            game.getGameState().nextRoundState();
            updateButtons();
        });
    }

    private void updateButtons() {
        if (game.getGameState().getRoundState().equals("Building")) {
            this.controlPanel.setButtonText(0, "Bar");
            this.controlPanel.setButtonColors(0, new Color[]{
                    new Color(224, 136, 65),
                    new Color(58, 52, 46),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(0, e -> type = "Barricade");          //Lerakas

            this.controlPanel.setButtonText(1, "Sho");
            this.controlPanel.setButtonColors(1, new Color[]{
                    new Color(224, 136, 65),
                    new Color(152, 145, 138),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(1, e -> type = "Shotgun");

            this.controlPanel.setButtonText(2, "Sni");
            this.controlPanel.setButtonColors(2, new Color[]{
                    new Color(224, 136, 65),
                    new Color(63, 60, 60),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(2, e -> type = "Sniper");

            this.controlPanel.setButtonText(3, "Brk");
            this.controlPanel.setButtonColors(3, new Color[]{
                    new Color(224, 136, 65),
                    new Color(63, 60, 60),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(3, e -> type = "Barracks");

        } else if (game.getGameState().getRoundState().equals("Training")) {
            this.controlPanel.setButtonText(0, "Sol");
            this.controlPanel.setButtonColors(0, new Color[]{
                    new Color(224, 136, 65),
                    new Color(58, 52, 46),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(0, e -> type = "Soldier");          //Lerakas

            this.controlPanel.setButtonText(1, "Kam");
            this.controlPanel.setButtonColors(1, new Color[]{
                    new Color(224, 136, 65),
                    new Color(152, 145, 138),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(1, e -> type = "Kamikaze");

            this.controlPanel.setButtonText(2, "Ass");
            this.controlPanel.setButtonColors(2, new Color[]{
                    new Color(224, 136, 65),
                    new Color(63, 60, 60),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(2, e -> type = "Assassin");

            this.controlPanel.setButtonText(3, "Div");
            this.controlPanel.setButtonColors(3, new Color[]{
                    new Color(224, 136, 65),
                    new Color(63, 60, 60),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(3, e -> type = "Diver");

            this.controlPanel.setButtonText(4, "Cli");
            this.controlPanel.setButtonColors(4, new Color[]{
                    new Color(224, 136, 65),
                    new Color(63, 60, 60),
                    new Color(175, 100, 49)});
            this.controlPanel.attachActionListener(4, e -> type = "Climber");
        }
        controlPanel.updateButtonText();
        repaint();
    }

    protected void placeBlock(int x, int y) { //Lerakas
        int yIdx = y / scale;
        int xIdx = x / scale;
        Terrain[][] map = mapRef;

        if (yIdx < yLength && xIdx < xLength)
            try {
                ArrayList<Entity> ent = map[yIdx][xIdx].getEntities();
                switch (type) {
                    case "Plains" -> map[yIdx][xIdx] = new Plains(new Point(xIdx, yIdx), ent);
                    case "Swamp" -> map[yIdx][xIdx] = new Swamp(new Point(xIdx, yIdx), ent);
                    case "Mountain" -> map[yIdx][xIdx] = new Mountain(new Point(xIdx, yIdx), ent);
                    case "Desert" -> map[yIdx][xIdx] = new Desert(new Point(xIdx, yIdx), ent);
                    case "Barracks" -> placeBuilding(new Barracks(new Point(xIdx, yIdx), ""));
                    case "Barricade" -> placeBuilding(new Barricade(new Point(xIdx, yIdx), ""));
                    case "Sniper" -> placeBuilding(new Sniper(new Point(xIdx, yIdx), ""));
                    case "Shotgun" -> placeBuilding(new Shotgun(new Point(xIdx, yIdx), ""));
                    case "Soldier" -> placeBuilding(new Soldier(new Point(xIdx, yIdx), 0));
                    case "Assassin" -> placeBuilding(new Assassin(new Point(xIdx, yIdx), 0));
                    case "Kamikaze" -> placeBuilding(new Kamikaze(new Point(xIdx, yIdx), 0));
                    case "Diver" -> placeBuilding(new Diver(new Point(xIdx, yIdx), 0));
                    case "Climber" -> placeBuilding(new Climber(new Point(xIdx, yIdx), 0));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    protected boolean isEmpty(int xIdx, int yIdx, Dimension size) { //Will probably be moved to GameFieldRenderer
        boolean isEmpty = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isEmpty = isEmpty && mapRef[y][x].getEntities().isEmpty();
            }
        }
        return isEmpty;
    }

    protected boolean isBuildable(int xIdx, int yIdx, Dimension size, String side) {
        boolean isBuildable = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isBuildable = isBuildable && mapRef[y][x].getEntities().isEmpty() && !mapRef[y][x].getType().equals("Mountain") && !mapRef[y][x].getType().equals("Swamp");
            }
        }
        if (!((side.equals("left") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 1) || (side.equals("right") && game.getGameState().getCurrentPlayer().getPlayerNumber() == 2)))
            isBuildable = false;
        return isBuildable;
    }
    //

    private void updateSelection(int x, int y) { //Lerakas
        int yIdx = y / scale;
        int xIdx = x / scale;
        Building tmp;
        switch (type) {
            case "Castle" -> tmp = new Castle(new Point(xIdx, yIdx), "");
            case "Barracks" -> tmp = new Barracks(new Point(xIdx, yIdx), "");
            case "Barricade" -> tmp = new Barricade(new Point(xIdx, yIdx), "");
            case "Sniper" -> tmp = new Sniper(new Point(xIdx, yIdx), "");
            case "Shotgun" -> tmp = new Shotgun(new Point(xIdx, yIdx), "");
            default -> tmp = null;
        }

        if (inverted && tmp != null) tmp.invert();
        setSelection(tmp);
        repaint();
    }


    protected void deleteBuilding(Building b) {
        if (b != null) {
            for (int y = b.getPosition().y; y < b.getPosition().y + b.getSize().height; y++) {
                for (int x = b.getPosition().x; x < b.getPosition().x + b.getSize().width; x++) {
                    mapRef[y][x].getEntities().clear();
                }
            }
        }
    }

    protected void placeBuilding(Entity b) { // Entity switched building
        if (game.getGameState().getCurrentPlayer().getGold() >= b.getValue()) {
            int xIdx = b.getPosition().x;
            int yIdx = b.getPosition().y;
            String side = xIdx + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side
            b.setSide(side);
            if (inverted)
                b.invert();

            if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && isEmpty(xIdx, yIdx, b.getSize()) && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0) && isBuildable(xIdx, yIdx, b.getSize(), side)) {
                for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
                    for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                        mapRef[y][x].addEntities(b);
                    }
                }
                game.getGameState().getCurrentPlayer().addEntity(b);
                controlPanel.updateButtonText();
            }
        }

    }


}
