package view;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    public MainWindow() {
        JFrame frame = new JFrame("Double Trouble Kingdom");
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
