package view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    public MainMenu(MainWindow wnd) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        JLabel players = new JLabel("Játékos1 vs Játékos2", SwingConstants.CENTER);
        topBar.add(players);
        JLabel mapName = new JLabel("Pálya: Kezdő", SwingConstants.CENTER);
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
        bottomButtons.add(rename);
        JButton mapSelect = new JButton("Pályaválasztás");
        bottomButtons.add(mapSelect);
        this.add(bottomButtons);
    }
}
