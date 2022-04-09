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

    public boolean isEnded() {
        return isEnded;
    }

    private GameField linkedGameField = null;
    private int fps = 60;

    /**
     * The starting player's number.
     */
    private int starterPlayer;

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
    }

    private void setFps(int fps) {
        this.fps = fps;
        elapsedTimer.setDelay((int) (1000.0 / this.fps));
    }

    public void linkGameField(GameField gf) {
        linkedGameField = gf;
    }

    private void timerFunction() {


        gameLoop();
        long curTime = System.currentTimeMillis();
        deltaTime = (double) curTime - prevTime;

        for (int i = 0; i < animBuffer.size(); i++) {
            if (!animBuffer.get(i).getEnt().isAnimated) continue;
            animBuffer.get(i).animation(linkedGameField.getMapRef().getTiles());
            if (animBuffer.get(i).getPath().isEmpty()) {
                animBuffer.get(i).stopanim();
                animBuffer.remove(i--);

            }

        }
        if (linkedGameField != null) {
            linkedGameField.updateUIState();
        }
        prevTime = curTime;
    }


    public void gameLoop() {
        if (roundState.equals("Attacking")) {
            if (animBuffer.stream().noneMatch(e -> e.getEnt().isAnimated())) {
                attacks();
                setTowerTargets();
                towerAttack();
                if (getWinner() != null) {
                    isEnded = true;
                    System.out.println("Winner: " + getWinner().getName());
                }
                nextRoundState();
            }
        }

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


    public Player getWinner() {
        if (players.get(0).getCastle().getHealthPoints() <= 0 && players.get(0).getSoldierCount() == 0) {
            return players.get(1);
        } else if (players.get(1).getCastle().getHealthPoints() <= 0 && players.get(1).getSoldierCount() == 0) {
            return players.get(0);
        }
        return null;
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

    public Building getEnemyCastle(int playerNumber) {
        return players.get(playerNumber == 1 ? 1 : 0).getCastle();
    }

    public ArrayList<Soldier> getEnemySoldiers(int playerNumber) {
        return players.get(playerNumber == 1 ? 1 : 0).getSoldiers();
    }


    public void setTargets() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Soldier s) {
                    s.selectTarget(getEnemyCastle(player.getPlayerNumber()));
                }
            }
        }
    }

    public void attacks() {
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
    }

    public void towerAttack() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Tower t) {
                    t.attack();
                }
            }
        }
        for (Player player : players) {
            for (int i = 0; i < player.getEntities().size(); i++) {
                if (player.getEntities().get(i) instanceof Soldier s) {
                    if (!s.isAlive()) {
                        player.removeSoldier((Soldier) player.getEntities().get(i--));
                    }
                }
            }
        }
    }

    public void setTowerTargets() {
        for (Player player : players) {
            for (Entity entity : player.getEntities()) {
                if (entity instanceof Tower t) {
                    t.selectTargets(getEnemySoldiers(player.getPlayerNumber()));
                }
            }
        }
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
