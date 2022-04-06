package model;

import view.GameField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    private void timerFunction() {
        elapsedTime += 50;
        long curTime = System.currentTimeMillis();
        deltaTime = (double) curTime - prevTime;

        for (int i = 0; i < animBuffer.size(); i++) {
            if (!animBuffer.get(i).ent.isAnimated) break;
            if (animBuffer.get(i).animation()) {

                linkedGameField.getMapRef().getTiles()[animBuffer.get(i).ent.getPosition().y][animBuffer.get(i).ent.getPosition().x].getEntities().remove(animBuffer.get(i).ent);
                System.out.println(animBuffer.get(i).ent.getPosition());
                animBuffer.get(i).nextstep();
                linkedGameField.getMapRef().getTiles()[animBuffer.get(i).ent.getPosition().y][animBuffer.get(i).ent.getPosition().x].getEntities().add(animBuffer.get(i).ent);
                if (animBuffer.get(i).getPath().isEmpty()) {
                    animBuffer.get(i).stopanim();
                    animBuffer.remove(i--);
                }
            }
        }
        if (linkedGameField != null) {
            linkedGameField.updateCounter();
        }
        prevTime = curTime;
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
        if (this.roundState.equals("Building"))
            this.roundState = "Training";
        else if (this.roundState.equals("Training") && this.playerNumber == starterPlayer) {
            nextPlayer();
            this.roundState = "Building";
        } else if (this.roundState.equals("Attacking")) {
            nextPlayer();
            this.roundState = "Building";
        } else {
            this.roundState = "Attacking";
            for (Animator animator : animBuffer) {
                animator.startanim();
            }
        }

    }

//    public int getWinner(){
//        if(players.get(0).getEntities().size() == 0)
//            return 2;
//        if(players.get(1).getEntities().size() == 0)
//            return 1;
//    }

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
}
