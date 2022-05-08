package DTK_model;

import DTK_view.GameFieldRenderer;
import com.fasterxml.jackson.annotation.JsonCreator;

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
    private Entity entity;
    /**
     * The time spent to travel 1 block.
     */
    private double second = 0.5;
    /**
     * The animation path.
     */
    private ArrayList<Point> path = new ArrayList<>();
    /**
     * The steps taken in the round.
     */
    private int steps = 0;
    /**
     * The speed modifier.
     */
    private double speedModifier = 1;

    /**
     * Sprite frame.
     */
    private int frame = 0;

    /**
     * Constructor for Animator class.
     *
     * @param entity The animated Entity.
     */
    public Animator(Entity entity) {
        this.entity = entity;
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
    public Entity getEntity() {
        return entity;
    }

    /**
     * Set the entity to animate.
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
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
    public void startAnimation() {
        ((Soldier) entity).calculatePath();
        path = ((Soldier) entity).getPath();
        entity.isAnimated = true;
    }

    /**
     * Stop the animation.
     */
    public void stopAnimation() {
        entity.isAnimated = false;
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
     * Animate an entity.
     */
    public void animate() {
        Terrain[][] entities = Game.getMap().getTiles();
        setSpeedModifier(entities[entity.getPosition().y][entity.getPosition().x].getSpeedMod());
        setSeconds(speedModifier);

        x += (GameFieldRenderer.getScale() * ((Soldier) entity).getSpeed() / (second * 1000.0) * GameState.deltaTime) * (double) path.get(0).x;
        y += (GameFieldRenderer.getScale() * ((Soldier) entity).getSpeed() / (second * 1000.0) * GameState.deltaTime) * (double) path.get(0).y;

        if (isInScale()) {
            entities[entity.getPosition().y][entity.getPosition().x].getEntities().remove(entity);
            if (isEmpty(entities) || (!isEmpty(entities) && !isCastle(entities)))
                entities[entity.getPosition().y + path.get(0).y][entity.getPosition().x + path.get(0).x].getEntities().add(entity);

            entity.setPosition(new Point(entity.getPosition().x + path.get(0).x, entity.getPosition().y + path.get(0).y));
            path.remove(0);

            nextFrame();
            performSteps();
        }
    }

    /**
     * Perform the number of steps, allowed by the unit type
     */
    private void performSteps() {
        x = 0;
        y = 0;
        steps++;
        if (steps >= ((Soldier) entity).getSpeed()) {
            stopAnimation();
            steps = 0;
        }
    }

    /**
     * Check if the coordinates are in a grid.
     * Cycles sprite frames.
     */
    private void nextFrame() {
        frame = frame == 3 ? 0 : (frame + 1);
    }

    /**
     * Gets the sprite frame number.
     *
     * @return frame.
     */
    public int getFrame() {
        return frame;
    }

    /**
     * Animate the entity.
     *
     * @return if the coordinates are in a grid
     */
    private boolean isInScale() {
        return Math.abs(x) >= GameFieldRenderer.getScale() || Math.abs(y) >= GameFieldRenderer.getScale();
    }

    /**
     * Check if the entity is on a Castle instance
     *
     * @param entities 2D array containing the entities
     * @return if the entity is on a Castle instance
     */
    private boolean isCastle(Terrain[][] entities) {
        return entities[entity.getPosition().y + path.get(0).y][entity.getPosition().x + path.get(0).x].getEntities().get(0).getType().equals(Types.CASTLE);
    }

    /**
     * Check if tile the entity stands on is empty.
     *
     * @param entities 2D array containing the entities
     * @return if tile the entity stands on is empty
     */
    private boolean isEmpty(Terrain[][] entities) {
        return entities[entity.getPosition().y + path.get(0).y][entity.getPosition().x + path.get(0).x].getEntities().isEmpty();
    }

    /**
     * Set the speed modifier.
     *
     * @param speedModifier The speed modifier.
     */
    public void setSpeedModifier(double speedModifier) {
        this.speedModifier = speedModifier;
    }
}
