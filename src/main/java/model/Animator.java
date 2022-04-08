package model;

import view.GameFieldRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of Animator class, contains methods for moving the Units, and animate the attacking of Towers.
 */
public class Animator {
    public double X = 0;
    public double Y = 0;
    public Entity ent;
    private double second = 0.5;
    private ArrayList<Point> path = new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(1, 0), new Point(1, 0), new Point(1, 0), new Point(0, -1), new Point(-1, 0)));
    private Point nextPoint;
    int steps = 0;
    double speedMod = 1;

    public Animator(Entity ent) {
        this.ent = ent;
    }

    public void setSeconds(double second) {
        this.second = second;
    }

    public double getX() {
        return X;
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

    public void nextstep() {
        if (!path.isEmpty()) {
            ent.setPosition(new Point(ent.getPosition().x + path.get(0).x, ent.getPosition().y + path.get(0).y));
            path.remove(0);
        }
        X = Y = 0;
    }

    public void animation(Terrain[][] mapEnts) {
        setSpeedMod(mapEnts[ent.getPosition().y][ent.getPosition().x].getSpeedMod());
        setSeconds(speedMod);
        //setSeconds(1);
        X += ((GameFieldRenderer.getScale() * ((Soldier) ent).getSpeed()) / (second * 1000.0) * GameState.deltaTime) * (double) path.get(0).x;
        Y += ((GameFieldRenderer.getScale() * ((Soldier) ent).getSpeed()) / (second * 1000.0) * GameState.deltaTime) * (double) path.get(0).y;

        if (Math.abs(X) >= GameFieldRenderer.getScale() || Math.abs(Y) >= GameFieldRenderer.getScale()) {
            //System.out.println(ent.getType()+ " Scale: " + GameFieldRenderer.getScale() + " > "+X+" |"+Y);
            if (!path.isEmpty()) {
                mapEnts[ent.getPosition().y][ent.getPosition().x].getEntities().remove(ent);
                mapEnts[ent.getPosition().y + path.get(0).y][ent.getPosition().x + path.get(0).x].getEntities().add(ent);
                ent.setPosition(new Point(ent.getPosition().x + path.get(0).x, ent.getPosition().y + path.get(0).y));
                path.remove(0);
            }
            X = 0;
            Y = 0;
            steps++;
            if (steps >= 10) {
                stopanim();
                steps = 0;
            }
        }
    }

    public void setSpeedMod(double speedMod) {
        this.speedMod = speedMod;
    }
}
