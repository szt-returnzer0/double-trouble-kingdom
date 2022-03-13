package view;

import model.Game;
import model.Terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameField extends JPanel {

    private final Terrain[][] mapRef;
    private final int xLength;
    private final int yLength;
    private final ControlPanel controlPanel;
    private int scale;

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
                scale = getSize().width / xLength;
                int panelX = (int) (frame.getSize().getWidth());
                int panelY = (int) (frame.getSize().getHeight());
                int w = panelX / 3;
                int h = (int) (panelY * 0.07);
                controlPanel.setBounds(panelX / 2 - w / 2, (int) (panelY * 0.85), w, h);
                repaint();
            }
        });
        this.add(controlPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //scale = (this.frame.getContentPane().getSize().width + 15) / xLength;

        renderField(g2d);

        g2d.dispose();
        //g.dispose(); //not needed as g wasn't created by us
    }

    private void renderField(Graphics2D g2d) {


        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                //System.out.println("["+y+"]"+"["+x+"]");
                drawObj(g2d, x, y);
                g2d.setColor(Color.GRAY);
                g2d.drawRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    private void drawObj(Graphics2D g2d, int x, int y) {
        handleType(g2d, mapRef[y][x].getType());
        //System.out.println("["+y+"]"+"["+x+"]");
        g2d.fillRect(x * scale, y * scale, scale, scale);
    }

    private void handleType(Graphics2D g2d, String type) {
        switch (type) {
            case "Plains" -> g2d.setColor(Color.GREEN);
            case "Desert" -> g2d.setColor(Color.YELLOW);
            case "Swamp" -> g2d.setColor(Color.BLUE);
            case "Mountain" -> g2d.setColor(Color.DARK_GRAY);
            default -> g2d.setColor(Color.GRAY);
        }
    }

}
