package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;


public class MapEditorView extends GameField {
    private final boolean pressed = false;
    private String type = "Plains";
    private Timer timer;
    private final Castle[] castles = new Castle[]{null, null};
    private final Barracks[] barracks = new Barracks[]{null, null};
    private boolean inverted = false;
    public MapEditorView(Game dummyGame, JFrame frame) {
        super(dummyGame, frame);
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

    private void setupButtons() {

        this.controlPanel.changeButtonColor(0, Color.green, 2);
        this.controlPanel.attachActionListener(0, e -> type = "Plains");
        this.controlPanel.changeButtonColor(1, Color.blue, 2);
        this.controlPanel.attachActionListener(1, e -> type = "Swamp");
        this.controlPanel.changeButtonColor(2, Color.darkGray, 2);
        this.controlPanel.attachActionListener(2, e -> type = "Mountain");
        this.controlPanel.changeButtonColor(3, Color.yellow, 2);
        this.controlPanel.attachActionListener(3, e -> type = "Desert");

        this.controlPanel.changeButtonColor(4, Color.lightGray, 2);
        this.controlPanel.attachActionListener(4, e -> type = "Castle");
        this.controlPanel.changeButtonColor(5, new Color(64, 37, 19), 2);
        this.controlPanel.attachActionListener(5, e -> type = "Barracks");

        this.hamburgerMenu.attachActionListener(4, e -> System.exit(0));

    }

    private void placeBlock(int x, int y) {
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
                    //case "Castle" -> placeCastle(xIdx, yIdx);
                    //case "Castle" -> placeBuilding(new Castle(new Point(xIdx, yIdx),""));
                    case "Castle" -> placeLimitedBuilding(new Castle(new Point(xIdx, yIdx), ""));
                    case "Barracks" -> placeLimitedBuilding(new Barracks(new Point(xIdx, yIdx), ""));
                }

            } catch (Exception e) {
            }
    }

    private boolean isEmpty(int xIdx, int yIdx, Dimension size) { //Will probably be moved to GameField
        boolean isEmpty = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isEmpty = isEmpty && mapRef[y][x].getEntities().isEmpty();
            }
        }
        return isEmpty;
    }

    private boolean notOnOtherBuilding(int xIdx, int yIdx, Dimension size) {
        boolean onBuilding = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                onBuilding = onBuilding && (hasMatchingTypes(mapRef[y][x].getEntities()) || mapRef[y][x].getEntities().isEmpty());//hasNoMatchingTypes(mapRef[y][x].getEntities(), type);
            }
        }
        return onBuilding;
    }

    private void updateSelection(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        Building tmp;
        switch (type) {
            case "Castle" -> tmp = new Castle(new Point(xIdx, yIdx), "");
            case "Barracks" -> tmp = new Barracks(new Point(xIdx, yIdx), "");
            default -> tmp = null;
        }

        if (inverted && tmp != null) tmp.invert();
        setSelection(tmp);
        repaint();
    }

    private boolean hasMatchingTypes(ArrayList<Entity> ent) {
        boolean result = false;
        for (Entity entity : ent) {
            result = result || Objects.equals(entity.getType(), this.type);
        }
        return result;
    }

    private void deleteBuilding(Building b) { //Will probably be moved to GameField
        if (b != null) {
            for (int y = b.getPosition().y; y < b.getPosition().y + b.getSize().height; y++) {
                for (int x = b.getPosition().x; x < b.getPosition().x + b.getSize().width; x++) {
                    mapRef[y][x].getEntities().clear();
                }
            }
        }
    }

    private void placeBuilding(Building b) { //Will probably be used in GameField
        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        String side = xIdx + 3 < xLength / 2 ? "left" : "right";
        b.setSide(side);
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && isEmpty(xIdx, yIdx, b.getSize())) {
            for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
                for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                    mapRef[y][x].addEntities(b);
                }
            }
        }
    }

    private void placeLimitedBuilding(Building b) {
        int xIdx = b.getPosition().x;
        int yIdx = b.getPosition().y;
        String side = xIdx < xLength / 2 ? "left" : "right";
        b.setSide(side);
        if (inverted)
            b.invert();
        Building[] arr = Objects.equals(b.getType(), "Castle") ? castles : barracks;
        if (xIdx + b.getSize().width <= xLength && yIdx + b.getSize().height <= yLength && notOnOtherBuilding(xIdx, yIdx, b.getSize()) && !(xIdx > xLength / 2.0 - 1 - (b.getSize().width) && xIdx < xLength / 2.0)) {
            if (side.equals("left")) {
                deleteBuilding(arr[0]);
                arr[0] = b;
            } else {
                deleteBuilding(arr[1]);
                arr[1] = b;
            }
            for (int y = yIdx; y < yIdx + b.getSize().height; y++) {
                for (int x = xIdx; x < xIdx + b.getSize().width; x++) {
                    mapRef[y][x].addEntities(b);
                }
            }

        }
    }

}
