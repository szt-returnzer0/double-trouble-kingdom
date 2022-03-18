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
    }

    public void saveMapDialog(Map map) {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Map", "dtk"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            FileHandler.saveMap(fileChooser.getSelectedFile(), map);
    }


    public void saveGameDialog(Game game) {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Save", "dtk_save"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            FileHandler.saveGame(fileChooser.getSelectedFile(), game);
    }

    public Map loadMapDialog() {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Map", "dtk"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            return FileHandler.loadMap(fileChooser.getSelectedFile());
        else
            return null;
    }

    public Game loadGameDialog() {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Save", "dtk_save"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            return FileHandler.loadGame(fileChooser.getSelectedFile());
        else
            return null;
    }
}
