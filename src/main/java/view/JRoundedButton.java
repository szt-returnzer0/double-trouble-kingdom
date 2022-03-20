package view;

import javax.swing.*;
import java.awt.*;

/**
 * Implementation of JRoundedButtons component.
 */
public class JRoundedButton extends JButton {
    /**
     * The text of the button.
     */
    private String text;
    /**
     * The selected state color of the button.
     */
    private Color selectedColor = new Color(92, 200, 230, 185);
    /**
     * The disabled background color of the button.
     */
    private Color notEnabled_bgColor = new Color(46, 154, 200, 130);
    /**
     * The enabled background color of the button.
     */
    private Color enabled_bgColor = new Color(16, 124, 170, 185);
    /**
     * The arc width of the button.
     */
    private int arcWidth = 25;
    /**
     * The arc height of the button.
     */
    private int arcHeight = 25;

    /**
     * Constructs a new JRoundedButton instance.
     *
     * @param text the text of the button
     * @param w    the width of the button
     * @param h    the height of the button
     */
    public JRoundedButton(String text, int w, int h) {
        super(text);
        this.text = text;
        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    /**
     * Constructs a new JRoundedButton instance with non-default colors.
     *
     * @param text   the text of the button
     * @param w      the width of the button
     * @param h      the height of the button
     * @param colors color array with colors to set the three color types
     */
    public JRoundedButton(String text, int w, int h, Color[] colors) {
        super(text);
        this.text = text;
        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        for (int i = 0; i < colors.length; i++) {
            switch (i) {
                case 0 -> selectedColor = colors[i];
                case 1 -> notEnabled_bgColor = colors[i];
                case 2 -> enabled_bgColor = colors[i];
            }
        }
    }

    /**
     * Constructs a new JRoundedButton instance with non-default colors and non-default rounding.
     *
     * @param text   the text of the button
     * @param w      the width of the button
     * @param h      the height of the button
     * @param colors color array with colors to set the three color types
     * @param arcW   the arc width we want
     * @param arcH   the arc height we want
     */
    public JRoundedButton(String text, int w, int h, Color[] colors, int arcW, int arcH) {
        super(text);
        this.text = text;
        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        for (int i = 0; i < colors.length; i++) {
            switch (i) {
                case 0 -> selectedColor = colors[i];
                case 1 -> notEnabled_bgColor = colors[i];
                case 2 -> enabled_bgColor = colors[i];
            }
        }
        arcWidth = arcW;
        arcHeight = arcH;
    }

    /**
     * Sets the buttons color at the given type.
     *
     * @param idx   the index of the type
     * @param color the color we want to set
     */
    public void setColor(int idx, Color color) {
        switch (idx) {
            case 0 -> selectedColor = color;
            case 1 -> notEnabled_bgColor = color;
            case 2 -> enabled_bgColor = color;
            default -> selectedColor = notEnabled_bgColor = enabled_bgColor = Color.pink;
        }
    }

    /**
     * Sets the buttons all color types.
     *
     * @param colors array of the color we want to set
     */
    public void setColors(Color[] colors) {
        for (int i = 0; i < colors.length; i++) {
            switch (i) {
                case 0 -> selectedColor = colors[i];
                case 1 -> notEnabled_bgColor = colors[i];
                case 2 -> enabled_bgColor = colors[i];
            }
        }
    }

    /**
     * Sets to button to transparent.
     */
    public void setTransparent() {
        selectedColor = new Color(0, 0, 0, 0);
        notEnabled_bgColor = new Color(0, 0, 0, 0);
        enabled_bgColor = new Color(0, 0, 0, 0);
    }

    /**
     * Returns the button's text.
     *
     * @return the button's text
     */
    @Override
    public String getText() {
        return this.text;
    }

    /**
     * Sets the buttons text.
     *
     * @param text the text we want to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * The paintComponent method of the class.
     *
     * @param g graphics we use
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (!this.isEnabled()) {
            g2d.setColor(notEnabled_bgColor);
        } else if (!this.getModel().isArmed()) {
            g2d.setColor(enabled_bgColor);
        } else {
            g2d.setColor(selectedColor);
        }
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        if (text.length() > 0) {
            Font font = new Font("Roboto", Font.PLAIN, 20);
            int width = g.getFontMetrics(font).stringWidth(text);
            g2d.setColor(Color.white);
            g2d.setFont(font);
            g2d.drawString(text, getWidth() / 2 - (int) Math.ceil(width / 2.0), getHeight() / 2 + 6);
        }
        g2d.dispose();
    }
}