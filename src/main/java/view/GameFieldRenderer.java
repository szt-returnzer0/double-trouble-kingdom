package view;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Implements the game's rendering engine.
 */
public class GameFieldRenderer extends JPanel {

    /**
     * The scaling of the game.
     */
    protected static int scale;
    /**
     * The height of the panel.
     */
    protected final int xLength;
    /**
     * The width of the panel.
     */
    protected final int yLength;
    /**
     * ControlPanel component.
     */
    protected final ControlPanel controlPanel;
    /**
     * HamburgerMenu component.
     */
    protected final HamburgerMenu hamburgerMenu;
    /**
     * Reference to a Map instance.
     */
    protected Map mapRef;
    /**
     * The selected Entity.
     */
    protected Entity selection = null;
    /**
     * The parent frame.
     */
    protected JFrame frame;
    /**
     * The Game dependency.
     */
    protected Game game;
    /**
     * Text field 1 on the HUD.
     */
    protected String middleText;
    /**
     * Text field 2 on the HUD.
     */
    protected String sideText;
    private boolean texuresOn;

    /**
     * Constructs a GameFieldRenderer instance.
     *
     * @param game  Game dependency injection
     * @param frame the parent frame
     */
    public GameFieldRenderer(Game game, JFrame frame) {
        texuresOn = false;
        this.game = game;
        this.mapRef = game.getMap();
        setTextures();
        this.frame = frame;
        this.xLength = mapRef.getTiles()[0].length;
        this.yLength = mapRef.getTiles().length;
        scale = (frame.getContentPane().getSize().width) / xLength;
        middleText = "";
        sideText = "";
        GameState.animBuffer.clear();
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

        this.hamburgerMenu.attachActionListener(6, e -> {
            this.game.pauseGame();
            this.removeAll();
            frame.getContentPane().removeAll();
            frame.repaint();
            MainWindow.startMainMenu(frame);
        });
        this.hamburgerMenu.attachActionListener(3, e -> toggleScale());

        this.hamburgerMenu.attachActionListener(4, e -> toggleTextures());

    }

    public static int getScale() {
        return scale;
    }

    public void toggleScale() {

        if (MainWindow.getDimension().width <= 1100 && MainWindow.getDimension().height <= 810) {
            MainWindow.setDimension(new Dimension(1920, 1080));
            MainWindow.repack();

        } else if (MainWindow.getDimension().width <= 1700 && MainWindow.getDimension().height <= 1000) {
            MainWindow.setDimension(new Dimension(1024, 768));
            MainWindow.repack();

        } else if (MainWindow.getDimension().width <= 2000 && MainWindow.getDimension().height <= 1100) {
            MainWindow.setDimension(new Dimension(1600, 900));
            MainWindow.repack();

        }
        System.out.println(MainWindow.getDimension().width + " " + MainWindow.getDimension().height);
        repaint();
    }

    public void toggleTextures() {
        texuresOn = !texuresOn;
    }

    /**
     * Renders the canvas
     *
     * @param g2d the graphics we use
     */
    protected void renderField(Graphics2D g2d) {


        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                //System.out.println("["+y+"]"+"["+x+"]");
                drawTile(g2d, x, y);
                drawEnt(g2d, x, y);
                g2d.setColor(Color.GRAY);
                g2d.drawRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    /**
     * Draws the selected tile.
     *
     * @param g2d the graphics we use
     * @param x   the horizontal coordinate
     * @param y   the vertical coordinate
     */
    protected void drawTile(Graphics2D g2d, int x, int y) {
        handleType(g2d, mapRef.getTiles()[y][x].getType());
        //System.out.println("["+y+"]"+"["+x+"]");
        g2d.fillRect(x * scale, y * scale, scale, scale);
        if (texuresOn)
            if (mapRef.getTiles()[y][x] != null)
                drawImage(g2d, mapRef.getTiles()[y][x], x, y);
    }

    private int bevelCnt = 0;

    /**
     * The paintComponent method of the class.
     *
     * @param g graphics we use
     */
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
        if (Objects.equals(game.getGameState().getRoundState(), "Attacking"))
            drawAnimated(g2d);
        drawWayPoints(g2d);
        /*g2d.setColor(Color.red);
        for (Point point : GameField.testpath) {
            g2d.fillRect(point.x*scale,point.y*scale,scale,scale);
        }*/ //VISUALIZATION
        bevelCnt = bevelCnt < 360 ? bevelCnt + 1 : 0;
        g2d.dispose();
        //g.dispose(); //not needed as g wasn't created by us
    }


