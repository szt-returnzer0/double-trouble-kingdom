package view;

import model.Map;
import model.Pathfinder;
import persistence.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Implementation of MainMenu panel.
 */
public class MainMenu extends JPanel {
    /**
     * The default map name.
     */
    private String mapName;
    /**
     * The label to show the player names.
     */
    private final JLabel playersLabel;
    /**
     * The label to the loaded map's name.
     */
    private final JLabel mapNameLabel;
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
    private Map map;

    /**
     * Constructs a new MainMenu instance.
     *
     * @param wnd the parent window
     */
    public MainMenu(MainWindow wnd) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        p1Name = "Játékos1";
        p2Name = "Játékos2";
        map = FileHandler.loadMap(new File("Test1.dtk"));
        assert map != null;
        mapName = map.getName();

        this.playersLabel = new JLabel(p1Name + " vs " + p2Name, SwingConstants.CENTER);
        topBar.add(playersLabel);
        this.mapNameLabel = new JLabel(map != null ? map.getName() : mapName, SwingConstants.CENTER);
        topBar.add(mapNameLabel);
        this.add(topBar);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Double Trouble Kingdom");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(title);

        JPanel centerButtons = new JPanel();
        centerButtons.setLayout(new FlowLayout());
        JButton start = new JButton("Start");
        centerButtons.add(start);
        start.addActionListener(e -> wnd.startGame(64, 32));

        JButton editor = new JButton("Térképszerkesztő");
        centerButtons.add(editor);
        centerPanel.add(centerButtons);
        this.add(centerPanel);
        editor.addActionListener(e -> wnd.startEditor(64, 32));

        JPanel bottomButtons = new JPanel(new FlowLayout());
        JButton rename = new JButton("Átnevezés");
        rename.addActionListener(e -> {
            this.p1Name = (String) JOptionPane.showInputDialog(
                    null,
                    "1. Játékos neve",
                    "Átnevezés",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "név1");

            this.p2Name = (String) JOptionPane.showInputDialog(
                    null,
                    "2. Játékos neve",
                    "Átnevezés",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "név2");

            updateName();
            repaint();

        });
        bottomButtons.add(rename);
        JButton mapSelect = new JButton("Pályaválasztás");
        FileDialog fileDialog = new FileDialog();
        mapSelect.addActionListener(e -> {
            Map loadedMap = fileDialog.loadMapDialog();
            if (loadedMap != null) {
                this.map = loadedMap;
                this.mapName = loadedMap.getName();

                updateMapName();
                repaint();
            }
        });
        bottomButtons.add(mapSelect);
        this.add(bottomButtons);
        Pathfinder.setMap(map);
        //Diver d = new Diver(new Point(1, 31), 0);
        //Pathfinder pathfinder = new Pathfinder(map);

        //athfinder.genPath(d, "right");
        //pathfinder.startD3(d,"right");
        // int[][] graph = pathfinder.fieldToGraph(d);
        // int[] list = pathfinder.dijkstra(graph, 0, 800);
        // pathfinder.listToPath(list);
    }

    public Map getMap() {
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
        this.mapNameLabel.setText("Pálya: " + map.getName());
        repaint();
    }
}
