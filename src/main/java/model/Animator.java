package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import view.GameFieldRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Animator class, contains methods for moving the Units, and animate the attacking of Towers.
 */
public class Animator {
    private double x = 0;
    private double y = 0;
    private Entity ent;
    private double second = 0.5;
    private ArrayList<Point> path = new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(1, 0), new Point(1, 0), new Point(1, 0), new Point(0, -1), new Point(-1, 0)));
    private Point nextPoint;
    private int steps = 0;
    private double speedMod = 1;

    public Animator(Entity ent) {
        this.ent = ent;
    }

    @JsonCreator
    public Animator() {
    }


    public void setSeconds(double second) {
        this.second = second;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Entity getEnt() {
        return ent;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setEnt(Entity ent) {
        this.ent = ent;
    }

    public void setSecond(double second) {
        this.second = second;
    }

    public void setNextPoint(Point nextPoint) {
        this.nextPoint = nextPoint;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void startanim() {
        path = ((Soldier) ent).getPath();
        ent.isAnimated = true;
        //X=0;
    }

    public void stopanim() {
        ent.isAnimated = false;
    }

    public ArrayList<Point> getPath() {
        return path;
    }

    public void setPath(ArrayList<Point> path) {
        this.path = path;
    }

    public void removePath() {
        setPath(null);
    }

    public void nextstep() {
        if (!path.isEmpty()) {
            ent.setPosition(new Point(ent.getPosition().x + path.get(0).x, ent.getPosition().y + path.get(0).y));
            path.remove(0);
        }
        x = y = 0;
    }

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

    public void setSpeedMod(double speedMod) {
        this.speedMod = speedMod;
    }
}
