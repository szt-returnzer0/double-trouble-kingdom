package model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameState implements Serializable {
    private final Timer elapsedTimer;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private int playerNumber;
    private boolean isEnded;
    private int elapsedTime;
    private String roundState;
    private int starterPlayer;

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

    public String getRoundState() {
        return roundState;
    }

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

    public void decideStarter() {
        Random rnd = new Random();
        this.playerNumber = rnd.nextInt(2);
        this.starterPlayer = this.playerNumber;
        this.currentPlayer = players.get(this.playerNumber);
    }

    public void nextPlayer() {
        this.playerNumber = this.playerNumber == 0 ? 1 : 0;
        this.currentPlayer = players.get(this.playerNumber);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void startElapsedTimer() {
        elapsedTimer.start();
    }

    public void stopElapsedTimer() {
        elapsedTimer.stop();
    }

    public void restartElapsedTimer() {
        elapsedTimer.restart();
    }

    public void setEnded(boolean ended) {
        this.isEnded = ended;
    }
}
