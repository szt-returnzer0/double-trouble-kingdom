package model;

import persistence.DBRecord;
import persistence.Database;
import view.GameField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * GameState class implementation for Double Trouble Kingdom game, contains the Players, playerNumbers, the roundState, and other fields and methods for managing the state of the Game.
 */
public class GameState {
    public static double deltaTime = 1;
    public static ArrayList<Animator> animBuffer = new ArrayList<>();
    /**
     * Timer to increment elapsedTime.
     */
    private final Timer elapsedTimer;
    /**
     * ArrayList containing the Players.
     */
    private final ArrayList<Player> players;
    private final long startTime;
    /**
     * The current Player.
     */
    private Player currentPlayer;
    /**
     * The current Player's number.
     */
    private int playerNumber;
    /**
     * Check if the Game is ended.
     */
    private boolean isEnded;
    private long prevTime = 1;
    /**
     * The current round phase.
     */
    private String roundState;
    /**
     * Linked gamefield.
     */
    private GameField linkedGameField = null;
    /**
     * Frames per second.
     */
    private int fps = 60;
    /**
     * DB reference.
     */
    private Database DBRef;
    /**
     * The elapsed time in seconds.
     */
    private int elapsedTime;

    /**
     * Constructs a class containing the Players, roundState, and elapsedTimer.
     *
     * @param p1Name name of Player1
     * @param p2Name name of Player2
     */
    public GameState(String p1Name, String p2Name) {
        this.isEnded = false;
        this.elapsedTimer = new Timer((int) (1000.0 / fps), (e) -> timerFunction());
        elapsedTimer.start();
        this.players = new ArrayList<>(Arrays.asList(new Player(p1Name), new Player(p2Name)));
        decideStarter();
        this.roundState = "Building";
        startTime = System.currentTimeMillis();
    }

    /**
     * Returns if the Game is ended.
     *
     * @return if the Game is ended
     */
    public boolean isEnded() {
        return isEnded;
    }

    /**
     * Sets the Game's end to a boolean value.
     *
     * @param ended if the Game has ended
     */
    public void setEnded(boolean ended) {
        this.isEnded = ended;
    }

    /**
     * Sets the frame rate of the game.
     *
     * @param fps the frame rate
     */
    private void setFps(int fps) {
        this.fps = fps;
        elapsedTimer.setDelay((int) (1000.0 / this.fps));
    }

    /**
     * Link the GameField to the GameState.
     *
     * @param gf the GameField
     */
    public void linkGameField(GameField gf) {
        linkedGameField = gf;
    }

    /**
     * Link the Database to the GameState.
     *
     * @param db the Database
     */
    public void linkDBRef(Database db) {
        DBRef = db;
    }

