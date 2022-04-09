package view;

import model.*;
import persistence.Database;
import persistence.FileHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Implementation of the game's main window.
 */
public class MainWindow {
    /**
     * Main menu dependency.
     */
    private static MainMenu mainMenu;
    /**
     * The frame of the main window.
     */
    private final JFrame frame;

    /**
     * Constructs a MainWindow instance.
     */
    public MainWindow() {
        frame = new JFrame("Double Trouble Kingdom");
        Dimension windowSize = new Dimension(1920, 1080);
        frame.setPreferredSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        mainMenu = new MainMenu(this);
        frame.add(mainMenu, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Starts main menu.
     *
     * @param frame the parent frame
     */
    public static void startMainMenu(JFrame frame) {
        frame.remove(frame);
        frame.add(mainMenu, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Starts a new Game
     *
     * @param xMax the width of the game Map
     * @param yMax the height of the game Map
     */
    public void startGame(int xMax, int yMax) {
        frame.remove(mainMenu);

        Database db = new Database();
        Game game = new Game(db, FileHandler.loadMap(mainMenu.getMap().getFile()), mainMenu.getP1Name(), mainMenu.getP2Name());
        GameField gameField = new GameField(game, frame);

        frame.add(gameField, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Test method to generate maps.
     *
     * @param xMax the width of the game Map
     * @param yMax the height of the game Map
     * @return a randomized map of tiles
     */
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

    /**
     * Starts a new Map editor
     *
     * @param xMax the width of the game Map
     * @param yMax the height of the game Map
     */
    public void startEditor(int xMax, int yMax) {
        frame.remove(mainMenu);

        Map map = new Map((String) JOptionPane.showInputDialog(
                frame,
                "Add meg a pálya nevét",
                "Pályanév",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Új Pálya"), new Terrain[yMax][xMax]);
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                map.getTiles()[y][x] = new Plains(new Point(x, y));
            }
        }
        Game dummyGame = new Game(null, map, "", "");
        MapEditorView mapEditor = new MapEditorView(dummyGame, frame);

        frame.add(mapEditor, BorderLayout.CENTER);
        frame.pack();
    }
}
