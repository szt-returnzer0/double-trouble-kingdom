package DTK_model;

/**
 * This class is used to store the current state of the game.
 */
public enum RoundState {
    /**
     * The game is in the first state.
     */
    TRAINING ("Training"),
    /**
     * The game is in the second state.
     */
    BUILDING ("Building"),
    /**
     * The game is in the third state.
     */
    ATTACKING ("Attacking");

    /**
     * The name of the state.
     */
    public final String text;

    /**
     * Constructor for the RoundState enum.
     */
    RoundState(String text) {
        this.text = text;
    }
}
