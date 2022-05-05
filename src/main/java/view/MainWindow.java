package view;

import model.Game;
import model.Map;
import model.Plains;
import model.Terrain;
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

        Database db = new Database();
        Game game = new Game(db, FileHandler.loadMap(mainMenu.getMap().getFile()), mainMenu.getP1Name(), mainMenu.getP2Name());
        GameField gameField = new GameField(game, frame);

        frame.add(gameField, BorderLayout.CENTER);
        frame.pack();
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
        generateBlankMap(xMax, yMax, map);
        Game editor = new Game(map);
        MapEditorView mapEditor = new MapEditorView(editor, frame);

        frame.add(mapEditor, BorderLayout.CENTER);
        frame.pack();
    }

    private void generateBlankMap(int xMax, int yMax, Map map) {
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
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
