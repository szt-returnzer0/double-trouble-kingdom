package model;


import persistence.Database;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private final Map map;

    public Database getDatabase() {
        return database;
    }

    private final Database database;
    private final GameState gameState;

    public Game(Database database, Map map, String p1Name, String p2Name) {
        this.database = database;
        this.gameState = new GameState(p1Name, p2Name);
        this.map = map;
    }

    public void initializeGame() {
        this.gameState.startElapsedTimer();
        this.gameState.decideStarter();
    }

    public void restartGame() {
        this.gameState.restartElapsedTimer();
        this.gameState.decideStarter();
    }

    public void pauseGame() {
        this.gameState.stopElapsedTimer();
    }

    public void resumeGame() {
        this.gameState.startElapsedTimer();
    }

    public void endTurn() {
        this.gameState.nextPlayer();
    }

    public Map getMap() {
        return this.map;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void saveScore() {
        // Implement after Database implementation
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<Record> getScores() {
        // Implement after Database implementation
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
