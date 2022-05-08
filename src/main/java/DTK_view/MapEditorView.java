package DTK_view;

import DTK_model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;


/**
 * Implementation of the view of Map editor.
 */
public class MapEditorView extends GameField {
    /**
     * MapEditorModel for logics
     */
    private final MapEditorModel mapEditorModel;

    /**
     * Constructs a new MapEditorView instance.
     *
     * @param editor game dependency, because of inheritance
     * @param frame  the parent frame
     */
    public MapEditorView(Game editor, JFrame frame) {
        super(editor, frame);
        refreshTimer.stop();
        middleText = mapRef.getName();
        mapEditorModel = new MapEditorModel();
        sideText = "Selection: " + type.text;
        Timer repaintTimer = new Timer(17, e ->
                this.repaint()
        );

        this.hamburgerMenu.attachActionListener(6, e ->
                repaintTimer.stop()
        );
        repaintTimer.start();

    }

    /**
     * Sets the view's buttons.
     */
    @Override
    protected void setupButtons() {

        this.controlPanel.setButtonColor(0, Color.green, 2);
        this.controlPanel.attachActionListener(0, e -> sideText = "Selection: " + (type = Types.PLAINS).text);
        this.controlPanel.setButtonColor(1, Color.blue, 2);
        this.controlPanel.attachActionListener(1, e -> sideText = "Selection: " + (type = Types.SWAMP).text);
        this.controlPanel.setButtonColor(2, Color.darkGray, 2);
        this.controlPanel.attachActionListener(2, e -> sideText = "Selection: " + (type = Types.MOUNTAIN).text);
        this.controlPanel.setButtonColor(3, Color.yellow, 2);
        this.controlPanel.attachActionListener(3, e -> sideText = "Selection: " + (type = Types.DESERT).text);

        this.controlPanel.setButtonColor(4, Color.lightGray, 2);
        this.controlPanel.attachActionListener(4, e -> sideText = "Selection: " + (type = Types.CASTLE).text);
        this.controlPanel.setButtonColor(5, new Color(64, 37, 19), 2);
        this.controlPanel.attachActionListener(5, e -> sideText = "Selection: " + (type = Types.BARRACKS).text);

        this.controlPanel.attachActionListener(6, e -> sideText = "Selection: " + (type = Types.DELETE).text);
        this.controlPanel.setButtonColors(6, new Color[]{
                new Color(255, 142, 142),
                new Color(70, 0, 0),
                new Color(166, 0, 0)});
        this.controlPanel.setButtonText(6, "X");

        FileDialog fileDialog = new FileDialog();

        this.hamburgerMenu.attachActionListener(0, e -> fileDialog.saveMapDialog(mapRef));

        this.hamburgerMenu.attachActionListener(1, e -> {
            Pair<Map, File> loaded = fileDialog.loadMapDialog();
            if (loaded != null) {
                mapRef = loaded.getMap();
            }
        });

    }

    /**
     * Places a new tile.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    @Override
    protected void placeBlock(int x, int y) {
        int yIdx = y / scale;
        int xIdx = x / scale;
        mapEditorModel.placeBlock(xIdx, yIdx, gameFieldModel, this.inverted, this.type);
    }

}
