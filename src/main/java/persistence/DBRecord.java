package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of DBRecord class for Database INSERT and SELECT queries.
 */
public final class DBRecord {
    /**
     * Name of Player1.
     */
    private String p1Name;
    /**
     * Name of Player2.
     */
    private String p2Name;
    /**
     * Determines the Winner.
     */
    private int winner;
    /**
     * The length of the game in seconds.
     */
    private int time;

    /**
     * Constructs a new record from String and int parameters.
     *
     * @param p1Name name of Player1
     * @param p2Name name of Player2
     * @param winner the winner's number
     * @param time   the game's length in seconds
     */
    public DBRecord(String p1Name, String p2Name, int winner, int time) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.winner = winner;
        this.time = time;
    }

    /**
     * Constructs a new record from a ResultSet.
     *
     * @param resultSet the ResultSet to convert
     */
    public DBRecord(ResultSet resultSet) {
        try {
            this.p1Name = resultSet.getString("P1NAME");
            this.p2Name = resultSet.getString("P2NAME");
            this.winner = resultSet.getInt("WINNER");
            this.time = resultSet.getInt("TIME");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of Player1.
     *
     * @return the name of Player1
     */
    public String getP1Name() {
        return p1Name;
    }

    /**
     * Returns the name of Player2.
     *
     * @return the name of Player2
     */
    public String getP2Name() {
        return p2Name;
    }

    /**
     * Returns the winner's number or 0 for draw.
     *
     * @return the winner's number or 0 for draw
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Returns the game's length in seconds.
     *
     * @return the game's length in seconds
     */
    public int getTime() {
        return time;
    }

    /**
     * Returns the inner state of the record as a String.
     *
     * @return the inner state of the record as a String
     */
    @Override
    public String toString() {
        return "'" + p1Name + "','" + p2Name + "'," + winner + "," + time;
    }

}
