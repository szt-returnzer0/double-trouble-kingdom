package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Implementation of ControlPanel component.
 */
public class ControlPanel extends JPanel {

    /**
     * The background color of the panel.
     */
    private final Color backgroundColor;
    /**
     * The border color of the panel.
     */
    private final Color borderColor;
    /**
     * The buttons in the panel.
     */
    private final JRoundedButton[] buttons = new JRoundedButton[7]; //for multiple actionListener support
    /**
     * The inner panel of the panel.
     */
    JPanel innerPanel = new JPanel();
    /**
     * Game dependency.
     */
    private Game game;

    /**
     * Constructs a new ControlPanel instance with dependency injection.
     *
     * @param game Game dependency injection
     */
    public ControlPanel(Game game) {
        super();
        this.game = game;
        setLayout(new BorderLayout());
        innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 8));
        add(innerPanel, BorderLayout.CENTER);
        innerPanel.setOpaque(false);
        backgroundColor = new Color(185, 185, 185, 200);
        borderColor = Color.white;
        setOpaque(false);
        createButtons();
        repaint();
    }

    /**
     * Constructs a new ControlPanel instance.
     */
    public ControlPanel() {
        super();
        setLayout(new BorderLayout());
        innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 8));
        add(innerPanel, BorderLayout.CENTER);
        innerPanel.setOpaque(false);
        backgroundColor = new Color(185, 185, 185, 200);
        borderColor = Color.white;
        setOpaque(false);
        createButtons();
        repaint();
    }

    /**
     * Constructs a new ControlPanel instance with given colors.
     *
     * @param bg the background color
     * @param br the border color
     */
    public ControlPanel(Color bg, Color br) {
        super(new FlowLayout());
        backgroundColor = bg;
        borderColor = br;
        setOpaque(false);
        createButtons();
    }

    /**
     * Returns the buttons of the panel.
     *
     * @return the buttons of the panel
     */
    public JRoundedButton[] getButtons() {
        return buttons;
    }

    //void resize button and panel

    /**
     * Resizes the panel and buttons.
     */
    public void resize() {
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        for (JRoundedButton button : buttons) {
            button.setPreferredSize(new Dimension(GameFieldRenderer.scale + 20, GameFieldRenderer.scale + 20));
        }
    }

    /**
     * Creates the buttons in the panel.
     */
    private void createButtons() {
        //System.out.println("size: " + getSize());

        for (int i = 0; i < 6; i++) {
            buttons[i] = new JRoundedButton("", GameFieldRenderer.scale + 20, GameFieldRenderer.scale + 20);
            innerPanel.add(buttons[i]);
        }

        JPanel sidePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, GameFieldRenderer.scale + 15, 8));
        sidePanel.setOpaque(false);
        this.add(sidePanel, BorderLayout.LINE_END);

        buttons[6] = new JRoundedButton("" + game.getGameState().getCurrentPlayer().getGold(), 50, 50, new Color[]{
                new Color(255, 205, 0),
                new Color(255, 205, 0),
                new Color(255, 205, 0)}, 50, 50);

        sidePanel.add(buttons[6]);
        sidePanel.setOpaque(false);
        this.add(sidePanel, BorderLayout.LINE_END);
    }

    /**
     * Updates the buttons text in the panel.
     */
    public void updateButtonText() {
        buttons[5].setText(Objects.equals(game.getGameState().getRoundState(), "Building") ? "Train" : "End turn");
        buttons[6].setText("" + game.getGameState().getCurrentPlayer().getGold());
    }

    /**
     * Adds an ActionListener to a button in the panel.
     *
     * @param idx the index of the button
     * @param e   the ActionListener we want to add
     */
    public void attachActionListener(int idx, ActionListener e) {
        if (buttons[idx].getActionListeners().length == 0)
            buttons[idx].addActionListener(e);
        else {
            buttons[idx].removeActionListener(buttons[idx].getActionListeners()[0]);
            buttons[idx].addActionListener(e);
        }

    }

    /**
     * Sets a buttons color in the panel.
     *
     * @param idx   the index of the button
     * @param color the color
     * @param idx2  the index of the color type we set
     */
    public void setButtonColor(int idx, Color color, int idx2) {
        buttons[idx].setColor(idx2, color);
    }

    /**
     * Sets a buttons three color types at once.
     *
     * @param idx    the index of the button
     * @param colors the list of colors to set to the color types
     */
    public void setButtonColors(int idx, Color[] colors) {
        buttons[idx].setColors(colors);
    }

    /**
     * Hides a button in the panel.
     *
     * @param idx the index of the button
     */
    public void hideButton(int idx) {
        buttons[idx].setText("");
        buttons[idx].setTransparent();
        buttons[idx].setEnabled(false);
    }

    /**
     * Sets a buttons text.
     *
     * @param idx  the index of the button
     * @param text the text we want to set
     */
    public void setButtonText(int idx, String text) {
        buttons[idx].setText(text);
    }

    /**
     * Sets a buttons size.
     *
     * @param idx the index of the button
     * @param dim the size we want to set
     */
    public void setButtonSize(int idx, Dimension dim) {
        buttons[idx].setPreferredSize(dim);
    }


    public void hideControlPanel() {
        setVisible(false);
    }

    public void showControlPanel() {
        setVisible(true);
    }

    /**
     * The paintComponent method of the class.
     *
     * @param g graphics we use
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 60, 100);
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 60, 100);
        g2d.dispose();
    }
}
