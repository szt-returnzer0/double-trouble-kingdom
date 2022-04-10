package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import view.GameFieldRenderer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Implementation of Animator class, contains methods for moving the Units, and animate the attacking of Towers.
 */
public class Animator {
    /**
     * x-coordinate of the Unit.
     */
    private double x = 0;
    /**
     * y-coordinate of the Unit.
     */
    private double y = 0;
    /**
     * The animated Entity.
     */
    private Entity ent;
    /**
     * The time spent to travel 1 block.
     */
    private double second = 0.5;
    /**
     * The animation path.
     */
    private ArrayList<Point> path = new ArrayList<>();// = new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(1, 0), new Point(1, 0), new Point(1, 0), new Point(0, -1), new Point(-1, 0)));
    /**
     * The steps taken in the round.
     */
    private int steps = 0;
    /**
     * The speed modifier.
     */
    private double speedMod = 1;

    /**
     * Constructor for Animator class.
     *
     * @param ent The animated Entity.
     */
    public Animator(Entity ent) {
        this.ent = ent;
    }

    /**
     * Constructor for Animator class loaded from JSON.
     */
    @JsonCreator
    public Animator() {
    }

    /**
     * Sets the second variable to the time it takes to travel 1 block.
     *
     * @param second The time it takes to travel 1 block.
     */
    public void setSeconds(double second) {
        this.second = second;
    }

    /**
     * The next point x coordinate to move to.
     *
     * @return The next point x coordinate to move to.
     */
    public double getX() {
        return x;
    }

    /**
     * Set the next point x coordinate to move to.
     *
     * @param x The next point x coordinate to move to.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * The entity to animate.
     *
     * @return The entity to animate.
     */
    public Entity getEnt() {
        return ent;
    }

    /**
     * Set the entity to animate.
     */
    public void setEnt(Entity ent) {
        this.ent = ent;
    }

    /**
     * The next point y coordinate to move to.
     *
     * @return The next point y coordinate to move to.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the next point y coordinate to move to.
     *
     * @param y The next point y coordinate to move to.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Set the time it takes to travel 1 block.
     *
     * @param second The time it takes to travel 1 block.
     */
    public void setSecond(double second) {
        this.second = second;
    }

    /**
     * Set the path of the animation.
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     * Start the animation.
     */
    public void startanim() {
        path = ((Soldier) ent).getPath();
        ent.isAnimated = true;
        //X=0;
    }

    /**
     * Stop the animation.
     */
    public void stopanim() {
        ent.isAnimated = false;
    }

    /**
     * Get the path of the animation.
     *
     * @return The path of the animation.
     */
    public ArrayList<Point> getPath() {
        return path;
    }

    /**
     * Set the path of the animation.
     *
     * @param path The path of the animation.
     */
    public void setPath(ArrayList<Point> path) {
        this.path = path;
    }

    /**
     * Remove the path of the animation.
     */
    public void removePath() {
        setPath(null);
    }

    /**
     * Set the next point to move to.
     */
    public void nextstep() {
        if (!path.isEmpty()) {
            ent.setPosition(new Point(ent.getPosition().x + path.get(0).x, ent.getPosition().y + path.get(0).y));
            path.remove(0);
        }
        x = y = 0;
    }

    /**
     * Animate the entity.
     *
     * @param mapEnts The map entities.
     */
    public void animation(Terrain[][] mapEnts) {
        setSpeedMod(mapEnts[ent.getPosition().y][ent.getPosition().x].getSpeedMod());
        setSeconds(speedMod);
        //setSeconds(1);
        x += ((GameFieldRenderer.getScale() * ((Soldier) ent).getSpeed()) / (second * 1000.0) * GameState.deltaTime) * (double) path.get(0).x;
        y += ((GameFieldRenderer.getScale() * ((Soldier) ent).getSpeed()) / (second * 1000.0) * GameState.deltaTime) * (double) path.get(0).y;

        if (Math.abs(x) >= GameFieldRenderer.getScale() || Math.abs(y) >= GameFieldRenderer.getScale()) {
            //System.out.println(ent.getType()+ " Scale: " + GameFieldRenderer.getScale() + " > "+X+" |"+Y);
            mapEnts[ent.getPosition().y][ent.getPosition().x].getEntities().remove(ent);
            if (mapEnts[ent.getPosition().y + path.get(0).y][ent.getPosition().x + path.get(0).x].getEntities().isEmpty() ||
                    (!mapEnts[ent.getPosition().y + path.get(0).y][ent.getPosition().x + path.get(0).x].getEntities().isEmpty() &&
                            !mapEnts[ent.getPosition().y + path.get(0).y][ent.getPosition().x + path.get(0).x].getEntities().get(0).getType().equals("Castle")))
                mapEnts[ent.getPosition().y + path.get(0).y][ent.getPosition().x + path.get(0).x].getEntities().add(ent);
            /*else {
                mapEnts[ent.getPosition().y][ent.getPosition().x].getEntities().add(ent);

            }*/
            ent.setPosition(new Point(ent.getPosition().x + path.get(0).x, ent.getPosition().y + path.get(0).y));
            path.remove(0);
            x = 0;
            y = 0;
            steps++;
            if (steps >= (int) ((Soldier) ent).getSpeed()) {
                stopanim();
                steps = 0;
            }
        }
    }

    /**
     * Set the speed modifier.
     *
     * @param speedMod The speed modifier.
     */
    public void setSpeedMod(double speedMod) {
        this.speedMod = speedMod;
    }
}
