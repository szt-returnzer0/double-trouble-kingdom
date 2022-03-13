package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private final Color backgroundColor;
    private final Color borderColor;
    private final JRoundedButton[] buttons = new JRoundedButton[6]; //for multiple actionListener support

    public ControlPanel() {
        super();

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 8));
        backgroundColor = new Color(185, 185, 185, 200);
        borderColor = Color.white;
        setOpaque(false);
        createButtons();
        repaint();
    }

    public ControlPanel(Color bg, Color br) {
        super(new FlowLayout());
        backgroundColor = bg;
        borderColor = br;
        setOpaque(false);
        createButtons();
    }

    private void createButtons() {
        System.out.println("size: " + getSize());
        /*JRoundedButton[] controls = new JRoundedButton[6];
        for (JRoundedButton e : controls) {
            e = new JRoundedButton("0", 50, 50);
            this.add(e);
        }*/
        /*String[] texts = new String[]{"Teszt", "2", "Block", "4", "5", "6"};
        for (int i = 0; i < texts.length; i++) {
            buttons[i] = new JRoundedButton(texts[i], 50, 50);
            this.add(buttons[i]);
        }*/

        for (int i = 0; i < 6; i++) {
            buttons[i] = new JRoundedButton("", 50, 50);
            this.add(buttons[i]);
        }

        //TESTING PURPOSES
        /*for (int i = 4; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }*/

    }

    public void attachActionListener(int idx, ActionListener e) {
        buttons[idx].addActionListener(e);
    }

    public void changeButtonColor(int idx, Color color, int idx2) {
        buttons[idx].changeColor(idx2, color);
    }

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

    /*private static class JRoundedButton extends JButton {
        private Color selectedColor = new Color(92, 200, 230, 185);
        private Color notEnabled_bgColor = new Color(46, 154, 200, 130);
        private Color enabled_bgColor = new Color(16, 124, 170, 185);
        private final String text;
        public JRoundedButton(String text, int w, int h) {
            super(text);
            this.text = text;
            setPreferredSize(new Dimension(w, h));
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
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
    }*/
}
