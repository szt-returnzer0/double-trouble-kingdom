package DTK_view;

import DTK_model.Map;
import DTK_model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    protected static Map mapRef;
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
    /**
     * If the textures are on or off.
     */
    private boolean texturesOn;

    /**
     * Index of current resolution.
     */
    private int resolutionState = 0;

    /**
     * Texture indexer.
     */
    private final java.util.Map<String, java.util.Map<String, BufferedImage>> textureHolders = new HashMap<>();

    /**
     * Returns the current scale.
     *
     * @return the current scale
     */
    public static int getScale() {
        return scale;
    }

    /**
     * Constructs a GameFieldRenderer instance.
     *
     * @param game  Game dependency injection
     * @param frame the parent frame
     */
    public GameFieldRenderer(Game game, JFrame frame) {
        texturesOn = true;
        this.game = game;
        mapRef = game.getMap();
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
                controlPanel.resize();
                repaint();
            }
        });
        this.hamburgerMenu = new HamburgerMenu(frame);
        this.add(controlPanel);
        this.add(hamburgerMenu);

        this.hamburgerMenu.attachActionListener(6, e -> {
            this.game.pauseGame();
            if (game.getDatabase() != null)
                this.game.getDatabase().closeConnection();
            this.removeAll();
            frame.getContentPane().removeAll();
            frame.repaint();
            MainWindow.startMainMenu(frame);
        });
        this.hamburgerMenu.attachActionListener(3, e -> toggleScale());

        this.hamburgerMenu.attachActionListener(4, e -> toggleTextures());

    }

    /**
     * Toggles the scale.
     */
    public void toggleScale() {
        switch (resolutionState) {
            case 0 -> {
                MainWindow.setDimension(new Dimension(1680, 872));
                MainWindow.repack();
                resolutionState++;
            }
            case 1 -> {
                MainWindow.setDimension(new Dimension(1553, 808));
                MainWindow.repack();
                resolutionState++;
            }
            case 2 -> {
                MainWindow.setDimension(new Dimension(1361, 711));
                MainWindow.repack();
                resolutionState++;
            }
            case 3 -> {
                MainWindow.setDimension(new Dimension(1873, 968));
                MainWindow.repack();
                resolutionState = 0;
            }
        }
        repaint();
    }

    public void toggleTextures() {
        texturesOn = !texturesOn;
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
                if (!texturesOn) {
                    g2d.setColor(Color.GRAY);
                    g2d.drawRect(x * scale, y * scale, scale, scale);
                }
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
        if (!texturesOn) {
            handleType(g2d, mapRef.getTiles()[y][x].getType());
            g2d.fillRect(x * scale, y * scale, scale, scale);
        } else if (mapRef.getTiles()[y][x] != null)
            drawImage(g2d, mapRef.getTiles()[y][x], x, y);
    }

    private int bevelCnt = 0;

    /**
     * Returns a map reference.
     *
     * @return the map
     */
    public static Map getMapRef() {
        return mapRef;
    }


    /**
     * Draws the waypoints
     *
     * @param g2d the graphics we use
     */
    protected void drawWayPoints(Graphics2D g2d) {
        if (game.getGameState().getWayPoints() != null && !game.getGameState().getWayPoints().isEmpty()) {
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
                if (!texturesOn) {
                    g2d.fillRect(x * scale, y * scale, scale, scale);

                    drawUnitOwner(g2d, x, y, entity.getSide(), entity);
                    drawBldState(g2d, entity);
                    drawHealthBar(g2d, entity);
                } else {
                    if (entity instanceof Building b) {
                        Point start = new Point(b.getPosition().x, b.getPosition().y);
                        Dimension size = b.getSize();
                        if (x == start.x + size.width - 1 && y == start.y + size.height - 1) {
                            g2d.drawImage(textureHolders.get(b.getType().text).get(b.getImage()), start.x * scale, start.y * scale, size.width * scale, size.height * scale, null);
                        }

                        drawHealthBar(g2d, entity);
                    } else if (entity instanceof Soldier s) {
                        g2d.drawImage(textureHolders.get(s.getType().text).get(s.getImage()), x * scale, y * scale, scale, scale, null);
                        drawAvgHealthBar(g2d, mapRef.getTiles()[y][x].getEntities());
                    }
                }


            }


        }
        if (!game.getGameState().getRoundState().equals("Attacking")) {
            drawUnitNumber(g2d, x, y, 0, 0);
        }


    }

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

        bevelCnt = bevelCnt < 360 ? bevelCnt + 1 : 0;
        if (game.getGameState().isPathVisualization()) {
            for (Entity entity : game.getGameState().getCurrentPlayer().getEntities()) {
                if (entity instanceof Soldier s) {
                    if (s.isAnimated())
                        s.visualizationStart = s.visualizationEnd = 0;
                    else
                        drawPath(g2d, s);
                }
            }
        }
        Set<Tower> towers = new HashSet<>();
        towers.addAll(game.getGameState().getEnemyTowers(1));
        towers.addAll(game.getGameState().getEnemyTowers(2));

        if (game.getGameState().getRoundState().equals("Attacking")) {
            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, bevelCnt * 2));
            for (Tower t : towers) {
                if (t.getTargets() != null && !t.getTargets().isEmpty() && t.isCanAttack() && !t.isDestroyed()) {
                    for (Entity target : t.getTargets()) {
                        if (t.getPosition().distance(target.getPosition()) <= t.getRange() && target.isAlive()) {
                            g2d.drawLine((target.getPosition().x + target.getSize().width / 2) * scale, (target.getPosition().y + target.getSize().height / 2) * scale,
                                    (t.getPosition().x + t.getSize().width / 2) * scale, (t.getPosition().y + t.getSize().height / 2) * scale);
                        }
                    }
                }
            }
        }
        g2d.dispose();
        //g.dispose(); //not needed as g wasn't created by us
    }

    /**
     * Draws the health bar of the unit
     *
     * @param g2d the graphics we use
     * @param ent the unit we want to draw the health bar of
     */
    protected void drawHealthBar(Graphics2D g2d, Entity ent) {

        Color green = new Color(0, 155, 35);
        Color red = new Color(155, 0, 0);
        if (ent instanceof Soldier s) {
            int width = (int) (scale * s.getHealthPoints() / ((double) s.getMaxHealthPoints()));
            drawUnitHealth(g2d, ent, green, red, width, s.getAnimObj(), s.getSide());
        } else if (ent instanceof Building b && !(b instanceof Barracks)) {
            drawBuildingHealth(g2d, green, red, b);
        }

    }

    /**
     * Draws the health bar of the building
     *
     * @param g2d   the graphics we use
     * @param green the color of the health bar
     * @param red   the color of the health bar
     * @param b     the building we want to draw the health bar of
     */
    private void drawBuildingHealth(Graphics2D g2d, Color green, Color red, Building b) {
        int width = (int) ((b.getSize().width) * scale * b.getHealthPoints() / ((double) b.getMaxHealthPoints()));
        g2d.setColor(green);
        g2d.fillRect(b.getPosition().x * scale, b.getPosition().y * scale - 6, width, 5);
        g2d.setColor(red);
        g2d.fillRect(b.getPosition().x * scale + width, b.getPosition().y * scale - 6, (b.getSize().width) * scale - width, 5);
        setSideColor(b.getSide(), g2d);
        g2d.drawRect(b.getPosition().x * scale, b.getPosition().y * scale - 6, (b.getSize().width) * scale, 5);
    }


    /**
     * Draws the avg health bar of the units
     *
     * @param g2d  the graphics we use
     * @param entities the units we want to draw the avg health bar of
     */
    protected void drawAvgHealthBar(Graphics2D g2d, ArrayList<Entity> entities) {
        int avgHealth = 0;
        for (Entity ent : entities) {
            avgHealth += ent.getHealthPoints();
        }
        avgHealth /= entities.size();

        int avgMaxHealth = 0;
        for (Entity ent : entities) {
            avgMaxHealth += ent.getMaxHealthPoints();
        }
        avgMaxHealth /= entities.size();
        Entity ent = entities.get(0);

        Color green = new Color(0, 155, 35);
        Color red = new Color(155, 0, 0);
        int width = ((entities.get(0).getSize().width) * scale * avgHealth / avgMaxHealth);
        drawUnitHealth(g2d, ent, green, red, width, ent.getAnimObj(), ent.getSide());
    }

    /**
     * Draws the health bar of the unit
     *
     * @param g2d     the graphics we use
     * @param ent     the unit we want to draw the health bar of
     * @param green   the color of the health bar
     * @param red     the color of the health bar
     * @param width   the width of the health bar
     * @param animObj the animation object of the unit
     * @param side    the side of the unit
     */
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
        if (ObjectTypes.getSoldierTypes().contains(entity.getType())) {
            setSideColor(side, g2d);
            g2d.drawRect(x * scale + 2, y * scale + 2, scale - 4, scale - 4);

        }
    }

    /**
     * Draws the paths
     *
     * @param g2d the graphics we use
     * @param s   the path we want to draw
     */
    protected void drawPath(Graphics2D g2d, Soldier s) {
        setSideColor(s.getSide(), g2d);
        Color c = g2d.getColor();
        int alpha = s.getSide().equals("left") ? 200 : 150;
        g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha));
        ArrayList<Point> path = s.getAbsPath();
        s.visualizationEnd = Math.min(s.visualizationEnd + 1, path.size());
        if (s.visualizationEnd >= path.size()) {
            s.visualizationStart = Math.min(s.visualizationStart + 1, path.size());
            if (s.visualizationStart >= path.size())
                s.visualizationStart = s.visualizationEnd = 0;
        }

        for (int i = s.visualizationStart; i < s.visualizationEnd; i++) {
            Point p = path.get(i);
            g2d.fillRect(p.x * scale, p.y * scale, scale, scale);
        }
    }

    protected void drawAnimated(Graphics2D g2d) {
        for (Animator animator : GameState.animBuffer) {
            if (!texturesOn) {
                handleType(g2d, animator.getEntity().getType());
                g2d.fillRect((int) (animator.getEntity().getPosition().x * scale + animator.getX()), (int) (animator.getEntity().getPosition().y * scale + animator.getY()), scale, scale);
            } else if (animator.getEntity() instanceof Soldier s) {
                g2d.drawImage(textureHolders.get(s.getType().text).get(s.getImage()), (int) (animator.getEntity().getPosition().x * scale + animator.getX()), (int) (animator.getEntity().getPosition().y * scale + animator.getY()), scale, scale, null);
            }

            drawUnitAnimatedInformation(g2d, animator.getEntity().getPosition().x, animator.getEntity().getPosition().y, animator.getEntity().getSide(), animator.getEntity(), animator.getX(), animator.getY());

        }

    }

    /**
     * Draws a building's visible attributes
     *
     * @param g2d the graphics we use
     * @param ent the building to draw to
     */
    protected void drawBldState(Graphics2D g2d, Entity ent) {
        String side = ent.getPosition().x + 3 < xLength / 2 ? "left" : "right";


        if (ObjectTypes.getTowerTypes().contains(ent.getType())) {
            Stroke def = g2d.getStroke();
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, 0);
            g2d.setStroke(dashed);

            if (!(ent instanceof Barracks)) {
                Tower tmp = (Tower) ent;
                g2d.setColor(new Color(255, 225, 0, 85 * tmp.getLevel()));
            } else if (((Barracks) ent).isUpgraded()) {
                g2d.setColor(new Color(255, 225, 0));
            }
            g2d.drawRect(ent.getPosition().x * scale + 2, ent.getPosition().y * scale + 2, ent.getSize().width * scale - 4, ent.getSize().height * scale - 4);

            g2d.setStroke(def);

            drawBldOwner(g2d, ent, side);

        } else if (ent instanceof Castle) {
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
    protected void handleType(Graphics2D g2d, ObjectTypes type) {
        g2d.setColor(type.color);
    }

    /**
     * Gets a Tile art type
     *
     * @param x    tile x coordinate
     * @param y    tile y coordinate
     * @param type tile type
     * @return The tile art type
     */
    private String getTileType(int x, int y, ObjectTypes type) {
        String tileType;
        boolean left = true;
        boolean down = true;
        boolean right = true;
        boolean up = true;

        if (x != 0)
            left = Objects.equals(game.getMap().getTiles()[y][x - 1].getType(), type);
        if (y + 1 != game.getMap().getTiles().length)
            down = Objects.equals(game.getMap().getTiles()[y + 1][x].getType(), type);
        if (x + 1 != game.getMap().getTiles()[0].length)
            right = Objects.equals(game.getMap().getTiles()[y][x + 1].getType(), type);
        if (y != 0)
            up = Objects.equals(game.getMap().getTiles()[y - 1][x].getType(), type);

        if (up && right && down && left) {
            tileType = "5" + game.getMap().getTiles()[y][x].getTileVersion();
        } else if (left && right && down) {
            tileType = "2";
        } else if (up && left && down) {
            tileType = "6";
        } else if (up && right && left) {
            tileType = "8";
        } else if (up && right && down) {
            tileType = "4";
        } else if (up && right) {
            tileType = "7";
        } else if (up && down) {
            tileType = "16";
        } else if (up && left) {
            tileType = "9";
        } else if (right && down) {
            tileType = "1";
        } else if (right && left) {
            tileType = "15";
        } else if (down && left) {
            tileType = "3";
        } else if (up) {
            tileType = "10";
        } else if (right) {
            tileType = "11";
        } else if (down) {
            tileType = "12";
        } else if (left) {
            tileType = "13";
        } else
            tileType = "14";


        return tileType;
    }

    /**
     * Sets textures
     */
    public void setTextures() {
        File resources = new File("./src/main/resources");
        String[] classNames = Arrays.stream(Objects.requireNonNull(resources.list())).filter(e -> !e.contains(".")).toArray(String[]::new);

        for (String className : Objects.requireNonNull(classNames)) {

            File classFolder = new File("./src/main/resources/" + className);
            String[] classTypes = classFolder.list();

            for (String type : Objects.requireNonNull(classTypes)) {
                textureHolders.put(type, new HashMap<>());
                File tileImages = new File("./src/main/resources/" + className + "/" + type);
                String[] images = tileImages.list();
                for (String imageName : Objects.requireNonNull(images)) {
                    try {
                        BufferedImage tex = ImageIO.read(new File("./src/main/resources/" + className + "/" + type + "/" + imageName));
                        textureHolders.get(type).put(imageName, tex);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * Draws the textures.
     *
     * @param g2d the graphics we use
     * @param ter the terrain to draw
     * @param x   the x coordinate
     * @param y   the y coordinate
     */
    public void drawImage(Graphics2D g2d, Terrain ter, int x, int y) {
        g2d.drawImage(textureHolders.get(ter.getType().text).get(getTileType(x, y, ter.getType()) + ".png"), x * scale, y * scale, scale, scale, null);
    }

    /**
     * Draws the number of units on terrain.
     *
     * @param g2d the graphics we use
     * @param x   the x coordinate
     * @param y   the y coordinate
     * @param mx  the move x coordinate
     * @param my  the move y coordinate
     */
    private void drawUnitNumber(Graphics2D g2d, int x, int y, double mx, double my) {
        if (mapRef.getTiles()[y][x].getEntities().size() > 1) {
            String text = "" + mapRef.getTiles()[y][x].getEntities().size();
            Font font = new Font("Roboto", Font.PLAIN, 20);
            int width = g2d.getFontMetrics(font).stringWidth(text);

            g2d.setFont(font);

            g2d.setColor(Color.black);
            g2d.drawString(text, (int) (x * scale + scale / 2 - (int) Math.floor(width / 2.0) + (int) (scale * 0.035) + mx) + 2, (int) (y * scale + scale / 2 + (int) (scale * 0.3) + my) + 2);

            g2d.setColor(Color.white);
            g2d.drawString(text, (int) (x * scale + scale / 2 - (int) Math.floor(width / 2.0) + (int) (scale * 0.035) + mx), (int) (y * scale + scale / 2 + (int) (scale * 0.3) + my));
        }
    }

    /**
     * Draws the animated information.
     *
     * @param g2d    the graphics we use
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param side   the side of the entity
     * @param entity the entity to draw
     * @param mx     the move x coordinate
     * @param my     the move y coordinate
     */
    private void drawUnitAnimatedInformation(Graphics2D g2d, int x, int y, String side, Entity entity, double mx, double my) {
        if (entity instanceof Soldier) {

            if (!texturesOn) {
                setSideColor(side, g2d);
                g2d.drawRect((int) (x * scale + 2 + mx), (int) (y * scale + 2 + my), scale - 4, scale - 4);
            }

            drawUnitNumber(g2d, x, y, mx, my);
            drawAvgHealthBar(g2d, mapRef.getTiles()[entity.getPosition().y][entity.getPosition().x].getEntities());
        }
    }




}
