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
        Map tileMap = map.getMap();
        if (tileMap == null) {
            tileMap = new Map();
        }


        Game game = new Game(database, map.getMap() == null ? tileMap : FileHandler.loadMap(map.getFile()), mainMenu.getP1Name(), mainMenu.getP2Name());
        GameField gameField = new GameField(game, frame);

        frame.add(gameField, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Checks if the given value is a number.
     *
     * @param strNum the string to check
     * @return true if the string is a number
     */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Starts a new Map editor
     */
    public void startEditor() {
        frame.remove(mainMenu);

        String mapName = (String) JOptionPane.showInputDialog(
                frame,
                "Add meg a pálya nevét",
                "Pályanév",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Új Pálya");


        JTextField height = new JTextField();
        do {
            Object[] message = {
                    "Méret (16-128):", height

            };
            JOptionPane.showConfirmDialog(null, message, "MapSize", JOptionPane.DEFAULT_OPTION);
        } while (!(isNumeric(height.getText()) && Integer.parseInt(height.getText()) >= 16 && Integer.parseInt(height.getText()) <= 128));

        int maxHeight = Integer.parseInt(height.getText());
        int maxWidth = 2 * maxHeight;

        Map map = new Map(mapName, new Terrain[maxHeight][maxWidth]);
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
