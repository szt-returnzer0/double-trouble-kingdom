package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

//NEEDS CLEANUP!!!
public class HamburgerMenu extends JPanel {
    private final JFrame frame;
    private final JRoundedButton[] buttons = new JRoundedButton[6]; //for multiple actionListener support
    // private Color bgColor = new Color(0, 0, 0, 0);
    private final Color bgColor = new Color(224, 224, 224, 205);
    private boolean opened = false;

    public HamburgerMenu(JFrame frame) {
        super();
        this.frame = frame;
        int height = frame.getHeight();
        setBounds(0, 0, 50, 50);
        setOpaque(false);
        tripleStripeButton toggleButton = new tripleStripeButton();
        setLayout(null);

        JPanel menuList = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 25));
        Color[] colors = new Color[]{new Color(77, 208, 255, 144), new Color(79, 104, 112, 108), new Color(5, 154, 197, 152)};
        String[] labels = new String[]{"Mentés", "Betöltés", "Eredmények", "Felbontás", "Kilépés"};

        for (int i = 0; i < labels.length; i++) {
            buttons[i] = new JRoundedButton(labels[i], 160, 50, colors);
            menuList.add(buttons[i]);
        }

        menuList.setBounds(0, 50, 200, height);
        menuList.setOpaque(false);
        menuList.setVisible(false);
        //menuList.setVisible(false);
        this.add(menuList);
        setBounds(0, 0, 50, 50);
        toggleButton.setBounds(0, 0, 50, 50);
        toggleButton.addItemListener(e -> {
            //int state=e.getStateChange();
            opened = e.getStateChange() == ItemEvent.SELECTED;

            if (opened) {
                setBounds(0, 0, 200, height);
                //bgColor = new Color(224, 224, 224, 205);
                menuList.setVisible(true);
                //add(menuList);

                repaint();
            } else {
                setBounds(0, 0, 50, 50);
                //bgColor = new Color(0, 0, 0, 0);
                //remove(menuList);
                menuList.setVisible(false);
                repaint();
            }
        });
        this.add(toggleButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        setBounds(0, 0, 200, frame.getHeight() - 40);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (opened) {
            g2d.setColor(bgColor);
            g2d.fillRoundRect(-25, 0, getWidth() - 1 + 25, getHeight() - 1, 50, 50);
            g2d.setColor(Color.white);
            g2d.drawRoundRect(-25, 0, getWidth() - 1 + 25, getHeight() - 1, 50, 50);
        }
        g2d.dispose();
    }

    public void attachActionListener(int idx, ActionListener e) {
        buttons[idx].addActionListener(e);
    }

    public void setButtonColor(int idx, Color color, int idx2) {
        buttons[idx].setColor(idx2, color);
    }

    private static class tripleStripeButton extends JToggleButton {
        public tripleStripeButton() {
            super();
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            //super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            for (int i = 0; i < 3; i++) {
                g2d.setColor(Color.white);
                g2d.fillRoundRect(5, 10 + i * 8, 40, 5, 12, 12);
                g2d.setColor(Color.darkGray);
                g2d.drawRoundRect(5, 10 + i * 8, 40, 5, 12, 12);
            }

            g2d.dispose();
        }

    }
}
