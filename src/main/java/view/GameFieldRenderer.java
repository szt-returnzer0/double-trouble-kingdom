package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class GameFieldRenderer extends JPanel {

    protected final int xLength;
    protected final int yLength;
    protected final ControlPanel controlPanel;
    protected final HamburgerMenu hamburgerMenu;
    protected Map mapRef;
    protected int scale;
    protected Entity selection = null;
    protected JFrame frame;
    protected Game game;
    protected String middleText;
    protected String sideText;


    public GameFieldRenderer(Game game, JFrame frame) {
        this.game = game;
        this.mapRef = game.getMap();
        this.frame = frame;
        this.xLength = mapRef.getTiles()[0].length;
        this.yLength = mapRef.getTiles().length;
        this.scale = (frame.getContentPane().getSize().width) / xLength;
        middleText = "";
        sideText = "";
        frame.setPreferredSize(new Dimension(xLength * scale + 17, yLength * scale + 40));

        setLayout(null);
        this.controlPanel = new ControlPanel(game);
        controlPanel.setSize((int) (frame.getSize().getWidth() / 3), (int) (frame.getSize().getHeight() * 0.07));

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                scale = (frame.getContentPane().getSize().width) / xLength;
                int panelX = (int) (frame.getSize().getWidth());
                int panelY = (int) (frame.getSize().getHeight());
                int w = panelX / 3;
                int h = (int) (panelY * 0.07);
                controlPanel.setBounds(panelX / 2 - w / 2, (int) (panelY * 0.85), w, h);
                repaint();
            }
        });
        this.hamburgerMenu = new HamburgerMenu(frame);
        //hamburgerMenu.setBounds(0,0,200,frame.getContentPane().getSize().width);
        this.add(controlPanel);
        this.add(hamburgerMenu);
        this.hamburgerMenu.attachActionListener(4, e -> {
            this.game.pauseGame();
            this.removeAll();
            frame.getContentPane().removeAll();
            frame.repaint();
            MainWindow.startMainMenu(frame);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //scale = (this.frame.getContentPane().getSize().width + 15) / xLength;

        renderField(g2d);
        drawCurrentSelection(g2d);
        drawLabels(g2d);
        g2d.dispose();
        //g.dispose(); //not needed as g wasn't created by us
    }

    protected void renderField(Graphics2D g2d) {


        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                //System.out.println("["+y+"]"+"["+x+"]");
                drawObj(g2d, x, y);
                drawEnt(g2d, x, y);
                g2d.setColor(Color.GRAY);
                g2d.drawRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    protected void drawObj(Graphics2D g2d, int x, int y) {
        handleType(g2d, mapRef.getTiles()[y][x].getType());
        //System.out.println("["+y+"]"+"["+x+"]");
        g2d.fillRect(x * scale, y * scale, scale, scale);
    }

    protected void drawEnt(Graphics2D g2d, int x, int y) {
        String side = x + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side

        for (Entity entity : mapRef.getTiles()[y][x].getEntities()) {
            handleType(g2d, entity.getType());
            // System.out.println(entity.getType());
            g2d.fillRect(x * scale, y * scale, scale, scale);

            drawUnitOwner(g2d, x, y, side, entity);
            drawBldState(g2d, entity);


        }

    }

    private void drawUnitOwner(Graphics2D g2d, int x, int y, String side, Entity entity) {
        ArrayList<String> units = new ArrayList<>(Arrays.asList("Soldier", "Kamikaze", "Diver", "Climber", "Assassin"));
        if (units.contains(entity.getType())) {
            setSideColor(side, g2d);
            g2d.drawRect(x * scale + 2, y * scale + 2, scale - 4, scale - 4);

        }
    }

    protected void drawBldState(Graphics2D g2d, Entity ent) {
        String side = ent.getPosition().x + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side

        ArrayList<String> towerTypes = new ArrayList<>(Arrays.asList("Sniper", "Shotgun", "Barracks"));
        if (towerTypes.contains(ent.getType())) {
            Stroke def = g2d.getStroke();
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, 0);
            g2d.setStroke(dashed);

            if (!ent.getType().equals("Barracks")) {
                Tower tmp = (Tower) ent;
                g2d.setColor(new Color(255, 225, 0, 85 * tmp.getLevel()));
            } else if (((Barracks) ent).isUpgraded()) {
                g2d.setColor(new Color(255, 225, 0));
            }
            g2d.drawRect(ent.getPosition().x * scale + 2, ent.getPosition().y * scale + 2, ent.getSize().width * scale - 4, ent.getSize().height * scale - 4);

            g2d.setStroke(def);

            drawBldOwner(g2d, ent, side);

        } else if (ent.getType().equals("Castle")) {
            drawBldOwner(g2d, ent, side);
        }

    }

    private void drawBldOwner(Graphics2D g2d, Entity ent, String side) {
        setSideColor(side, g2d);
        g2d.drawRect(ent.getPosition().x * scale + 4, ent.getPosition().y * scale + 4, ent.getSize().width * scale - 8, ent.getSize().height * scale - 8);
    }

    private void setSideColor(String side, Graphics2D g2d) {
        if (side.equals("left")) {
            g2d.setColor(Color.cyan);
        } else {
            g2d.setColor(Color.red);
        }
    }

    protected void drawCurrentSelection(Graphics2D g2d) {
        //System.out.println(selection);
        if (selection != null) {
            handleType(g2d, selection.getType());
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            Color col = g2d.getColor();
            g2d.drawRect(selection.getPosition().x * scale, selection.getPosition().y * scale, selection.getSize().width * scale, selection.getSize().height * scale);
            g2d.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 150));
            g2d.fillRect(selection.getPosition().x * scale, selection.getPosition().y * scale, selection.getSize().width * scale, selection.getSize().height * scale);

        }

    }

    public void drawLabels(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Roboto", Font.PLAIN, 24));
        g2d.drawString(middleText, getWidth() / 2 + 2, 40 + 1);
        g2d.drawString(sideText, getWidth() - 360 + 2, 40 + 1);

        g2d.setColor(Color.WHITE);
        g2d.drawString(middleText, getWidth() / 2, 40);
        g2d.drawString(sideText, getWidth() - 360, 40);
    }

    protected void setSelection(Entity ent) {
        selection = ent;
    }

    protected void handleType(Graphics2D g2d, String type) { //Lerakas
        switch (type) {
            case "Plains" -> g2d.setColor(Color.GREEN);
            case "Desert" -> g2d.setColor(Color.YELLOW);
            case "Swamp" -> g2d.setColor(Color.BLUE);
            case "Mountain" -> g2d.setColor(Color.DARK_GRAY);
            case "Castle" -> g2d.setColor(Color.lightGray);
            case "Barracks" -> g2d.setColor(new Color(64, 37, 19));
            case "Barricade" -> g2d.setColor(new Color(208, 146, 110));
            case "Sniper" -> g2d.setColor(new Color(118, 110, 106));
            case "Shotgun" -> g2d.setColor(new Color(17, 15, 15));
            case "Soldier" -> g2d.setColor(new Color(190, 30, 30));
            case "Assassin" -> g2d.setColor(new Color(45, 15, 80));
            case "Kamikaze" -> g2d.setColor(new Color(72, 18, 25));
            case "Diver" -> g2d.setColor(new Color(22, 107, 107));
            case "Climber" -> g2d.setColor(new Color(175, 112, 81));
            default -> g2d.setColor(Color.GRAY);
        }
    }
}
