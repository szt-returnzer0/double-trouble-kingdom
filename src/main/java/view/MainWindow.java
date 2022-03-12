package view;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    public MainWindow() {
        JFrame frame = new JFrame("Double Trouble Kingdom");
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        MainMenu mainMenu = new MainMenu();
        frame.add(mainMenu, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
