package view;

import javax.swing.*;
import java.awt.*;

public class JRoundedButton extends JButton {
    private final String text;
    private Color selectedColor = new Color(92, 200, 230, 185);
    private Color notEnabled_bgColor = new Color(46, 154, 200, 130);
    private Color enabled_bgColor = new Color(16, 124, 170, 185);

    public JRoundedButton(String text, int w, int h) {
        super(text);
        this.text = text;
        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

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

    public void changeColor(int idx, Color color) {
        switch (idx) {
            case 0 -> selectedColor = color;
            case 1 -> notEnabled_bgColor = color;
            case 2 -> enabled_bgColor = color;
            default -> selectedColor = notEnabled_bgColor = enabled_bgColor = Color.pink;
        }
    }

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
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
        if (text.length() > 0) {
            g2d.setColor(Color.white);
            g2d.setFont(new Font("Roboto", Font.PLAIN, 20));
            g2d.drawString(text, getWidth() / 2 - text.length() * 5, getHeight() / 2 + 5);
        }
        g2d.dispose();
    }
}