    /**
     * The tick function for the elapsedTimer.
     */
    private void timerFunction() {
        gameLoop();
        long curTime = System.currentTimeMillis();
        deltaTime = (double) curTime - prevTime;

        for (int i = 0; i < animBuffer.size(); i++) {
            if (!animBuffer.get(i).getEnt().isAnimated) continue;
            animBuffer.get(i).animation(linkedGameField.getMapRef().getTiles());
            if (animBuffer.get(i).getPath().isEmpty()) {
                animBuffer.get(i).stopanim();
                if (animBuffer.get(i).getEnt() instanceof Soldier s && s.getWayPoints().isEmpty())
                    animBuffer.remove(i--);

            }

        }
        if (linkedGameField != null) {
            linkedGameField.updateUIState();
        }
        prevTime = curTime;
        elapsedTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    private boolean pathVisualization = false;

    /**
     * The game loop for the GameState.
     */
    public void gameLoop() {
        if (roundState.equals("Attacking")) {
            if (animBuffer.stream().noneMatch(e -> e.getEnt().isAnimated())) {
                calculatePaths();
                setTowerTargets();
                setSpecialTargets();
                towerAttack();
                soldierAttack();
                if (getWinner() != null) {
                    isEnded = true;
                    System.out.println("Winner: " + getWinner().getName());
                    elapsedTimer.stop();
                    saveScore();
                }
                nextRoundState();
            }
        }
    }

    /**
     * Loads the buildings from a map file after load.
     *
     * @param map the map file
     */
    public void loadBuildings(Map map) {
        HashSet<Building> buildings = new HashSet<>();
        for (int i = 0; i < map.getTiles().length; i++) {
            for (int j = 0; j < map.getTiles()[0].length; j++) {
                if (map.getTiles()[i][j].getEntities().size() > 0) {
                    if (map.getTiles()[i][j].getEntities().get(0) instanceof Building e) {
                        buildings.add(e);
                    }
                }
            }
        }
        for (Building b : buildings) {
            players.get(b.side.equals("left") ? 0 : 1).addSavedEntity(b);

        }
    }

    /**
     * Returns the Players in an ArrayList.
     *
     * @return the Players in an ArrayList
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the roundState.
     *
     * @return the roundState
     */
    public String getRoundState() {
        return roundState;
    }

    /**
     * Switches the current roundState to the next one. Switches Player after Training roundState phase.
     */
    public void nextRoundState() {
        linkedGameField.getWayPoints().clear();
        switch (this.roundState) {
            case "Building" -> this.roundState = "Training";
            case "Training" -> {
                this.roundState = "Attacking";
                for (Animator animator : animBuffer) {
                    animator.startanim();
                }
            }
            case "Attacking" -> {
                nextPlayer();
                this.roundState = "Building";
                currentPlayer.calculateGoldAtRound();
            }
        }

    }

    /**
     * Returns the winner of the game.
     *
     * @return the winner of the game
     */
    public Player getWinner() {
        if (players.get(0).getCastle().getHealthPoints() <= 0 && players.get(0).getSoldierCount() == 0) {
            elapsedTimer.stop();
            return players.get(1);
        } else if (players.get(1).getCastle().getHealthPoints() <= 0 && players.get(1).getSoldierCount() == 0) {
            elapsedTimer.stop();
            return players.get(0);
        } else if (players.get(0).getCastle().getHealthPoints() <= 0 && players.get(1).getCastle().getHealthPoints() <= 0) {
            //even
        }
        return null;
    }

    /**
     * Saves a DBRecord to the Game's Database.
     */
    public void saveScore() {
        DBRef.saveRecord(new DBRecord(getPlayers().get(0).getName(), getPlayers().get(1).getName(), getWinner().getPlayerNumber(), getElapsedTime()));
    }

    /**
     * Determines the starting Player.
     */
    public void decideStarter() {
        Random rnd = new Random();
        this.playerNumber = rnd.nextInt(2);
        this.currentPlayer = players.get(this.playerNumber);
    }

    /**
     * Ends a Player's turn. Switches the Player and playerNumber to next Player.
     */
    public void nextPlayer() {
        this.playerNumber = this.playerNumber == 0 ? 1 : 0;
        this.currentPlayer = players.get(this.playerNumber);
    }

    /**
     * Returns the current Player.
     *
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Starts the elapsedTimer.
     */
    public void startElapsedTimer() {
        elapsedTimer.start();
    }

    /**
     * Stops the elapsedTimer.
     */
    public void stopElapsedTimer() {
        elapsedTimer.stop();
    }

    /**
     * Restarts the elapsedTimer.
     */
    public void restartElapsedTimer() {
        elapsedTimer.restart();
    }

    /**
     * Returns the enemy's Castle.
     *
     * @param playerNumber the Player's number
     * @return the enemy's Castle
     */
    public Building getEnemyCastle(int playerNumber) {
        return players.get(playerNumber == 1 ? 1 : 0).getCastle();
    }

    /**
     * Returns the enemy's Soldiers.
     *
     * @param playerNumber the Player's number
     * @return the enemy's Soldiers
     */
    public ArrayList<Soldier> getEnemySoldiers(int playerNumber) {
        return players.get(playerNumber == 1 ? 1 : 0).getSoldiers();
    }

    /**
     * Sets the soldier's target.
     */
    public void setTargets() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Soldier s) {
                    s.selectTarget(getEnemyCastle(player.getPlayerNumber()));
                }
            }
        }
    }

    /**
     * Attack with each Soldier.
     */
    public void soldierAttack() {
        for (Player player : players) {
            for (int i = 0; i < player.getEntities().size(); i++) {
                if (player.getEntities().get(i) instanceof Soldier s) {
                    s.attack();
                    if (!s.isAlive()) {
                        player.removeSoldier((Soldier) player.getEntities().get(i--));
                    }
                }
            }
        }
        removeDeadSoldiers();
    }

    /**
     * Attack with each Tower.
     */
    public void towerAttack() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Tower t) {
                    t.attack();
                }
            }
        }
        removeDeadSoldiers();
    }

    /**
     * Removes dead Soldiers.
     */
    private void removeDeadSoldiers() {
        for (Player player : players) {
            for (int i = 0; i < player.getEntities().size(); i++) {
                if (player.getEntities().get(i) instanceof Soldier s) {
                    if (!s.isAlive()) {
                        player.removeSoldier((Soldier) player.getEntities().get(i--));
                        players.get(player.getPlayerNumber() == 1 ? 1 : 0).addGold(6);
                    }
                }
            }
        }
    }

    /**
     * Sets the Towers' targets.
     */
    public void setTowerTargets() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Tower t) {
                    t.selectTargets(getEnemySoldiers(player.getPlayerNumber()));
                }
            }
        }
    }

    /**
     * Sets the special Soldiers' targets.
     */
    public void setSpecialTargets() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Assassin s) {
                    s.selectTargets(getEnemySoldiers(player.getPlayerNumber()));
                }
                if (entity instanceof Kamikaze k) {
                    k.selectTargets(getEnemyTowers(player.getPlayerNumber()));
                }
            }
        }
    }

    /**
     * Returns the enemy's Towers.
     *
     * @param playerNumber the Player's number
     * @return the enemy's Towers
     */
    public ArrayList<Tower> getEnemyTowers(int playerNumber) {
        return players.get(playerNumber == 1 ? 1 : 0).getTowers();

    }

    /**
     * Returns elapsed time.
     *
     * @return elapsed time
     */
    public int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Sets the elapsed time.
     *
     * @param elapsedTime the elapsed time
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Calculates the soldier' path.
     */
    public void calculatePaths() {
        Pathfinder.setMap(linkedGameField.getMapRef());
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Soldier s) {
                    s.calculatePath();
                    s.animObj.setPath(s.getPath());
                }
            }
        }
    }

    /**
     * Returns the waypoints.
     *
     * @return the waypoints
     */
    public ArrayList<Point> getWayPoints() {
        if (linkedGameField != null) {
            return linkedGameField.getWayPoints();
        }
        return null;
    }

    public long getStartTime() {
        return startTime;
    }

    public void togglePathVisualization() {
        pathVisualization = !pathVisualization;
    }

    public boolean isPathVisualization() {
        return pathVisualization;
    }
}
