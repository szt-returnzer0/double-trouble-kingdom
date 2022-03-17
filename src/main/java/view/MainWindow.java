package view;

import model.*;
import persistence.Database;
import persistence.FileHandler;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private final JFrame frame;
    private final MainMenu mainMenu;
    Terrain[][] map;
    String p1Name;
    String p2Name;

    public MainWindow() {
        frame = new JFrame("Double Trouble Kingdom");
        Dimension windowSize = new Dimension(1920, 1080);
        frame.setPreferredSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        map = FileHandler.loadMap("test-map01");
        p1Name = "P1";
        p2Name = "P2";
        mainMenu = new MainMenu(this, p1Name, p2Name, "Alapértelmezett");

        frame.add(mainMenu, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void startGame(int xMax, int yMax) {
        frame.remove(mainMenu);

        Database db = new Database();
        //Terrain[][] map = randomTerrain(xMax, yMax);
        Game game = new Game(db, map, p1Name, p2Name);
        GameField gameField = new GameField(game, frame);

        frame.add(gameField, BorderLayout.CENTER);
        frame.pack();
    }

    private Terrain[][] randomTerrain(int xMax, int yMax) {
        Terrain[][] map = new Terrain[yMax][xMax];
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                switch ((int) (Math.random() * 4)) {
                    case 0 -> map[y][x] = new Plains(new Point(x, y));
                    case 1 -> map[y][x] = new Swamp(new Point(x, y));
                    case 2 -> map[y][x] = new Desert(new Point(x, y));
                    case 3 -> map[y][x] = new Mountain(new Point(x, y));
                }
            }
        }
        return map;
    }

    public void startEditor(int xMax, int yMax) {
        frame.remove(mainMenu);

        Terrain[][] map = new Terrain[yMax][xMax];
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                map[y][x] = new Plains(new Point(x, y));
            }
        }
        Game dummyGame = new Game(null, map, "", "");
        MapEditorView mapEditor = new MapEditorView(dummyGame, frame);

        frame.add(mapEditor, BorderLayout.CENTER);
        frame.pack();
    }
}
