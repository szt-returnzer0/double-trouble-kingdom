package DTK_view;

import DTK_model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Implementation of the GameField class, which displays the game table.
 */
public class GameField extends GameFieldRenderer {
    /**
     * The selection type.
     */
    protected Types type = Types.NONE;

    /**
     * Determines if the selection is inverted.
     */
    protected boolean inverted = false;
    /**
     * Determines if delete mode is on.
     */
    private boolean deleteState;

    /**
     * Model of the GameField.
     */
    protected GameFieldModel gameFieldModel;

    /**
     * Timer to refresh the game field.
     */
    protected Timer refreshTimer;


    /**
     * Constructs a new GameField instance.
     *
     * @param game  Game dependency injection
     * @param frame the parent frame
     */
    public GameField(Game game, JFrame frame) {
        super(game, frame);

        this.game = game;
        setupButtons();
        gameFieldModel = new GameFieldModel(game);
        middleText = "0 sec";
        sideText = getRoundStateText();

        refreshTimer = new Timer((int) (1000.0 / 60), e -> { updateUIState(); repaint();});
        refreshTimer.start();


        if (game.getDatabase() == null) {
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                        setSelection(null);
                        draw(e);
                    }

                }
            });

        }

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateSelection(e.getX(), e.getY());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (Types.getSoldierTypes().contains(type)) {
                        handleWayPoint(e.getX(), e.getY());
                    } else
                        inverted = !inverted;
                    updateSelection(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    setSelection(null);
                    draw(e);
                }

            }
        });

        repaint();
    }

    /**
     * Handle a waypoint.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    private void handleWayPoint(int x, int y) {
        int yIndex = y / scale;
        int xIndex = x / scale;
        gameFieldModel.handleWayPoint(xIndex, yIndex, selection);
    }

    /**
     * Returns the waypoints.
     *
     * @return the waypoints
     */
    public ArrayList<Point> getWayPoints() {
        return GameFieldModel.getWayPoints();
    }

    /**
     * Updates the UIState.
     */
    public void updateUIState() {
        middleText = "" + game.getGameState().getElapsedTime() + " sec";
        if (!game.getGameState().getRoundState().equals(RoundState.ATTACKING)) {
            sideText = getRoundStateText();
            controlPanel.showControlPanel();
            updateButtons();
        } else
            controlPanel.hideControlPanel();
        if (game.getGameState().isEnded()) {
            controlPanel.hideControlPanel();
        }
        repaint();
    }

    /**
     * Draws or removes new Entities on the canvas.
     */
    private void draw(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (!deleteState)
            placeBlock(x, y);
        else
            delete(x, y);
        repaint();
    }

    /**
     * Deletes an Entity from the canvas and the player's entities
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    private void delete(int x, int y) {
        int yIndex = y / scale;
        int xIndex = x / scale;
        gameFieldModel.delete(xIndex, yIndex);
    }

    /**
     * Sets the panel's buttons
     */
    protected void setupButtons() {
        updateButtons();

        FileDialog fileDialog = new FileDialog();
        this.hamburgerMenu.attachActionListener(0, e -> fileDialog.saveGameDialog(this.game));
        this.hamburgerMenu.attachActionListener(1, e -> {
            refreshGameField();
            repaint();
        });

        this.hamburgerMenu.attachActionListener(5, e -> game.getGameState().togglePathVisualization());

        this.controlPanel.attachActionListener(5, e -> {
            getWayPoints().clear();
            game.getGameState().nextRoundState();
            sideText = getRoundStateText();
            updateButtons();
            setSelection(null);
            if (deleteState)
                toggleDelete();
            type = Types.NO_SELECTION;
        });
        this.controlPanel.setButtonSize(5, new Dimension(100, 50));

        this.controlPanel.attachActionListener(6, e -> {
            toggleDelete();
            setSelection(null);
            type = Types.NO_SELECTION;
        });

    }

    /**
     * Returns the Round state as a String.
     *
     * @return the Round state as a String
     */
    private String getRoundStateText() {
        return !Objects.equals(game.getGameState().getRoundState().text, RoundState.ATTACKING.text)
                ? ("Phase: " + game.getGameState().getRoundState().text + " | Player: "
                + game.getGameState().getCurrentPlayer().getPlayerNumber())
                : ("Phase: " + game.getGameState().getRoundState().text);
    }

    /**
     * Redraws the canvas.
     */
    public void refreshGameField() {
        game.getGameState().stopElapsedTimer();
        FileDialog fileDialog = new FileDialog();
        Game loadedGame = fileDialog.loadGameDialog();
        if (loadedGame != null) {
            this.game = loadedGame;




            frame.remove(this);

            GameField gameField = new GameField(new Game(game), frame);




            frame.add(gameField);
            frame.pack();
            repaint();

        }



    }

    /**
     * Updates the buttons' state.
     */
    private void updateButtons() {
        if (game.getGameState().getRoundState().equals(RoundState.BUILDING)) {
            this.controlPanel.setButtonText(0, "Bar");
            this.controlPanel.attachActionListener(0, e -> {
                type = Types.BARRICADE;
                if (deleteState)
                    toggleDelete();
            });

            this.controlPanel.setButtonText(1, "Sho");
            this.controlPanel.attachActionListener(1, e -> {
                type = Types.SHOTGUN;
                if (deleteState)
                    toggleDelete();
            });
            this.controlPanel.getButtons()[1].setEnabled(true);

            this.controlPanel.setButtonText(2, "Sni");
            this.controlPanel.attachActionListener(2, e -> {
                type = Types.SNIPER;
                if (deleteState)
                    toggleDelete();
            });
            this.controlPanel.getButtons()[2].setEnabled(true);

            this.controlPanel.setButtonText(3, "Brk");
            this.controlPanel.attachActionListener(3, e -> {
                type = Types.BARRACKS;
                if (deleteState)
                    toggleDelete();
            });
            this.controlPanel.getButtons()[3].setEnabled(true);

            this.controlPanel.hideButton(4);

        } else if (game.getGameState().getRoundState().equals(RoundState.TRAINING)) {

            this.controlPanel.setButtonText(0, "Sol");
            this.controlPanel.attachActionListener(0, e -> {type = Types.SOLDIER; GameFieldModel.getWayPoints().clear();});

            if (!game.getGameState().getCurrentPlayer().isUnitRestricted()) {
                this.controlPanel.setButtonText(1, "Kam");
                this.controlPanel.attachActionListener(1, e -> {type = Types.KAMIKAZE; GameFieldModel.getWayPoints().clear();});
                this.controlPanel.getButtons()[1].setEnabled(true);

                this.controlPanel.setButtonText(2, "Ass");
                this.controlPanel.attachActionListener(2, e -> {type = Types.ASSASSIN; GameFieldModel.getWayPoints().clear();});
                this.controlPanel.getButtons()[2].setEnabled(true);

                this.controlPanel.setButtonText(3, "Div");
                this.controlPanel.attachActionListener(3, e -> {type = Types.DIVER; GameFieldModel.getWayPoints().clear();});
                this.controlPanel.getButtons()[3].setEnabled(true);

                this.controlPanel.setButtonText(4, "Cli");
                this.controlPanel.attachActionListener(4, e -> {type = Types.CLIMBER; GameFieldModel.getWayPoints().clear();});
                this.controlPanel.getButtons()[4].setEnabled(true);
            } else {
                for (int i = 1; i < 5; i++) {
                    this.controlPanel.hideButton(i);
                }
            }
        }

        JRoundedButton[] buttons = this.controlPanel.getButtons();
        for (int i = 0, buttonsLength = buttons.length - 2; i < buttonsLength; i++) {
            JRoundedButton button = buttons[i];
            Color tmp;
            switch (button.getText()) {
                case "Brk" -> tmp = new Color(64, 37, 19);
                case "Bar" -> tmp = new Color(208, 146, 110);
                case "Sni" -> tmp = new Color(118, 110, 106);
                case "Sho" -> tmp = new Color(17, 15, 15);
                case "Sol" -> tmp = new Color(190, 30, 30);
                case "Ass" -> tmp = new Color(45, 15, 80);
                case "Kam" -> tmp = new Color(72, 18, 25);
                case "Div" -> tmp = new Color(22, 107, 107);
                case "Cli" -> tmp = new Color(175, 112, 81);
                default -> tmp = Color.gray;
            }
            button.setColors(
                    new Color[] { new Color(Math.min(tmp.getRed() + 30, 255), Math.min(tmp.getGreen() + 30, 255),
                            Math.min(tmp.getBlue() + 30, 255)), new Color(0, 0, 0, 0), tmp });
        }
        controlPanel.updateButtonText();
        repaint();
    }

    /**
     * Places the Entity.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    protected void placeBlock(int x, int y) {
        int yIndex = y / scale;
        int xIndex = x / scale;

        gameFieldModel.placeEntity(xIndex, yIndex, inverted, type);
    }

    /**
     * Updates the current selection to be drawn on the canvas.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    private void updateSelection(int x, int y) {
        int yIndex = y / scale;
        int xIndex = x / scale;
        Entity tmp = null;
        if (Types.getBuildingTypes().contains(type))
            tmp = Types.buildingFactory(type, xIndex, yIndex);
        else if (Types.getSoldierTypes().contains(type))
            tmp = Types.soldierFactory(type, xIndex, yIndex);

        if (inverted && tmp != null)
            tmp.invert();
        setSelection(tmp);
        repaint();
    }

    /**
     * Toggles the delete mode.
     */
    private void toggleDelete() {

        if (RoundState.BUILDING.equals(game.getGameState().getRoundState()) && !deleteState) {

            setDeleteButton();
            deleteState = true;
            repaint();

        } else {
            setGoldButton();
            controlPanel.updateButtonText();
            deleteState = false;
            repaint();
        }
    }

    /**
     * Sets the Button to show the gold mode.
     */
    private void setGoldButton() {
        this.controlPanel.setButtonColors(6, new Color[] {
                new Color(255, 205, 0),
                new Color(255, 205, 0),
                new Color(255, 205, 0) });
        this.controlPanel.setButtonText(6, "0");
    }

    /**
     * Sets the Button to show the delete mode.
     */
    private void setDeleteButton() {
        this.controlPanel.setButtonColors(6, new Color[] {
                new Color(255, 142, 142),
                new Color(70, 0, 0),
                new Color(166, 0, 0) });
        this.controlPanel.setButtonText(6, "X");
    }

}
