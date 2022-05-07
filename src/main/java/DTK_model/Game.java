package DTK_model;


import DTK_persistence.Database;

/**
 * Game class implementation for Double Trouble Kingdom, connects different game systems, contains methods for managing the state of game.
 */
public class Game {
    /**
     * The Map of the game.
     */
    private final Map map;
    /**
     * The Database of the game.
     */
    private Database database;
    /**
     * The GameState of the game.
     */
    private final GameState gameState;

    /**
     * Constructs a Game with dependency injection of Database and Map.
     *
     * @param database the Database to be injected
     * @param map      the Map to be injected
     * @param p1Name   the name of Player1
     * @param p2Name   the name of Player2
     */
    public Game(Database database, Map map, String p1Name, String p2Name) {
        Pathfinder.setMap(map);
        this.database = database;
        this.gameState = new GameState(p1Name, p2Name);
        this.gameState.linkDBRef(database);
        this.map = map;
    }

    /**
     * Constructs a Game for Editor use.
     *
     * @param map the Map to be injected
     */
    public Game(Map map) {
        Pathfinder.setMap(map);
        this.map = map;
        this.gameState = new GameState();
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
    public Map getMap() {
        return this.map;
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
