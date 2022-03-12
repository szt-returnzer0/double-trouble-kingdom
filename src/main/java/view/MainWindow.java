package view;

import model.*;
import persistence.Database;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private final JFrame frame;
    private final MainMenu mainMenu;

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

    public void startGame() {
        frame.remove(mainMenu);

        Database db = new Database();
        Terrain[][] map = new Terrain[32][64];
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 64; x++) {
                switch ((int) (Math.random() * 4)) {
                    case 0 -> map[y][x] = new Plains(new Point(x, y));
                    case 1 -> map[y][x] = new Swamp(new Point(x, y));
                    case 2 -> map[y][x] = new Desert(new Point(x, y));
                    case 3 -> map[y][x] = new Mountain(new Point(x, y));
                    default -> {
                    }
                }
            }
        }

        String p1Name = "Játékos1";
        String p2Name = "Játékos2";
        Game game = new Game(db, map, p1Name, p2Name);
        GameField gameField = new GameField(game, frame);

        frame.add(gameField, BorderLayout.CENTER);
        frame.pack();
    }
}
