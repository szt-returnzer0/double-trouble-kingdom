package DTK_view;

import DTK_model.Map;
import DTK_model.Pair;
import DTK_model.Pathfinder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Implementation of MainMenu panel.
 */
public class MainMenu extends JPanel {
    /**
     * The label to show the player names.
     */
    private final JLabel playersLabel;
    /**
     * The label to the loaded map's name.
     */
    private final JLabel mapNameLabel;

    /**
     * The background image of the panel.
     */
    BufferedImage background;
    /**
     * The default map name.
     */
    private String mapName;
    /**
     * The name of player 1.
     */
    private String p1Name;
    /**
     * The name of player 2.
     */
    private String p2Name;
    /**
     * The loaded Map instance.
     */
    private Pair<Map, File> map;

    /**
     * Constructs a new MainMenu instance.
     *
     * @param mainWindow the parent window
     */
    public MainMenu(MainWindow mainWindow) {
        setOpaque(false);

        loadBackground();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        FileDialog fileDialog = new FileDialog();
        map = new Pair<>(new Map(), null);
        mapName = map.getMap().getName();


        p1Name = "Kék";
        p2Name = "Piros";

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        topBar.setOpaque(false);
        this.playersLabel = new JLabel(p1Name + " vs " + p2Name, SwingConstants.CENTER);
        this.playersLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        this.playersLabel.setForeground(Color.WHITE);

        topBar.add(playersLabel);
        this.mapNameLabel = new JLabel(map != null ? map.getMap().getName() : mapName, SwingConstants.CENTER);
        this.mapNameLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        this.mapNameLabel.setForeground(Color.WHITE);
        topBar.add(mapNameLabel);
        this.add(topBar);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);

        JPanel centerButtons = new JPanel();
        centerButtons.setOpaque(false);
        centerButtons.setLayout(new FlowLayout());
        JRoundedButton start = new JRoundedButton("Start", 100, 50);
        centerButtons.add(start);
        start.addActionListener(e -> mainWindow.startGame());

        JRoundedButton editor = new JRoundedButton("Térképszerkesztő", 200, 50);
        centerButtons.add(editor);
        centerPanel.add(centerButtons);
        this.add(centerPanel);
        editor.addActionListener(e -> mainWindow.startEditor(64, 32));

        JPanel bottomButtons = new JPanel(new FlowLayout());
        bottomButtons.setOpaque(false);
        JRoundedButton rename = new JRoundedButton("Átnevezés", 110, 50);
        rename.addActionListener(e -> {
            String name1 = (String) JOptionPane.showInputDialog(null, "1. Játékos neve", "Átnevezés", JOptionPane.PLAIN_MESSAGE, null, null, "név1");
            if (name1 != null) {
                this.p1Name = name1;
            } else
                JOptionPane.showMessageDialog(null, "Nem adta meg az 1. Játékos nevét!", "Hiba", JOptionPane.ERROR_MESSAGE);
            String name2 = (String) JOptionPane.showInputDialog(null, "2. Játékos neve", "Átnevezés", JOptionPane.PLAIN_MESSAGE, null, null, "név2");
            if (name2 != null) {
                this.p2Name = name2;
            } else
                JOptionPane.showMessageDialog(null, "Nem adta meg az 2. Játékos nevét!", "Hiba", JOptionPane.ERROR_MESSAGE);

            updateName();
            repaint();

        });
        bottomButtons.add(rename);
        JRoundedButton mapSelect = new JRoundedButton("Pályaválasztás", 150, 50);
        mapSelect.addActionListener(e -> {
            Pair<Map, File> current = this.map;
            Pair<Map, File> loaded = fileDialog.loadMapDialog();
            if (map != null && loaded != null) {
                this.map = loaded;
                this.mapName = loaded.getMap().getName();
            } else {
                this.map = current;
                this.mapName = current.getMap().getName();
            }
            Pathfinder.setMap(map.getMap());
            updateMapName();
            repaint();
        });
        bottomButtons.add(mapSelect);
        this.add(bottomButtons);
        setButtonColors(start, editor, rename, mapSelect);
        Pathfinder.setMap(map.getMap());
    }

    private void loadBackground() {
        try {
            background = ImageIO.read(new File("./src/main/resources/MainMenu.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets the colors of the buttons
     *
     * @param start     the start button
     * @param editor    the editor button
     * @param rename    the rename button
     * @param mapSelect the map select button
     */
    private void setButtonColors(JRoundedButton start, JRoundedButton editor, JRoundedButton rename, JRoundedButton mapSelect) {
        setColors(start, new Color(116, 231, 4));
        setColors(editor, new Color(180, 26, 26));
        setColors(rename, new Color(208, 198, 10));
        setColors(mapSelect, new Color(124, 7, 201));
    }

    /**
     * Returns the map.
     *
     * @return the map
     */
    public Pair<Map, File> getMap() {
        return map;
    }

    /**
     * Returns player 1's name.
     *
     * @return player 1's name
     */
    public String getP1Name() {
        return p1Name;
    }

    /**
     * Returns player 2's name.
     *
     * @return player 2's name
     */
    public String getP2Name() {
        return p2Name;
    }

    /**
     * Updates the players' name
     */
    public void updateName() {
        this.playersLabel.setText("" + p1Name + " vs " + p2Name);
        repaint();
    }

    /**
     * Changes the Map to be loaded.
     */
    public void updateMapName() {
        this.mapNameLabel.setText("Pálya: " + map.getMap().getName());
        repaint();
    }

    /**
     * Draws the background.
     *
     * @param g graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

    }

    /**
     * Sets the colors of the buttons.
     *
     * @param button the button to be changed
     * @param color  the color to be set
     */
    private void setColors(JRoundedButton button, Color color) {
        button.setColors(new Color[]{new Color(Math.min(color.getRed() + 30, 255), Math.min(color.getGreen() + 30, 255), Math.min(color.getBlue() + 30, 255)), new Color(0, 0, 0, 0), color});
    }
}
