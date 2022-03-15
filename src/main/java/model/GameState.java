package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameState {
    private final Timer elapsedTimer;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private int playerNumber;
    private boolean isEnded;
    private int elapsedTime;
    private String roundState;

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
        nextRoundState();
        nextRoundState();
    }

    public String getRoundState() {
        return roundState;
    }

    public void nextRoundState() {
        this.roundState = this.roundState.equals("Building") ? "Attacking" : "Building";
    }

    public void decideStarter() {
        Random rnd = new Random();
        this.playerNumber = rnd.nextInt(2);
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
