package view;

import model.Entity;
import model.Game;
import model.Terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameField extends JPanel {

    protected final Terrain[][] mapRef;
    protected final int xLength;
    protected final int yLength;
    protected final ControlPanel controlPanel;
    protected final HamburgerMenu hamburgerMenu;
    protected int scale;
    protected Entity selection = null;

    public GameField(Game game, JFrame frame) {
        this.mapRef = game.getMap();
        this.xLength = mapRef[0].length;
        this.yLength = mapRef.length;
        this.scale = (frame.getContentPane().getSize().width) / xLength;
        frame.setPreferredSize(new Dimension(xLength * scale + 17, yLength * scale + 40));

        setLayout(null);
        this.controlPanel = new ControlPanel();
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
        handleType(g2d, mapRef[y][x].getType());
        //System.out.println("["+y+"]"+"["+x+"]");
        g2d.fillRect(x * scale, y * scale, scale, scale);
    }

    protected void drawEnt(Graphics2D g2d, int x, int y) {
        for (Entity entity : mapRef[y][x].getEntities()) {
            handleType(g2d, entity.getType());
            // System.out.println(entity.getType());
            g2d.fillRect(x * scale, y * scale, scale, scale);
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

    protected void setSelection(Entity ent) {
        selection = ent;
    }

    protected void handleType(Graphics2D g2d, String type) {
        switch (type) {
            case "Plains" -> g2d.setColor(Color.GREEN);
            case "Desert" -> g2d.setColor(Color.YELLOW);
            case "Swamp" -> g2d.setColor(Color.BLUE);
            case "Mountain" -> g2d.setColor(Color.DARK_GRAY);
            case "Castle" -> g2d.setColor(Color.lightGray);
            case "Barracks" -> g2d.setColor(new Color(64, 37, 19));
            default -> g2d.setColor(Color.GRAY);
        }
    }
}
