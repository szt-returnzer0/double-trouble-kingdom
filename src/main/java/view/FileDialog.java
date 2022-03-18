package view;

import model.Game;
import model.Map;
import persistence.FileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileDialog {
    private final JFileChooser fileChooser;

    public FileDialog() {
        this.fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Save", "dtk"));
    }

    public void saveDialog(Map mapRef) {
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            FileHandler.saveToFile(fileChooser.getSelectedFile(), mapRef);
    }

    public Map loadMapDialog() {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            return FileHandler.loadMap(fileChooser.getSelectedFile());
        else
            return null;
    }

    public Game loadGameDialog() {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            return FileHandler.loadGameState(fileChooser.getSelectedFile());
        else
            return null;
    }
}
