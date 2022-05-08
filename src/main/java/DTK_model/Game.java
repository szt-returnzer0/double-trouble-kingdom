package DTK_model;


import DTK_persistence.Database;

import java.io.Serializable;

/**
 * Game class implementation for Double Trouble Kingdom, connects different game systems, contains methods for managing the state of game.
 */
public class Game implements Serializable {
    /**
     * The Map of the game.
     */
    private static Map mapReference;
    /**
     * The Database of the game.
     */
    private Database database;
    /**
     * The GameState of the game.
     */
    private final GameState gameState;

    /**
     * Local Map of the game.
     */
    private final Map map;

    /**
     * Constructs a Game with dependency injection of Database and Map.
     *
     * @param database     the Database to be injected
     * @param mapReference the Map to be injected
     * @param p1Name       the name of Player1
     * @param p2Name       the name of Player2
     */
    public Game(Database database, Map mapReference, String p1Name, String p2Name) {
        Pathfinder.setMap(mapReference);
        this.database = database;
        this.gameState = new GameState(p1Name, p2Name, database);
        this.gameState.loadBuildings(mapReference);
        Game.mapReference = mapReference;
        this.map = mapReference;
    }

    /**
     * Returns the Map of the game.
     *
     * @return the Map of the game
     */
    public Map getMap() {
        return map;
    }

    /**
     * Constructs a Game for Editor use.
     *
     * @param mapReference the Map to be injected
     */
    public Game(Map mapReference) {
        Pathfinder.setMap(mapReference);
        Game.mapReference = mapReference;
        this.gameState = new GameState();
        this.gameState.loadBuildings(mapReference);
        this.map = mapReference;
    }


    /**
     * Copy Constructor for Game.
     * @param game the Game to be copied
     */
    public Game(Game game) {
        this.gameState = new GameState(game.getGameState());
        this.map = game.map;
        this.database = game.database;
        Pathfinder.setMap(map);
        Game.mapReference = map;
        game.getGameState().setElapsedTime(game.gameState.getElapsedTime());
    }

    /**
     * Start a new Game.
     */
    public void restartGame() {
        this.gameState.restartElapsedTimer();
        this.gameState.decideStarter();
    }

    /**
     * Pause the running Game.
     */
    public void pauseGame() {
        this.gameState.stopElapsedTimer();
    }

    /**
     * Returns the Map of the Game.
     *
     * @return the Map of the Game
     */
    public static Map getMapReference() {
        return mapReference;
    }

    /**
     * Returns the GameState of the Game.
     *
     * @return the GameState of the Game
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Returns the Database of the Game.
     *
     * @return the Database of the Game
     */
    public Database getDatabase() {
        return database;
    }

}
