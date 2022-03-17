package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private final Color backgroundColor;
    private final Color borderColor;
    private final JRoundedButton[] buttons = new JRoundedButton[7]; //for multiple actionListener support
    private Game game;
    JPanel innerPanel = new JPanel();

    public JRoundedButton[] getButtons() {
        return buttons;
    }

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

        for (int i = 0; i < 5; i++) {
            buttons[i] = new JRoundedButton("", 50, 50);
            innerPanel.add(buttons[i]);
        }
        buttons[5] = new JRoundedButton("" + game.getGameState().getRoundState() + " " + game.getGameState().getCurrentPlayer().getPlayerNumber(), 100, 50, new Color[]{
                new Color(100, 74, 34),
                new Color(100, 74, 34),
                new Color(100, 74, 34)}, 25, 25);

        JPanel sidePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 8));
        sidePanel.add(buttons[5]);
        sidePanel.setOpaque(false);
        this.add(sidePanel, BorderLayout.LINE_END);

        buttons[6] = new JRoundedButton("" + game.getGameState().getCurrentPlayer().getGold(), 50, 50, new Color[]{
                new Color(255, 205, 0),
                new Color(255, 205, 0),
                new Color(255, 205, 0)}, 50, 50);

        sidePanel.add(buttons[6]);
        sidePanel.setOpaque(false);
        this.add(sidePanel, BorderLayout.LINE_END);

        //TESTING PURPOSES
        /*for (int i = 4; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }*/

    }

    public void updateButtonText() {
        buttons[5].setText("" + game.getGameState().getRoundState() + " " + game.getGameState().getCurrentPlayer().getPlayerNumber());
        buttons[6].setText("" + game.getGameState().getCurrentPlayer().getGold());
    }

    public void attachActionListener(int idx, ActionListener e) {
        if (buttons[idx].getActionListeners().length == 0)
            buttons[idx].addActionListener(e);
        else
            buttons[idx].getActionListeners()[0] = e;
    }

    public void setButtonColor(int idx, Color color, int idx2) {
        buttons[idx].setColor(idx2, color);
    }

    public void setButtonColors(int idx, Color[] colors) {
        buttons[idx].setColors(colors);
    }

    public void setButtonText(int idx, String text) {
        buttons[idx].setText(text);
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
