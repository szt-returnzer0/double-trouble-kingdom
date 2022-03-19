package view;

import model.Map;
import persistence.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainMenu extends JPanel {
    private final String starterMap;
    private final JLabel playersLabel;
    private final JLabel mapNameLabel;
    private String p1Name;
    private String p2Name;
    private Map map;

    public MainMenu(MainWindow wnd) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        p1Name = "Játékos1";
        p2Name = "Játékos2";
        starterMap = "test01.dtk";

        map = getFreshMap();
        this.playersLabel = new JLabel(p1Name + " vs " + p2Name, SwingConstants.CENTER);
        topBar.add(playersLabel);
        this.mapNameLabel = new JLabel("Pálya: " + map.name(), SwingConstants.CENTER);
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
            this.map = fileDialog.loadMapDialog();
            updateMapName();
            repaint();
        });
        bottomButtons.add(mapSelect);
        this.add(bottomButtons);
    }

    public Map getFreshMap() {
        return FileHandler.loadMap(new File(starterMap));
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public JLabel getMapNameLabel() {
        return mapNameLabel;
    }

    public void updateName() {
        this.playersLabel.setText("" + p1Name + " vs " + p2Name);
        repaint();
    }

    public void updateMapName() {
        this.mapNameLabel.setText("Pálya: " + map.getName());
        repaint();
    }
}
