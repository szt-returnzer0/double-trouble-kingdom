package DTK_view;

import DTK_model.*;
import DTK_persistence.Database;
import DTK_persistence.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
    private static final JFrame frame = new JFrame("Double Trouble Kingdom");


    /**
     * Constructs a MainWindow instance.
     */
    public MainWindow() {
        Dimension windowSize = new Dimension(1920, 1080);
        frame.setPreferredSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        mainMenu = new MainMenu(this);
        frame.add(mainMenu, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
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
     */
    public void startGame() {
        frame.remove(mainMenu);

        Database database = new Database();
        Pair<Map, File> map = mainMenu.getMap();

        Game game = new Game(database, map.getFile() == null ? map.getMap() : FileHandler.loadMap(map.getFile()), mainMenu.getP1Name(), mainMenu.getP2Name());
        GameField gameField = new GameField(game, frame);

        frame.add(gameField, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Starts a new Map editor
     *
     * @param maxWidth  the width of the game Map
     * @param maxHeight the height of the game Map
     */
    public void startEditor(int maxWidth, int maxHeight) {
        frame.remove(mainMenu);

        Map map = new Map((String) JOptionPane.showInputDialog(
                frame,
                "Add meg a pálya nevét",
                "Pályanév",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Új Pálya"), new Terrain[maxHeight][maxWidth]);
        generateBlankMap(maxWidth, maxHeight, map);
        Game editor = new Game(map);
        MapEditorView mapEditor = new MapEditorView(editor, frame);

        frame.add(mapEditor, BorderLayout.CENTER);
        frame.pack();
    }


    /**
     * Generates a blank map.
     *
     * @param maxWidth  the width of the game Map
     * @param maxHeight the height of the game Map
     * @param map       the game Map
     */
    private void generateBlankMap(int maxWidth, int maxHeight, Map map) {
        for (int y = 0; y < maxHeight; y++) {
            for (int x = 0; x < maxWidth; x++) {
                map.getTiles()[y][x] = new Plains();
            }
        }
    }

    /**
     * Repacks the windows
     */
    public static void repack() {
        frame.pack();
    }

    /**
     * Returns the dimensions of the main window.
     *
     * @return the dimensions of the main window
     */
    public static Dimension getDimension() {
        return frame.getPreferredSize();
    }

    /**
     * Sets the dimensions of the main window.
     *
     * @param dimension the dimensions of the main window
     */
    public static void setDimension(Dimension dimension) {
        frame.setPreferredSize(dimension);
    }
}