package DTK_model;

import java.io.Serializable;

/**
 * This class is used to store the current state of the game.
 */
public enum RoundState implements Serializable {
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


    public final String text;

    /**
     * Constructor for the RoundState enum.
     */
    RoundState(String text) {
        this.text = text;
    }
}
