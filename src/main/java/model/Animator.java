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

    public Animator(Entity ent) {
        this.ent = ent;
    }

    public void setSpeed(double speed) {
        second = speed;
    }

    public double getX() {
        return X;
    }

    public void startanim() {
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
        // cnt++;
        //if(cnt>=path.size()){cnt=0;}
        X = Y = 0;
    }

    public boolean animation() {
        X += (GameFieldRenderer.getScale() / (second * 1000.0) * GameState.deltaTime) * path.get(0).x;
        Y += (GameFieldRenderer.getScale() / (second * 1000.0) * GameState.deltaTime) * path.get(0).y;
        return Math.abs(X) >= GameFieldRenderer.getScale() || Math.abs(Y) >= GameFieldRenderer.getScale();
    }
}
