package model;


import persistence.Database;

import java.util.ArrayList;

public class Game {
    private final Terrain[][] map;
    private final Database database;
    private final GameState gameState;

    public Game(Database database, Terrain[][] map, String p1Name, String p2Name) {
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

    public Terrain[][] getMap() {
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
