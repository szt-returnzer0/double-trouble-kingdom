package model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * GameState class implementation for Double Trouble Kingdom game, contains the Players, playerNumbers, the roundState, and other fields and methods for managing the state of the Game.
 */
public class GameState implements Serializable {
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
    /**
     * The current round phase.
     */
    private String roundState;
    /**
     * The player number of the starter player,
     */
    private int starterPlayer;


    /**
     * Constructs a class containing the Players, roundState, and elapsedTimer.
     *
     * @param p1Name name of Player1
     * @param p2Name name of Player2
     */
    public GameState(String p1Name, String p2Name) {
        this.isEnded = false;
        this.elapsedTimer = new Timer(1000, (e) -> {
            elapsedTime++;
            System.out.println(elapsedTime);
        });
        elapsedTimer.start();
        this.players = new ArrayList<>(Arrays.asList(new Player(p1Name), new Player(p2Name)));
        decideStarter();
        this.roundState = "Building";

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
        } else
            this.roundState = "Attacking";

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
