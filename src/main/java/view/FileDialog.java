package view;

import model.Game;
import model.Map;
import persistence.FileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Implementation of FileDialog class.
 */
public class FileDialog {
    /**
     * The fileChooser instance we use.
     */
    private final JFileChooser fileChooser;

    /**
     * Constructs a new FileDialog with the game's folder.
     */
    public FileDialog() {
        this.fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
    }

    /**
     * Opens a save dialog to save a Map.
     *
     * @param map the Map we want to save
     */
    public void saveMapDialog(Map map) {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Map", "dtk"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            FileHandler.saveMap(fileChooser.getSelectedFile(), map);
    }


    /**
     * Opens a save dialog to save a Game state.
     *
     * @param game the Game state we want to save
     */
    public void saveGameDialog(Game game) {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Save", "dtk_save"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            FileHandler.saveGame(fileChooser.getSelectedFile(), game);
    }

    /**
     * Opens a load dialog to load a Map.
     *
     * @return the loaded Map
     */
    public Map loadMapDialog() {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Map", "dtk"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            return FileHandler.loadMap(fileChooser.getSelectedFile());
        else
            return null;
    }

    /**
     * Opens a load dialog to load a Game state.
     *
     * @return the loaded Game state
     */
    public Game loadGameDialog() {
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("DTK Save", "dtk_save"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
            return FileHandler.loadGame(fileChooser.getSelectedFile());
        else
            return null;
    }
}
