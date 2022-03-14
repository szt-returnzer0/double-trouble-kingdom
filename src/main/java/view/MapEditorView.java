package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*public class MapEditorView extends JPanel {
    private final GameField gameField;
    public MapEditorView(Terrain[][] map, JFrame frame) {
        Game dummyGame = new Game(null, map, "", "");
        gameField = new GameField(dummyGame, frame);
        setupButtons();
        frame.add(gameField);
        frame.pack();
    }

    private void setupButtons() {

        gameField.attachActionListener(0,e->System.out.println("megy"));

    }

}*/

public class MapEditorView extends GameField {
    private final boolean pressed = false;
    private String type = "Plains";
    private Timer timer;

    public MapEditorView(Game dummyGame, JFrame frame) {
        super(dummyGame, frame);
        setupButtons();


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                draw(e);

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                draw(e);

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
                    case "Castle" -> placeCastle(xIdx, yIdx);
                }

            } catch (Exception e) {
            }
    }

    private boolean isEmpty(int xIdx, int yIdx, Dimension size) {
        boolean isEmpty = true;
        for (int y = yIdx; y < yIdx + size.height; y++) {
            for (int x = xIdx; x < xIdx + size.width; x++) {
                isEmpty = isEmpty && mapRef[y][x].getEntities().isEmpty();
            }
        }
        return isEmpty;
    }

    private void placeCastle(int xIdx, int yIdx) {
        String side = xIdx + 3 < xLength / 2 ? "left" : "right";
        System.out.println(side);
        Castle c = new Castle(new Point(xIdx, yIdx), side);
        if (xIdx + c.getSize().width <= xLength && yIdx + c.getSize().height <= yLength && isEmpty(xIdx, yIdx, c.getSize())) {
            for (int y = yIdx; y < yIdx + c.getSize().height; y++) {
                for (int x = xIdx; x < xIdx + c.getSize().width; x++) {
                    mapRef[y][x].addEntities(c);
                }
            }

        }


    }

}
