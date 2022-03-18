package view;

import model.Map;
import persistence.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainMenu extends JPanel {
    String p1Name;
    String p2Name;
    Map map;
    JLabel players;
    JLabel mapName;

    public MainMenu(MainWindow wnd) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        p1Name = "Játékos1";
        p2Name = "Játékos2";
        map = FileHandler.loadMap(new File("test01.dtk"));

        this.players = new JLabel(p1Name + " vs " + p2Name, SwingConstants.CENTER);
        topBar.add(players);
        this.mapName = new JLabel("Pálya: " + map.name(), SwingConstants.CENTER);
        topBar.add(mapName);
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
            this.map = fileDialog.loadDialog();
            updateMapName();
            repaint();
        });
        bottomButtons.add(mapSelect);
        this.add(bottomButtons);
    }

    public Map getMap() {
        return map;
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public JLabel getMapName() {
        return mapName;
    }

    public void updateName() {
        this.players.setText("" + p1Name + " vs " + p2Name);
        repaint();
    }

    public void updateMapName() {
        this.mapName.setText("Pálya: " + map.getName());
        repaint();
    }
}
