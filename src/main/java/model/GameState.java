package model;

import view.GameField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * GameState class implementation for Double Trouble Kingdom game, contains the Players, playerNumbers, the roundState, and other fields and methods for managing the state of the Game.
 */
public class GameState {
    /**
     * Timer to increment elapsedTime.
     */
    private final Timer elapsedTimer;
    /**
     * ArrayList containing the Players.
     */
    private final ArrayList<Player> players;
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
    /**
     * The elapsed time in seconds.
     */
    private int elapsedTime;

    public static double deltaTime = 1;
    public ArrayList<Animator> animBuffer = new ArrayList<>();
    private long prevTime = 1;
    /**
     * The current round phase.
     */
    private String roundState;
    /**
     * The player number of the starter player,
     */
    private int starterPlayer;
    private GameField linkedGameField = null;

    private int eventStarter = 0;

    private int attackTick = 0;

    private int fps = 60;

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
    }

    private void setFps(int fps) {
        this.fps = fps;
        elapsedTimer.setDelay((int) (1000.0 / this.fps));
    }

    public void linkGameField(GameField gf) {
        linkedGameField = gf;
    }

    int step = 0;

    private void timerFunction() {


        gameLoop();
        long curTime = System.currentTimeMillis();
        deltaTime = (double) curTime - prevTime;

        for (int i = 0; i < animBuffer.size(); i++) {
            if (!animBuffer.get(i).ent.isAnimated) continue;
            animBuffer.get(i).animation(linkedGameField.getMapRef().getTiles());
            if (animBuffer.get(i).getPath().isEmpty()) {
                animBuffer.get(i).stopanim();
                animBuffer.remove(i--);
            }

        }
        if (linkedGameField != null) {
            linkedGameField.updateCounter();
        }
        prevTime = curTime;
    }


    public void gameLoop() {
        eventStarter++;
        if (roundState.equals("Attacking")) {
            attackTick++;
            if (attackTick == 200) {
                nextRoundState();
                attackTick = 0;
            }
        }
        if (eventStarter % fps * 2 == 0) {
            attacks();
        }
        // System.out.println(eventStarter);
    }

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
     * Returns the elapsed Time in seconds.
     *
     * @return the elapsed Time in seconds
     */
    public int getElapsedTime() {
        return elapsedTime;
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
            }
        }

    }


    public int getWinner() {
        if (players.get(0).getCastle().getHealthPoints() <= 0 && players.get(0).getSoldierCount() == 0) {
            return players.get(1).getPlayerNumber();
        } else if (players.get(1).getCastle().getHealthPoints() <= 0 && players.get(1).getSoldierCount() == 0) {
            return players.get(0).getPlayerNumber();
        }
        return -1;
    }


    /**
     * Determines the starting Player.
     */
    public void decideStarter() {
        Random rnd = new Random();
        this.playerNumber = rnd.nextInt(2);
        this.starterPlayer = this.playerNumber;
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
     * Sets the Game's end to a boolean value.
     *
     * @param ended if the Game has ended
     */
    public void setEnded(boolean ended) {
        this.isEnded = ended;
    }

    public Building getEnemyCastle() {
        for (Entity entity : players.get(playerNumber == 0 ? 1 : 0).getEntities()) {
            if (entity.getType().equals("Castle")) {
                return (Building) entity;
            }
        }
        return null;
    }


    public void setTargets() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Soldier s) {
                    s.selectTarget(getEnemyCastle());
                }
            }
        }
    }

    public void attacks() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Soldier s) {
                    s.attack();
                }
            }
        }
    }

    public void removeDeadSoldiers() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Soldier s) {
                    if (s.getHealthPoints() <= 0) {
                        player.removeEntity(s);
                    }
                }
            }
        }
    }
}