    protected void drawWayPoints(Graphics2D g2d) {
        if (game.getGameState().getWayPoints() != null && !game.getGameState().getWayPoints().isEmpty()) {
            System.out.println("NEMNULL");
            setSideColor(game.getGameState().getCurrentPlayer().getSide(), g2d);
            Stroke def = g2d.getStroke();
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, bevelCnt);
            g2d.setStroke(dashed);
            for (Point wayPoint : game.getGameState().getWayPoints()) {
                g2d.drawRoundRect(wayPoint.x * scale, wayPoint.y * scale, scale, scale, 200, 200);
            }
            g2d.setStroke(def);
        }
    }

    /**
     * Draws the selected Entity.
     *
     * @param g2d the graphics we use
     * @param x   the horizontal coordinate
     * @param y   the vertical coordinate
     */
    protected void drawEnt(Graphics2D g2d, int x, int y) {
        //String side = x + 3 < xLength / 2 ? "left" : "right"; // check in if building is on current player's side
        //remove dead entities
        if (!mapRef.getTiles()[y][x].getEntities().isEmpty()) {
            for (int i = 0; i < mapRef.getTiles()[y][x].getEntities().size(); i++) {
                if (!mapRef.getTiles()[y][x].getEntities().get(i).isAlive()) {
                    mapRef.getTiles()[y][x].getEntities().remove(i--);

                }
            }
        }
        for (Entity entity : mapRef.getTiles()[y][x].getEntities()) {
            handleType(g2d, entity.getType());
            if (!entity.isAnimated() && entity.isAlive()) {
                g2d.fillRect(x * scale, y * scale, scale, scale);

                drawUnitOwner(g2d, x, y, entity.getSide(), entity);
                drawBldState(g2d, entity);
                drawHealthBar(g2d, entity);
            }


        }
        if (!game.getGameState().getRoundState().equals("Attacking")) {
            if (mapRef.getTiles()[y][x].getEntities().size() > 1) {
                String text = "" + mapRef.getTiles()[y][x].getEntities().size();
                Font font = new Font("Roboto", Font.PLAIN, 20);
                int width = g2d.getFontMetrics(font).stringWidth(text);
                g2d.setColor(Color.white);
                g2d.setFont(font);
                g2d.drawString(text, x * scale + scale / 2 - (int) Math.floor(width / 2.0) + (int) (scale * 0.035), y * scale + scale / 2 + (int) (scale * 0.3));
                drawAvgHealthBar(g2d, mapRef.getTiles()[y][x].getEntities());
            }
        }


    }

    protected void drawHealthBar(Graphics2D g2d, Entity ent) {

        Color green = new Color(0, 155, 35);
        Color red = new Color(155, 0, 0);
        if (ent instanceof Soldier s) {
            int width = (int) (scale * s.getHealthPoints() / ((double) s.getMaxHealthPoints()));
            drawUnitHealth(g2d, ent, green, red, width, s.getAnimObj(), s.getSide());
        } else if (ent instanceof Building b && !b.getType().equals("Barracks")) {
            drawBuildingHealth(g2d, green, red, b);
        }

    }

    private void drawBuildingHealth(Graphics2D g2d, Color green, Color red, Building b) {
        int width = (int) ((b.getSize().width) * scale * b.getHealthPoints() / ((double) b.getMaxHealthPoints()));
        g2d.setColor(green);
        g2d.fillRect(b.getPosition().x * scale, b.getPosition().y * scale - 6, width, 5);
        g2d.setColor(red);
        g2d.fillRect(b.getPosition().x * scale + width, b.getPosition().y * scale - 6, (b.getSize().width) * scale - width, 5);
        setSideColor(b.getSide(), g2d);
        g2d.drawRect(b.getPosition().x * scale, b.getPosition().y * scale - 6, (b.getSize().width) * scale, 5);
    }


    protected void drawAvgHealthBar(Graphics2D g2d, ArrayList<Entity> ents) {
        int avgHealth = 0;
        for (Entity ent : ents) {
            avgHealth += ent.getHealthPoints();
        }
        avgHealth /= ents.size();

        int avgMaxHealth = 0;
        for (Entity ent : ents) {
            avgMaxHealth += ent.getMaxHealthPoints();
        }
        avgMaxHealth /= ents.size();
        Entity ent = ents.get(0);

        Color green = new Color(0, 155, 35);
        Color red = new Color(155, 0, 0);
        int width = ((ents.get(0).getSize().width) * scale * avgHealth / avgMaxHealth);
        drawUnitHealth(g2d, ent, green, red, width, ent.getAnimObj(), ent.getSide());
    }

    private void drawUnitHealth(Graphics2D g2d, Entity ent, Color green, Color red, int width, Animator animObj, String side) {
        g2d.setColor(green);
        g2d.fillRect((int) (ent.getPosition().x * scale + animObj.getX()), (int) (ent.getPosition().y * scale - 6 + animObj.getY()), width, 5);
        g2d.setColor(red);
        g2d.fillRect((int) (ent.getPosition().x * scale + animObj.getX()) + width, (int) (ent.getPosition().y * scale - 6 + animObj.getY()), scale - width, 5);
        setSideColor(side, g2d);
        g2d.drawRect((int) (ent.getPosition().x * scale + animObj.getX()), (int) (ent.getPosition().y * scale - 6 + animObj.getY()), scale, 5);
    }


    /**
     * Draws the unit's owner color
     *
     * @param g2d    the graphics we use
     * @param x      the horizontal coordinate
     * @param y      the vertical coordinate
     * @param side   the side it belongs to
     * @param entity the entity to draw to
     */
    private void drawUnitOwner(Graphics2D g2d, int x, int y, String side, Entity entity) {
        ArrayList<String> units = new ArrayList<>(Arrays.asList("Soldier", "Kamikaze", "Diver", "Climber", "Assassin"));
        if (units.contains(entity.getType())) {
            setSideColor(side, g2d);
            g2d.drawRect(x * scale + 2, y * scale + 2, scale - 4, scale - 4);

        }
    }

    protected void drawAnimated(Graphics2D g2d) {
        for (Animator animator : GameState.animBuffer) {
            handleType(g2d, animator.getEnt().getType());
            g2d.fillRect((int) (animator.getEnt().getPosition().x * scale + animator.getX()), (int) (animator.getEnt().getPosition().y * scale + animator.getY()), scale, scale);

            drawUnitAnimatedInformation(g2d, animator.getEnt().getPosition().x, animator.getEnt().getPosition().y, animator.getEnt().getSide(), animator.getEnt(), animator.getX(), animator.getY());
            drawHealthBar(g2d, animator.getEnt());

        }
    }

    /**
     * Draws a building's visible attributes
     *
     * @param g2d the graphics we use
     * @param ent the building to draw to
     */
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

        //draw destroyed towers in red
        if (ent instanceof Tower t && t.isDestroyed()) {
            g2d.setColor(Color.RED);
            g2d.drawRect(ent.getPosition().x * scale + 2, ent.getPosition().y * scale + 2, ent.getSize().width * scale - 4, ent.getSize().height * scale - 4);


        }

    }

    /**
     * Draws the building's owner
     *
     * @param g2d  the graphics we use
     * @param ent  the building to draw to
     * @param side the side it belongs to
     */
    private void drawBldOwner(Graphics2D g2d, Entity ent, String side) {
        setSideColor(side, g2d);
        g2d.drawRect(ent.getPosition().x * scale + 4, ent.getPosition().y * scale + 4, ent.getSize().width * scale - 8, ent.getSize().height * scale - 8);
    }

    /**
     * Sets the side's color.
     *
     * @param side the side we are on
     * @param g2d  the graphics we use
     */
    private void setSideColor(String side, Graphics2D g2d) {
        if (side.equals("left")) {
            g2d.setColor(Color.cyan);
        } else {
            g2d.setColor(Color.red);
        }
    }

    /**
     * Draws the current selection
     *
     * @param g2d the graphics we use
     */
    protected void drawCurrentSelection(Graphics2D g2d) {
        //System.out.println(selection);
        if (selection != null) {
            handleType(g2d, selection.getType());
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, bevelCnt);
            g2d.setStroke(dashed);
            Color col = g2d.getColor();
            g2d.drawRect(selection.getPosition().x * scale, selection.getPosition().y * scale, selection.getSize().width * scale, selection.getSize().height * scale);
            g2d.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 150));
            g2d.fillRect(selection.getPosition().x * scale, selection.getPosition().y * scale, selection.getSize().width * scale, selection.getSize().height * scale);

        }

    }

    /**
     * Draws the HUD's labels.
     *
     * @param g2d the graphics we use
     */
    public void drawLabels(Graphics2D g2d) {
        Font font = new Font("Roboto", Font.PLAIN, 24);
        g2d.setFont(font);
        int middleWidth = g2d.getFontMetrics(font).stringWidth(middleText);
        int sideWidth = g2d.getFontMetrics(font).stringWidth(sideText);

        g2d.setColor(Color.BLACK);
        g2d.drawString(middleText, getWidth() / 2 - (int) Math.ceil(middleWidth / 2.0) + 2, 40 + 1);
        g2d.drawString(sideText, getWidth() - (int) (sideWidth * 1.2) + 2, 40 + 1);

        g2d.setColor(Color.WHITE);
        g2d.drawString(middleText, getWidth() / 2 - (int) Math.ceil(middleWidth / 2.0), 40);
        g2d.drawString(sideText, getWidth() - (int) (sideWidth * 1.2), 40);

        if (game.getGameState().isEnded()) {
            font = new Font("Roboto", Font.PLAIN, 64);
            g2d.setFont(font);
            String winner = game.getGameState().getWinner().getName() + " nyerte a játékot!";
            int winnerWidth = g2d.getFontMetrics(font).stringWidth(winner);
            g2d.setColor(Color.BLACK);
            g2d.drawString(winner, getWidth() / 2 - (int) Math.ceil(winnerWidth / 2.0) + 2, getHeight() / 2 + 1);
            g2d.setColor(Color.WHITE);
            g2d.drawString(winner, getWidth() / 2 - (int) Math.ceil(winnerWidth / 2.0), getHeight() / 2);
        }
    }


    /**
     * Sets the selection to a new Entity.
     *
     * @param ent the entity to set to
     */
    protected void setSelection(Entity ent) {
        selection = ent;
    }

    /**
     * Defines which color to use when drawing an Entity.
     *
     * @param g2d  the graphics we use
     * @param type the type to handle
     */
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



    public void setTextures() {
        for (int i = 0; i < game.getMap().getTiles().length; i++) {
            for (int j = 0; j < game.getMap().getTiles()[i].length; j++) {
                try {
                    game.getMap().getTiles()[i][j].setTexture(ImageIO.read(new File("./src/main/resources/" + game.getMap().getTiles()[i][j].getType() + ".png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void drawImage(Graphics2D g2d, Terrain ter, int x, int y) {

        g2d.drawImage(ter.getTexture(), x * scale, y * scale, scale, scale, null);

    }

    private void drawUnitAnimatedInformation(Graphics2D g2d, int x, int y, String side, Entity entity, double mx, double my) {
        ArrayList<String> units = new ArrayList<>(Arrays.asList("Soldier", "Kamikaze", "Diver", "Climber", "Assassin"));
        if (units.contains(entity.getType())) {
            setSideColor(side, g2d);
            g2d.drawRect((int) (x * scale + 2 + mx), (int) (y * scale + 2 + my), scale - 4, scale - 4);
            if (mapRef.getTiles()[y][x].getEntities().size() > 1) {
                String text = "" + mapRef.getTiles()[y][x].getEntities().size();
                Font font = new Font("Roboto", Font.PLAIN, 20);
                int width = g2d.getFontMetrics(font).stringWidth(text);
                g2d.setColor(Color.white);
                g2d.setFont(font);
                g2d.drawString(text, (int) (x * scale + scale / 2 - (int) Math.floor(width / 2.0) + (int) (scale * 0.035) + mx), (int) (y * scale + scale / 2 + (int) (scale * 0.3) + my));
                drawAvgHealthBar(g2d, mapRef.getTiles()[entity.getPosition().y][entity.getPosition().x].getEntities());

            }
        }
    }

    public Map getMapRef() {
        return mapRef;
    }


}
