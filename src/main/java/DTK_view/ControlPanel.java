package DTK_view;

import DTK_model.Game;
import DTK_model.RoundState;

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
    private final JRoundedButton[] buttons = new JRoundedButton[7];
    /**
     * The inner panel of the panel.
     */
    JPanel innerPanel = new JPanel();
    /**
     * Game dependency.
     */
    private final Game game;
    /**
     * GridBagLayout for panels.
     */
    GridBagLayout gridBagLayout = new GridBagLayout();

    /**
     * Constructs a new ControlPanel instance with dependency injection.
     *
     * @param game Game dependency injection
     */
    public ControlPanel(Game game) {
        super();
        this.game = game;
        setLayout(new BorderLayout());
        innerPanel.setLayout(gridBagLayout);
        add(innerPanel, BorderLayout.WEST);
        innerPanel.setOpaque(false);
        backgroundColor = new Color(185, 185, 185, 200);
        borderColor = Color.white;
        setOpaque(false);
        createButtons();
        resize();
    }

    /**
     * Returns the buttons of the panel.
     *
     * @return the buttons of the panel
     */
    public JRoundedButton[] getButtons() {
        return buttons;
    }

    /**
     * Resizes the panel and buttons.
     */
    public void resize() {
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        for (int i = 0; i < buttons.length; i++) {
            JRoundedButton button = buttons[i];
            button.setPreferredSize(new Dimension((i == 5 && !button.getText().equals("") ? 2 : 1) * (GameFieldRenderer.dockScale() + 20), GameFieldRenderer.dockScale() + 20));
        }
        validate();
    }

    /**
     * Creates the buttons in the panel.
     */
    private void createButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 10, 0, 10);

        for (int i = 0; i < 6; i++) {
            if (i == 0)
                c.insets.left = 20;
            else if (i == 1)
                c.insets.left = 5;

            buttons[i] = new JRoundedButton(GameFieldRenderer.scale + 20, GameFieldRenderer.scale + 20);
            gridBagLayout.setConstraints(buttons[i], c);
            innerPanel.add(buttons[i]);
        }

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(gridBagLayout);
        sidePanel.setOpaque(false);
        this.add(sidePanel, BorderLayout.LINE_END);


        buttons[6] = new JRoundedButton(String.valueOf(game.getGameState().getCurrentPlayer().getGold()), 50, 50, new Color[]{
                new Color(255, 205, 0),
                new Color(255, 205, 0),
                new Color(255, 205, 0)}, 50, 50);

        c.insets.right = 20;
        gridBagLayout.setConstraints(buttons[6], c);

        sidePanel.add(buttons[6]);
        sidePanel.setOpaque(false);
        this.add(sidePanel, BorderLayout.LINE_END);
    }

    /**
     * Updates the buttons text in the panel.
     */
    public void updateButtonText() {
        buttons[5].setText(Objects.equals(game.getGameState().getRoundState(), RoundState.BUILDING) ? "Train" : "End turn");
        if (!buttons[6].getText().equals("X"))
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

    /**
     * Hides the panel.
     */
    public void hideControlPanel() {
        setVisible(false);
    }

    /**
     * Shows the panel.
     */
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
