package view;

import persistence.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;


/**
 * Implementation of HamburgerMenu component.
 */
public class HamburgerMenu extends JPanel {

    /**
     * The number of items in the menu.
     */
    private final int BUTTON_COUNT = 8;

    /**
     * The frame of the menu.
     */
    private final JFrame frame;
    /**
     * The buttons of the menu.
     */
    private final JRoundedButton[] buttons = new JRoundedButton[BUTTON_COUNT];
    /**
     * The background color of the menu.
     */
    private final Color bgColor = new Color(224, 224, 224, 205);
    /**
     * Determines if the menu is opened.
     */
    private boolean isOpened;

    /**
     * Constructs a new HamburgerMenu instance.
     *
     * @param frame the parent frame of the menu
     */
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
        String[] labels = new String[]{"Mentés", "Betöltés", "Eredmények", "Felbontás", "Textúrák", "Utak", "Főmenü", "Kilépés"};

        for (int i = 0; i < labels.length; i++) {
            buttons[i] = new JRoundedButton(labels[i], 160, 50, colors);
            menuList.add(buttons[i]);
        }

        attachActionListener(2, e -> new HallOfFame(new Database().getRecords(), null));
        attachActionListener(7, e -> System.exit(0));

        menuList.setBounds(0, 50, 200, height);
        menuList.setOpaque(false);
        menuList.setVisible(false);
        this.add(menuList);
        setBounds(0, 0, 50, 50);
        toggleButton.setBounds(0, 0, 50, 50);
        toggleButton.addItemListener(e -> {
            isOpened = e.getStateChange() == ItemEvent.SELECTED;

            if (isOpened) {
                setBounds(0, 0, 200, height);
                menuList.setVisible(true);
                repaint();
            } else {
                setBounds(0, 0, 50, 50);
                menuList.setVisible(false);
                repaint();
            }
        });
        this.add(toggleButton);
    }

    /**
     * The paintComponent method of the class.
     *
     * @param g graphics we use
     */
    @Override
    protected void paintComponent(Graphics g) {
        setBounds(0, 0, 200, frame.getHeight() - 40);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (isOpened) {
            g2d.setColor(bgColor);
            g2d.fillRoundRect(-25, 0, getWidth() - 1 + 25, getHeight() - 1, 50, 50);
            g2d.setColor(Color.white);
            g2d.drawRoundRect(-25, 0, getWidth() - 1 + 25, getHeight() - 1, 50, 50);
        }
        g2d.dispose();
    }

    /**
     * Adds an ActionListener to a button in the menu.
     *
     * @param idx the index of the button
     * @param e   the ActionListener we want to add
     */
    public void attachActionListener(int idx, ActionListener e) {
        buttons[idx].addActionListener(e);
    }

    /**
     * Inner class implementation of tripleStripeButton component.
     */
    private static class tripleStripeButton extends JToggleButton {
        public tripleStripeButton() {
            super();
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
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
