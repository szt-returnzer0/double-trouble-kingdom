package view;

import javax.swing.*;
import java.awt.*;
import model.Game;
import model.Terrain;

public class GameField extends JPanel {

    private final Terrain[][] mapRef;
    private final int xLength;
    private final int yLength;
    private int scale;
    private final JFrame frame;

    public GameField(Game game, JFrame frame) {
        this.mapRef = game.getMap();
        this.xLength = mapRef[0].length;
        this.yLength = mapRef.length;
        this.frame = frame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        scale = (this.frame.getSize().width) / xLength;
        //System.out.println("x:"+this.xLength+", y:"+this.yLength);

        renderField(g2d);

        g2d.dispose();
        g.dispose();
    }

    private void renderField(Graphics2D g2d) {


        for(int y = 0; y < yLength; y++) {
            for(int x = 0; x < xLength; x++) {
                //System.out.println("["+y+"]"+"["+x+"]");
                drawObj(g2d, x, y);
                g2d.setColor(Color.GRAY);
                g2d.drawRect(x*scale, y*scale, scale, scale);
            }
        }
    }

    private void drawObj(Graphics2D g2d, int x, int y) {
        handleType(g2d, mapRef[y][x].getType());
        //System.out.println("["+y+"]"+"["+x+"]");
        g2d.fillRect(x*scale, y*scale, scale, scale);
    }

    private void handleType(Graphics2D g2d, String type){
        switch (type) {
            case "Plains" -> g2d.setColor(Color.GREEN);
            case "Desert" -> g2d.setColor(Color.YELLOW);
            case "Swamp" -> g2d.setColor(Color.BLUE);
            case "Mountain" -> g2d.setColor(Color.DARK_GRAY);
            default -> g2d.setColor(Color.GRAY);
        }
    }

}
